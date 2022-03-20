package io.ruin.model.entity.npc.actions.varrock;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

public class Gertrude {

    static {
        NPCAction.register(3500, "talk-to", (player, npc) -> talkTo(player));
    }

    public static void talkTo(Player player) {
        player.dialogue(
                new NPCDialogue(3500, "Hello. What can I do for you?"),
                new OptionsDialogue("Select an option",
                        new Option("I'd like to purchase a cat.", () -> purchaseCat(player)),
                        new Option("Nevermind")
                )
        );
    }

    public static void purchaseCat(Player player) {
        player.dialogue(
                new PlayerDialogue("I'd like to purchase a cat."),
                new NPCDialogue(3500, "Certainly, that will cost 5000 coins."),
                new OptionsDialogue("Select an option",
                        new Option("Sure", () -> {
                            if (player.getInventory().contains(995, 5000)) {
                                player.getInventory().remove(995, 5000);
                                player.getInventory().add(1555 + Random.get(0, 5), 1);
                                player.sendMessage("You purchase a kitten for 5000 gp.");
                            } else {
                                player.sendMessage("You do not have enough money to purchase a kitten.");
                            }
                        }),
                        new Option("Nevermind")
                )
        );
    }

}