package io.ruin.model.content.tasksystem.areas.diaryitems;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMask;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.skills.magic.SpellBook;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/22/2024
 */
public class ExplorersRing {

    private static final String ENERGY_KEY = "EXPL_RUN";
    private static final String TELEPORT_KEY = "EXPL_TELE";
    public static final Config DAILY_ALCHEMY = Config.varpbit(4554, true);
    private static final Config ALCHEMY_TAB = Config.varpbit(5398, false);

    private static final int[] RINGS = { Items.EXPLORERS_RING_1, Items.EXPLORERS_RING_2, Items.EXPLORERS_RING_3, Items.EXPLORERS_RING_4 };

    private static boolean canAlch(Player player) {
        return DAILY_ALCHEMY.get(player) < 30;
    }

    public static boolean hasRing(Player player) {
        if (!canAlch(player)) return false;
        return player.getInventory().containsAny(false, RINGS)
                || player.getEquipment().containsAny(false, RINGS);
    }

    public static boolean hasFourthRing(Player player) {
        if (!canAlch(player)) return false;
        return player.getInventory().contains(Items.EXPLORERS_RING_4)
                || player.getEquipment().contains(Items.EXPLORERS_RING_4);
    }

    private static void alchemy(Player player, boolean high) {
        ALCHEMY_TAB.set(player, high ? 1 : 0);
        player.openInterface(InterfaceType.INVENTORY, 483);
        player.getPacketSender().sendAccessMask(483, 0, 0, 2, AccessMasks.ClickOp1);
        player.getPacketSender().sendAccessMask(483, 7, 0, 27, AccessMasks.ClickOp1);
    }

    private static void restoreRun(Player player, Item item) {
        int maxUses = item.getId() == Items.EXPLORERS_RING_1 ? 2 : item.getId() == Items.EXPLORERS_RING_3 ? 4 : 3;
        int recharge = item.getId() == Items.EXPLORERS_RING_4 ? 100 : 50;
        if (player.getAttributeIntOrZero(ENERGY_KEY) >= maxUses) {
            player.timeTillDailyReset("You've already used your energy recharges for the day.<br><br>");
            return;
        }
        player.incrementNumericAttribute(ENERGY_KEY, 1);
        player.getMovement().restoreEnergy(recharge);
    }

    static {
        ItemAction.registerInventory(Items.EXPLORERS_RING_1, "alchemy", (player, item) -> alchemy(player, false));
        ItemAction.registerInventory(Items.EXPLORERS_RING_1, "energy", ExplorersRing::restoreRun);
        for (int itemId : RINGS) {
            ItemAction.registerInventory(itemId, "functions", (player, item) -> {
                player.dialogue(
                        new OptionsDialogue(
                                new Option("Run energy recharge", () -> restoreRun(player, item)),
                                new Option("Alchemy", () -> alchemy(player, itemId == Items.EXPLORERS_RING_4))
                        )
                );
            });
            ItemAction.registerEquipment(itemId, "alchemy", (player, item) -> alchemy(player, itemId == Items.EXPLORERS_RING_4));
            ItemAction.registerEquipment(itemId, "energy", ExplorersRing::restoreRun);
        }
        // Alchemy interface
        InterfaceHandler.register(483, h -> {
            h.actions[1] = (SimpleAction) player -> ALCHEMY_TAB.set(player, 0);
            h.actions[2] = (SimpleAction) player -> {
                if (hasFourthRing(player)) ALCHEMY_TAB.set(player, 1);
                else player.sendMessage("Only the Explorer's ring 4 can perform high alchemy.");
            };
            h.actions[4] = (SimpleAction) player -> player.closeInterface(InterfaceType.INVENTORY);
            h.actions[7] = (SlotAction) (player, slot) -> {
                if (!canAlch(player)) {
                    player.timeTillDailyReset("You've used all your Explorer's ring alchemy charges for the day.<br><br>");
                    return;
                }
                SpellBook.MODERN.spells[ALCHEMY_TAB.get(player) == 0 ? 13 : 34].itemAction.accept(player, player.getInventory().get(slot));
            };
        });
    }
}
