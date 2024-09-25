package io.ruin.model.activities.shadesofmortton;

import io.ruin.api.utils.StringUtils;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.data.impl.dialogue.DialogueLoaderAction;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/22/2024
 */
@Getter
@AllArgsConstructor
public enum Coffin {
    BRONZE(25442, 25459, 25469, 3),
    STEEL(25445, 25461, 25470, 8),
    BLACK(25448, 25463, 25471, 14),
    SILVER(25451, 25465, 25472, 20),
    GOLD(25454, 25467, 25473, 28);

    private final int lockId, coffinId, openId, shadeStorage;

    private void fill(Player player) {
        int storedRemains = getTotalStored(player);
        for (Item item : player.getInventory().getItems()) {
            if (storedRemains >= shadeStorage) {
                player.sendMessage("Your coffin is full.");
                return;
            }
            if (item == null) continue;
            if (!FuneralPyre.REMAINS.contains(item.getId())) continue;
            item.remove();
            player.incrementNumericAttribute(getRemainsKey(item.getId()), 1);
            storedRemains++;
        }
    }

    private void configure(Player player, Item item) {
        player.dialogue(new OptionsDialogue(
                new Option("Check Shade Count.", () -> check(player)),
                new Option("Empty Coffin.", () -> empty(player)),
                new Option("Remove Lock.", () -> removeLock(player, item))
        ));
    }

    private void removeLock(Player player, Item item) {
        if (player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough inventory space to remove the lock.");
            return;
        }
        if (player.getEquipment().hasId(item.getId())) {
            player.sendMessage("You need to unequip the coffin before removing the key..");
            return;
        }
        player.dialogue(
                new MessageDialogue("Really remove the lock?<br><br>All shade remains will be lost!"),
                new OptionsDialogue("Remove the lock and lose remains?",
                        new Option("Remove the lock and lose remains.", () -> {
                            clearRemains(player);
                            player.getInventory().add(lockId);
                            item.setId(25457);
                        }),
                        new Option("Do nothing.")
                )
        );
    }

    private void clearRemains(Player player) {
        for (int itemId : FuneralPyre.REMAINS) {
            player.removeAttribute(getRemainsKey(itemId));
        }
    }

    private void check(Player player) {
        StringBuilder sb = new StringBuilder();
        for (int itemId : FuneralPyre.REMAINS) {
            sb.append(StringUtils.capitalizeFirst(ItemDefinition.get(itemId).name.toLowerCase().split(" ")[0]));
            sb.append(" ");
            sb.append(player.getAttributeIntOrZero(getRemainsKey(itemId)));
            if (itemId != 25419) {
                sb.append(" / ");
            }
        }
        player.sendMessage(sb.toString());
    }

    private void remove(Player player, int itemId, int amt) {
        int freeSlots = player.getInventory().getFreeSlots();
        if (freeSlots == 0) {
            player.sendMessage("You don't have enough inventory space to withdraw any remains.");
            return;
        }
        int storedAmt = player.getAttributeIntOrZero(getRemainsKey(itemId));
        if (storedAmt < amt) amt = storedAmt;
        if (freeSlots < amt) amt = freeSlots;
        player.incrementNumericAttribute(getRemainsKey(itemId), -amt);
        player.getInventory().add(itemId, amt);
        check(player);
    }

    private void empty(Player player) {
        if (player.getInventory().getFreeSlots() == 0) {
            player.sendMessage("You don't have enough inventory space to withdraw any remains.");
            return;
        }
        List<SkillItem> skillItems = new ArrayList<>();
        for (int itemId : FuneralPyre.REMAINS) {
            if (player.getAttributeIntOrZero(getRemainsKey(itemId)) > 0) {
                skillItems.add(new SkillItem(itemId).addAction((p, integer, event) -> remove(p, itemId, integer)));
            }
        }
        if (skillItems.isEmpty()) {
            player.sendMessage("You don't have any remains stored in the coffin.");
            return;
        }
        SkillDialogue.make("What would you like to take?", player, skillItems.toArray(new SkillItem[0]));
    }

    private void open(Player player, Item item) {
        item.setId(openId);
        player.sendMessage("You open the coffin.");
    }

    private void close(Player player, Item item) {
        item.setId(coffinId);
        player.sendMessage("You close the coffin.");
    }

    private int getTotalStored(Player player) {
        int amt = 0;
        for (int itemId : FuneralPyre.REMAINS) {
            amt += player.getAttributeIntOrZero(getRemainsKey(itemId));
        }
        return amt;
    }

    private String getRemainsKey(int itemId) {
        return ItemDefinition.get(itemId).name.toUpperCase();
    }

    public boolean addRemains(Player player, int itemId) {
        int storedRemains = getTotalStored(player);
        if (storedRemains >= shadeStorage) {
            return false;
        }
        player.incrementNumericAttribute(getRemainsKey(itemId), 1);
        return true;
    }

    private static final Set<Integer> OPEN_COFFINS = new LinkedHashSet<>();

    public static Coffin getCoffin(Player player) {
        Optional<Integer> coffinId = OPEN_COFFINS.stream().filter(id -> player.getInventory().hasId(id) || player.getEquipment().hasId(id)).findFirst();
        if (!coffinId.isPresent()) return null;
        for (Coffin coffin : values()) {
            if (coffinId.get().intValue() == coffin.openId) {
                return coffin;
            }
        }
        return null;
    }

    static {
        for (Coffin coffin : values()) {
            ItemAction.registerInventory(coffin.coffinId, "open", coffin::open);
            ItemAction.registerInventory(coffin.openId, "close", coffin::close);
            ItemAction.registerEquipment(coffin.coffinId, "open", coffin::open);
            ItemAction.registerEquipment(coffin.openId, "close", coffin::close);
            ItemAction.registerInventory(coffin.coffinId, "fill", (player, item) -> coffin.fill(player));
            ItemAction.registerInventory(coffin.openId, "fill", (player, item) -> coffin.fill(player));
            ItemAction.registerEquipment(coffin.coffinId, "fill", (player, item) -> coffin.fill(player));
            ItemAction.registerEquipment(coffin.openId, "fill", (player, item) -> coffin.fill(player));
            ItemAction.registerInventory(coffin.coffinId, "configure", coffin::configure);
            ItemAction.registerInventory(coffin.openId, "configure", coffin::configure);
            ItemAction.registerEquipment(coffin.coffinId, "configure", coffin::configure);
            ItemAction.registerEquipment(coffin.openId, "configure", coffin::configure);

            ItemDefinition.get(coffin.coffinId).custom_values.put("DESTROY_CONSUMER", (Consumer<Player>) (coffin::clearRemains));
            ItemDefinition.get(coffin.openId).custom_values.put("DESTROY_CONSUMER", (Consumer<Player>) (coffin::clearRemains));

            OPEN_COFFINS.add(coffin.openId);
        }
        // Dampe
        NPCAction.register(10590, "locks", (player, npc) -> {
            player.setDialogueNPC(npc);
            DialogueLoaderAction.DAMPE_LOCKS.getAction().accept(player);
        });
        for (Coffin coffin : values()) {
            ItemNPCAction.register(coffin.lockId, 10590, (player, item, npc) -> {
                player.setDialogueNPC(npc);
                DialogueLoaderAction.DAMPE_LOCKS.getAction().accept(player);
            });
        }
        ItemNPCAction.register(25457, 10590, (player, item, npc) -> {
            Coffin coffin = Arrays.stream(Coffin.values()).filter(c -> player.getInventory().hasId(c.getLockId())).findFirst().orElse(null);
            if (coffin == null) {
                player.dialogue(
                        new PlayerDialogue("Can you fix this for me?"),
                        new NPCDialogue(npc, "Depends, you got any locks for it?"),
                        new PlayerDialogue("No..."),
                        new NPCDialogue(npc, "Yeah, well clear off them a go find one.")
                );
            } else {
                player.setDialogueNPC(npc);
                DialogueLoaderAction.DAMPE_LOCKS.getAction().accept(player);
            }
        });
    }
}