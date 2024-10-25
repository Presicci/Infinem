package io.ruin.model.content.transmog;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/4/2024
 */
public class TransmogInterface {

    public static void open(Player player) {
        if (player.getCombat().isDefending(16)) {
            player.sendMessage("You can't open your transmogrifications while in combat.");
            return;
        }
        if (player.wildernessLevel > 0 || player.pvpAttackZone) {
            player.sendMessage("You can't open your transmogrifications in the wilderness.");
            return;
        }
        if (player.isLocked())
            return;
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 1011);
        // Title dropdown
        player.getPacketSender().sendAccessMask(1011, 42, 0, 200, 2);
        // Filter dropdown
        player.getPacketSender().sendAccessMask(1011, 46, 0, 20, 2);
        // Hide title selection for now
        player.getPacketSender().setHidden(1011, 25, true);
        sendTransmogList(player);
        send(player);
        player.getAppearance().update();
    }

    public static void sendTransmogList(Player player) {
        Set<Integer> collectedTransmogs = player.getTransmogCollection().getCollectedTransmogs();
        List<Integer> sortedTransmogs = new ArrayList<>(collectedTransmogs);
        TransmogSlot filter = TransmogFilter.getFilteredSlot(player);
        sortedTransmogs = sortedTransmogs.stream()
                .filter(transmog -> filter == null || filter.getEquipmentSlot() == ItemDefinition.get(transmog).equipSlot)
                .sorted((item1, item2) -> {
            return ItemDefinition.get(item1).name.compareTo(ItemDefinition.get(item2).name);
        }).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (int itemId : sortedTransmogs) {
            sb.append(ItemDefinition.get(itemId).name);
            sb.append("||");
        }
        if (filter == null) {
            for (List<UnlockableTransmog> transmogs : UnlockableTransmog.TRANSMOGS_BY_SLOT.values()) {
                for (UnlockableTransmog transmog : transmogs) {
                    int itemId = transmog.getItemId();
                    if (collectedTransmogs.contains(itemId)) continue;
                    sortedTransmogs.add(transmog.getItemId());
                    sb.append(ItemDefinition.get(itemId).name);
                    sb.append("|");
                    sb.append(transmog.getUnlockRequirement());
                    sb.append("|");
                }
            }
        } else {
            List<UnlockableTransmog> transmogs = UnlockableTransmog.TRANSMOGS_BY_SLOT.get(TransmogSlot.getSlot(filter.getEquipmentSlot()));
            if (transmogs != null) {
                for (UnlockableTransmog transmog : transmogs) {
                    int itemId = transmog.getItemId();
                    if (collectedTransmogs.contains(itemId)) continue;
                    sortedTransmogs.add(transmog.getItemId());
                    sb.append(ItemDefinition.get(itemId).name);
                    sb.append("|");
                    sb.append(transmog.getUnlockRequirement());
                    sb.append("|");
                }
            }
        }
        player.getPacketSender().sendClientScript(10080, "s", sb.toString());
        player.getPacketSender().sendAccessMask(1011, 23, 0, 1000, 2);
        player.putTemporaryAttribute("TRANSMOGS", sortedTransmogs);
    }

    private static void clickTransmog(Player player, int option, int slot, int itemId) {
        if (option == 1) {
            List<Integer> transmogs = player.getTemporaryAttributeOrDefault("TRANSMOGS", new ArrayList<>());
            int index = slot / 4;
            if (index >= transmogs.size()) {
                player.sendMessage("Indexoob");
                return;
            }
            int transmogId = transmogs.get(index);
            if (player.getTransmogCollection().hasTransmog(transmogId)) {
                player.getTransmogCollection().setTransmogPreview(transmogId);
            } else {
                UnlockableTransmog unlockableTransmog = UnlockableTransmog.TRANSMOGS_BY_ID.get(transmogId);
                if (unlockableTransmog == null) return;
                player.dialogueKeepInterfaces(new MessageDialogue("To unlock this transmog:<br><br>" + unlockableTransmog.getUnlockRequirement()));
            }
        }
        player.sendMessage("op:" + option +", slot:"+slot);
    }

    private static void clickAppliedTransmog(Player player, int option, TransmogSlot slot) {
        int slotId = slot.getEquipmentSlot();
        if (option == 1) {
            player.getTransmogCollection().clearSlot(slotId);
        } else if (option == 2) {
            TransmogFilter.selectFilter(player, slot);
        } else if (option == 10) {
            int itemId = player.getTransmogCollection().getItemIdForSlot(slot);
            if (itemId != -1) {
                new Item(itemId).examine(player);
            }
        }
    }

    private static void clickDropdown(Player player, int slot) {
        TransmogFilter.selectFilter(player, slot - 1);
        sendTransmogList(player);
    }

    protected static void send(Player player) {
        if (!player.isVisibleInterface(1011)) return;
        for (TransmogSlot slot : TransmogSlot.values()) {
            sendSlot(player, slot);
        }
    }

    protected static void sendSlot(Player player, int slot) {
        TransmogSlot transmogSlot = TransmogSlot.getSlot(slot);
        if (transmogSlot == null) {
            player.sendMessage("Improper slot for: " + slot);
            return;
        }
        sendSlot(player, transmogSlot);
    }

    private static void sendSlot(Player player, TransmogSlot slot) {
        int itemId = player.getTransmogCollection().getTransmogIdForSlot(slot.getEquipmentSlot());
        int unapplied = player.getTransmogCollection().getPreviewIdForSlot(slot.getEquipmentSlot());
        if (unapplied != -2) {
            player.getPacketSender().sendClientScript(10079, "iii", 1011 << 16 | slot.getComponent(), unapplied, 5);
        } else {
            player.getPacketSender().sendClientScript(10079, "iii", 1011 << 16 | slot.getComponent(), itemId, itemId == -1 ? 0 : 1);
        }
        player.getAppearance().update();
    }

    static {
        InterfaceHandler.register(1011, h -> {
            h.actions[23] = (DefaultAction) TransmogInterface::clickTransmog;
            for (TransmogSlot slot : TransmogSlot.values()) {
                int slotId = slot.getComponent();
                h.actions[slotId] = (OptionAction) (player, option) -> clickAppliedTransmog(player, option, slot);
            }
            h.actions[28] = (SimpleAction) player -> player.getTransmogCollection().applyTransmog();
            h.actions[30] = (SimpleAction) player -> player.getTransmogCollection().clearPreview();
            h.actions[46] = (SlotAction) TransmogInterface::clickDropdown;
            h.closedAction = (player, integer) -> {
                player.getAppearance().update();
            };
        });
    }
}
