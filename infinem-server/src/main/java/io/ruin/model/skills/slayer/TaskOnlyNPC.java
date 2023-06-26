package io.ruin.model.skills.slayer;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public enum TaskOnlyNPC {
    NONE(-1, ""),
    JELLY(7518, "Naughty human! You not hunting the Kurask! Jelly's Kurasks only for people on Slayer tasks."),
    SLIEVE(7653, "Hey, go and train somewhere else! I'm not having people mess with my dragons unless a Slayer Master's told them to do it."),
    BUGGY(491, "wooooo ooo woo-woo"),
    BRIEVE(7654, "<col=0040ff>*cough*</col> that isn't your assignment <col=0040ff>*wheeze*</col>");

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
            if (this == BUGGY && player.getEquipment().getId(Equipment.SLOT_AMULET) == Items.GHOSTSPEAK_AMULET) {
                player.dialogue(new NPCDialogue(npcId, "I didn't... create this cave for... you to do that... sorry, it's not for you."));
            } else {
                player.dialogue(new NPCDialogue(npcId, dialogue));
            }
        }
    }
}
