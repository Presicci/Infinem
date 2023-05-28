package io.ruin.model.skills.slayer;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public enum TaskOnlyNPC {
    NONE(-1, ""),
    JELLY(7518, "Naughty human! You not hunting the Kurask! Jelly's Kurasks only for people on Slayer tasks.");

    private final int npcId;
    private final String dialogue;

    TaskOnlyNPC(int npcId, String dialogue) {
        this.npcId = npcId;
        this.dialogue = dialogue;
    }

    public void sendDialogue(Player player) {
        if (npcId == -1) {
            player.sendMessage("You need to be on a slayer task to attack this monster.");
        } else {
            player.dialogue(
                    new NPCDialogue(npcId, dialogue)
            );
        }
    }
}
