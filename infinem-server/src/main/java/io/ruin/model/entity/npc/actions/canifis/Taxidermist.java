package io.ruin.model.entity.npc.actions.canifis;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;

import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public class Taxidermist {

    public final static int TAXIDERMIST = 5418;

    private static void nevermind(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("Nevermind."),
                new NPCDialogue(npc, "No worries!")
        );
    }

    private static void stuffIt(Player player, NPC npc, StuffableItems stuffable){
        if (!player.getInventory().hasItem(995, 200000)){
            player.dialogue(
                    new NPCDialogue(npc, "Oh, looks like you don't have the coins!"),
                    new PlayerDialogue("Sorry, I will comeback when I have them.")
            );
            return;
        } else {
            player.getInventory().remove(995, 200000);
            player.getInventory().remove(stuffable.itemID, 1);
            player.getInventory().add(stuffable.stuffedItemID, 1);
            player.dialogue(
                    new NPCDialogue(npc, "Done!"),
                    new PlayerDialogue("Thanks.")
            );
        }
    }

    private static void findStuffables(Player player, NPC npc){
        Optional<StuffableItems> stuffOpt = StuffableItems.findStuffable(player);

        stuffOpt.ifPresent(stuffable -> {
            player.dialogue(
                    new NPCDialogue(npc, "Let me see here..."),
                    new NPCDialogue(npc, "Okay do you want me to stuff the " + stuffable.getStuffName() + "."),
                    new OptionsDialogue(
                            new Option("Yes!", () -> stuffIt(player, npc, stuffable)),
                            new Option("Nevermind.", () -> nevermind(player, npc))
                    ));
        });

        if(!stuffOpt.isPresent()) {
            player.dialogue(
                    new NPCDialogue(npc, "Let me see here..."),
                    new NPCDialogue(npc, "Looks like you don't have anything I can stuff."),
                    new PlayerDialogue("Okay, thanks anyways!"));
        }
    }

    static {
        NPCAction.register(TAXIDERMIST, "talk-to", (player, npc) ->{
            player.dialogue(
                    new NPCDialogue(npc, "Do you want me to stuff something for you?<br>It will only cost 200,000 coins."),
                    new PlayerDialogue("Uhhhhmmm...."),
                    new OptionsDialogue(
                            new Option("Yes!", () -> findStuffables(player, npc)),
                            new Option("Nevermind.", () -> nevermind(player, npc))
                    )
            );
        });
    }
}