package io.ruin.model.map.object.actions.impl;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/26/2024
 */
public class GnomeVillage {

    static {
        NPCAction toMaze = (player, npc) -> {
            if (!player.getTaskManager().hasCompletedTask(564)) {  // Make it through the Tree Gnome Village Maze
                player.dialogue(new NPCDialogue(npc, "You should find your way through the first time on your own, then I can help you."));
                return;
            }
            player.startEvent(event -> {
                player.lock();
                player.sendMessage("Elkoy leads you through the maze.");
                player.getPacketSender().fadeOut();
                event.delay(2);
                if (player.getAbsY() > 3174) {
                    player.getMovement().teleport(2515, 3160, 0);
                } else {
                    player.getMovement().teleport(2503, 3192, 0);
                }
                event.delay(2);
                player.getPacketSender().fadeIn();
                player.unlock();
                player.dialogue(new NPCDialogue(npc,"Here we are."));
            });
        };

        NPCAction.register(4968, "follow", toMaze);
        NPCAction.register(4968, "talk-to", (player, npc) -> {
            if (!player.getTaskManager().hasCompletedTask(564)) {  // Make it through the Tree Gnome Village Maze
                player.dialogue(new NPCDialogue(npc, "You should find your way through the first time on your own, then I can help you."));
                return;
            }
            player.dialogue(new NPCDialogue(npc, "I can lead you through the maze, if you'd like."),
                    new OptionsDialogue(
                            new Option("Yes, please.", () -> toMaze.handle(player, npc)),
                            new Option("No thanks.")
                    )
            );
        });
    }
}
