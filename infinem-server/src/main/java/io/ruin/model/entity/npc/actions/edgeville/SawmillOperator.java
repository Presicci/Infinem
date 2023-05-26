package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.skills.Tool;

import static io.ruin.cache.ItemID.COINS_995;

public class SawmillOperator {
    public enum Plank {
        WOOD(100, 1511, 960),
        OAK(250, 1521, 8778),
        TEAK(500, 6333, 8780),
        MAHOGANY(1500, 6332, 8782);

        public final int cost, woodId, plankId;

        Plank(int cost, int woodId, int plankId) {
            this.cost = cost;
            this.woodId = woodId;
            this.plankId = plankId;
        }

        public static Plank getFromLog(int logId) {
            for (Plank p : values())
                if (p.woodId == logId)
                    return p;
            return null;
        }

        private static void skillDialogue(Player player) {
            SkillDialogue.make(player,
                    new SkillItem(WOOD.woodId).addAction((p, amount, event) -> convertPlank(p, WOOD, amount)),
                    new SkillItem(OAK.woodId).addAction((p, amount, event) -> convertPlank(p, OAK, amount)),
                    new SkillItem(TEAK.woodId).addAction((p, amount, event) -> convertPlank(p, TEAK, amount)),
                    new SkillItem(MAHOGANY.woodId).addAction((p, amount, event) -> convertPlank(p, MAHOGANY, amount)));
        }

        private static void convertPlank(Player player, Plank plank, int amount) {
            player.closeInterface(InterfaceType.MAIN);
            player.startEvent(event -> {
                int amt = amount;
                Item wood = player.getInventory().findItem(plank.woodId);
                if (wood == null) {
                    player.sendFilteredMessage("You don't have any logs.");
                    return;
                }
                Item coins = player.getInventory().findItem(COINS_995);
                if (coins == null || coins.getAmount() < plank.cost) {
                    player.dialogue(new NPCDialogue(3101, "You don't have enough coins."));
                    return;
                }
                while (amt-- > 0) {
                    wood = player.getInventory().findItem(plank.woodId);
                    if (wood == null) {
                        return;
                    }
                    coins = player.getInventory().findItem(COINS_995);
                    if (coins == null || coins.getAmount() < plank.cost) {
                        player.dialogue(new NPCDialogue(3101, "You'll need to bring me some more coins."));
                        return;
                    }
                    player.getInventory().remove(COINS_995, plank.cost);
                    player.getInventory().remove(plank.woodId, 1);
                    player.getInventory().add(plank.plankId, 1);
                    player.getTaskManager().doLookupByUUID(43, 1);  // Turn a Log Into a Plank
                }
            });

        }

        static {
            NPCAction.register(3101, "talk-to", (player, npc) -> player.dialogue(
                    new NPCDialogue(npc, "Do you want me to make some planks for you? Or would you be interested in some other housing supplies?"),
                    new OptionsDialogue(
                            new Option("Planks please!", () -> player.dialogue(
                                    new PlayerDialogue("Planks please!"),
                                    new NPCDialogue(npc, "What kind of planks do you want?"),
                                    new ActionDialogue(() -> skillDialogue(player))
                            )),
                            new Option("What kind of planks can you make?", () -> player.dialogue(
                                    new PlayerDialogue("What kind of planks can you make?"),
                                    new NPCDialogue(npc, "I can make planks from wood, oak, teak and mahogany. I don't make planks from other woods " +
                                            "as they're no good for making furniture."),
                                    new NPCDialogue(npc, "Wood and oak are all over the place, but teak and mahogany can only be found in a few places " +
                                            "like Karamja and Etceteria.")
                            )),
                            new Option("Can I buy some housing supplies?", () -> npc.openShop(player)),
                            new Option("Nothing, thanks.", () -> player.dialogue(
                                    new PlayerDialogue("Nothing, thanks."),
                                    new NPCDialogue(npc, "Well come back when you want some. You can't get good quality planks anywhere but here!")))
                    )
            ));
            NPCAction.register(3101, "buy-plank", (player, npc) -> skillDialogue(player));
        }
    }
}
