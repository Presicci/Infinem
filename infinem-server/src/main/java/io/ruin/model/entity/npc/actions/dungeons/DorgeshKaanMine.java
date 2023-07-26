package io.ruin.model.entity.npc.actions.dungeons;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.map.Position;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/1/2022
 */
public class DorgeshKaanMine {

    private static final int MISTAG = 7298, KAZGAR = 7300, IRON_ORE = Items.IRON_ORE, SILVER_ORE = Items.SILVER_ORE;
    private static final int[] ACTIVE_MINERS = { 5336, 5337, 5338, 5339 };
    private static final int[] PASSIVE_MINERS = { 5330, 5331, 5332, 5333, 5334, 5335 };

    private static void sellOre(Player player, NPC npc, int itemId) {
        int amount = player.getInventory().getAmount(itemId);
        if (amount <= 0) {
            player.dialogue(
                    new MessageDialogue("You rummage around in your bags and fail to find any ore."),
                    new PlayerDialogue("I must've left it in my other pants.")
            );
        } else {
            int payout = (itemId == SILVER_ORE ? 60 : 13) * amount;
            player.getInventory().remove(itemId, amount);
            player.getInventory().add(995, payout);
            player.dialogue(
                    new ItemDialogue().one(itemId, "The goblin quickly devours every last rock. Food must be hard to come by down here..."),
                    new NPCDialogue(npc.getId(), "Please! More!")
            );
        }
    }

    private static void mistagDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc.getId(), "Greetings adventurer, do you have any iron or silver ore for sale?"),
                new OptionsDialogue(
                        new Option("Iron ore", () -> sellOre(player, npc, IRON_ORE)),
                        new Option("Silver ore", () -> sellOre(player, npc, SILVER_ORE)),
                        new Option("I don't trade with goblins...")
                )
        );
    }

    private static void kazgarDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc.getId(), "Greetings friend, would you like me to escort you to the mines?"),
                new OptionsDialogue(
                        new Option("Yes, please.", () -> Traveling.fadeTravel(player, new Position(3312, 9612))),
                        new Option("No, thank you.")
                )
        );
    }

    private static void minerDialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc.getId(), "Won't you help us mine, adventurer? Master Mistag requires more and more of us each day. " +
                        "We don't even produce anything at our forge!")
        );
    }

    static {
        // Mistag travel
        NPCAction.register(MISTAG, "follow", (player, npc) -> Traveling.fadeTravel(player, new Position(3229, 9610)));
        // Mistag dialogue
        NPCAction.register(MISTAG, "talk-to", DorgeshKaanMine::mistagDialogue);
        // Kazgar travel
        NPCAction.register(KAZGAR, "follow", (player, npc) -> Traveling.fadeTravel(player, new Position(3312, 9612)));
        // Kazgar dialogue
        NPCAction.register(KAZGAR, "talk-to", DorgeshKaanMine::kazgarDialogue);
        // Miners
        for (int id : PASSIVE_MINERS) {
            NPCAction.register(id, "talk-to", DorgeshKaanMine::minerDialogue);
        }
        for (int id : ACTIVE_MINERS) {
            NPCAction.register(id, "talk-to", (player, npc) -> {
                player.dialogue(
                        new MessageDialogue("This goblin is too busy mining to pay any attention to you.")
                );
            });
        }
    }
}
