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

    static {
        // Mistag travel
        NPCAction.register(MISTAG, "travel", (player, npc) -> Traveling.fadeTravel(player, new Position(3229, 9610)));
        // Mistag dialogue
        NPCAction.register(MISTAG, "talk-to", DorgeshKaanMine::mistagDialogue);
        // Kazgar travel
        NPCAction.register(KAZGAR, "travel", (player, npc) -> Traveling.fadeTravel(player, new Position(3312, 9612)));
    }
}
