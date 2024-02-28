package io.ruin.model.activities.cluescrolls;

import io.ruin.model.activities.cluescrolls.impl.EmoteClue;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Material;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Utils;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/21/2021
 */
public class StashUnits {
    /**
     * TEST THIS I REDID SOME SHIT
     */
    @AllArgsConstructor
    public enum StashRequirements {
        BEGINNER(12, 150.0, new Item[] { new Item(Items.PLANK, 2) }),
        EASY(27, 150.0, new Item[] { new Item(Items.PLANK, 2) }),
        MEDIUM(42, 250.0, new Item[] { new Item(Items.OAK_PLANK, 2) }),
        HARD(55, 400.0, new Item[] { new Item(Items.TEAK_PLANK, 2) }),
        ELITE(77, 600.0, new Item[] { new Item(Items.MAHOGANY_PLANK, 2) }),
        MASTER(88, 1500.0, new Item[] { new Item(Items.MAHOGANY_PLANK, 2), new Item(Items.GOLD_LEAF, 1) });

        private final int level;
        private final double experience;
        private final Item[] items;
    }

    private static void build(Player player, EmoteClue.EmoteClueData emoteClueData, Config config) {
        StashRequirements stashReqs = StashRequirements.values()[emoteClueData.type.ordinal()];
        if (player.getStats().get(StatType.Construction).currentLevel < stashReqs.level) {
            player.sendMessage("You need a construction level of " + stashReqs.level + " to build this stash.");
            return;
        }
        boolean missingItem = false;
        for (Item item : stashReqs.items) {
            if (!player.getInventory().contains(item)) {
                missingItem = true;
            }
        }
        int nail = 0;
        for (int i : Material.NAIL_TYPES) {
            if (player.getInventory().contains(i, 10)) {
                nail = i;
                break;
            }
        }
        if (missingItem || nail == 0) {
            // Elaborate ass string builder
            StringBuilder message = new StringBuilder("You need ");
            List<Item> list = new ArrayList<Item>(Arrays.asList(stashReqs.items));
            list.add(new Item(Items.HAMMER, 1));
            list.add(new Item(Items.SAW, 1));
            List<String> sList = Utils.itemsToStringList(list);
            sList.add("10 nails");
            message.append(Utils.grammarCorrectList(sList));
            message.append(" to build this stash unit.");

            player.dialogue(new ItemDialogue().two(stashReqs.items[0].getId(), Items.IRON_NAILS, message.toString()));
            return;
        }
        int finalNail = nail;
        player.startEvent(e -> {
            for (Item item : stashReqs.items) {
                player.getInventory().remove(item);
            }
            player.getInventory().remove(finalNail, 10);
            player.animate(3683);
            e.delay(1);
            config.set(player, 1);
            player.getStats().addXp(StatType.Construction, stashReqs.experience, true);
            player.sendMessage("You build the stash unit.");
        });
    }

    /*
     * Withdrawing from the unit.
     */
    private static void withdraw(Player player, EmoteClue.EmoteClueData emoteClueData, List<Item> itemCon) {
        if (!player.getInventory().hasFreeSlots(itemCon.size())) {
            player.sendMessage("You do not have enough inventory space to withdraw your items.");
            return;
        }
        for (Item item : itemCon) {
            player.getInventory().add(item.copy());
        }
        player.getStashUnits().get(emoteClueData).clear();
        player.sendMessage("You withdraw your items from the stash.");
    }

    /*
     * Depositing to the unit.
     */
    private static void deposit(Player player, EmoteClue.EmoteClueData emoteClueData) {
        List<Item> itemsToDeposit = new ArrayList<>();
        if (emoteClueData.setSize > 0) {
            setLoop: for (int index = 0; index < emoteClueData.equipment.size() / emoteClueData.setSize; index++) {
                int leftIndex = index * emoteClueData.setSize;
                List<Integer> set = emoteClueData.equipment.subList(leftIndex, leftIndex + emoteClueData.setSize);
                int count = 0;
                for (int itemId : set) {
                    Item item = EmoteClue.getAlternative(player, itemId, false);
                    if (item != null) {
                        count++;
                    } else {
                        continue setLoop;
                    }
                }
                if (count == emoteClueData.setSize) {
                    for (int itemId : set) {
                        Item item = EmoteClue.getAlternative(player, itemId, false);
                        if (item != null) {
                            itemsToDeposit.add(item);
                        } else {    // If the player does not have all the items required to fill the stash
                            player.dialogue(new ItemDialogue().one(itemId, "You need a full barrows set to fill the stash."));
                            return;
                        }
                    }
                    break;
                }
            }
            if (itemsToDeposit.size() < emoteClueData.setSize) {
                player.dialogue(new ItemDialogue().one(Items.DHAROKS_HELM, "You need a full barrows set to fill the stash."));
                return;
            }
        } else {
            for (int itemId : emoteClueData.equipment) {
                Item item = EmoteClue.getAlternative(player, itemId, false);
                if (item != null) {
                    itemsToDeposit.add(item);
                } else {    // If the player does not have all the items required to fill the stash
                    String message = "You need " + Utils.grammarCorrectListForItemIds(emoteClueData.equipment) +
                            " to fill this stash.";
                    for (String replacement : EmoteClue.STRING_REPLACEMENTS.keySet()) {
                        message = message.replace(replacement, EmoteClue.STRING_REPLACEMENTS.get(replacement));
                    }
                    player.dialogue(new ItemDialogue().one(itemId, message));
                    return;
                }
            }
        }
        for (Item item : itemsToDeposit) {
            item.remove();
        }
        if (player.getStashUnits().containsKey(emoteClueData)) {
            player.getStashUnits().get(emoteClueData).addAll(itemsToDeposit);
        } else {
            player.getStashUnits().put(emoteClueData, itemsToDeposit);
        }
        player.sendMessage("You stash the items.");
        if (emoteClueData.type == ClueType.HARD)
            player.getTaskManager().doLookupByUUID(196, 1); // Fill a Hard S.T.A.S.H. Unit
        if (emoteClueData.type == ClueType.MEDIUM)
            player.getTaskManager().doLookupByUUID(119, 1); // Fill a Medium S.T.A.S.H. Unit
    }

    private static void search(Player player, EmoteClue.EmoteClueData emoteClueData) {
        List<Item> itemCon = player.getStashUnits().get(emoteClueData);
        if (itemCon != null && itemCon.size() > 1) {
            withdraw(player, emoteClueData, itemCon);
        } else {
            deposit(player, emoteClueData);
        }
    }

    public static void registerStashUnit(EmoteClue.EmoteClueData emoteClueData, Config config, int obj) {
        if (obj == -1 || config == null)
            return;
        /*
         * Registers the building of the stash unit, only done once per account.
         */
        ObjectAction.register(obj, "build", (player, object) -> build(player, emoteClueData, config));

        /*
         * Registers the filling and emptying of the stash unit items.
         */
        ObjectAction.register(obj, "search", (player, object) -> search(player, emoteClueData));
    }
}
