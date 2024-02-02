package io.ruin.model.skills.slayer;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.GhostSpeak;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/28/2023
 */
public enum TaskOnlyNPC {
    NONE(-1, ""),
    GENERIC(-1, "You need to be on a slayer task to attack this monster."),
    JELLY(7518, "Naughty human! You not hunting the Kurask! Jelly's Kurasks only for people on Slayer tasks."),
    SLIEVE(7653, "Hey, go and train somewhere else! I'm not having people mess with my dragons unless a Slayer Master's told them to do it."),
    BUGGY(491, "wooooo ooo woo-woo"),
    BRIEVE(7654, "<col=0040ff>*cough*</col> that isn't your assignment <col=0040ff>*wheeze*</col>"),
    LIEVE(7412, "Oi, I'm Lieve, and I say leave it!"),
    LIEVE_KRAKEN(7412, "Oi, I'm Lieve McCracken and I say leave m' Kraken alone!"),
    DUSTY_ALIV(11161, "Oi! Only people with a Slayer task can slay in here!"),
    PEEVE(11237, "wooooo ooo woo-woo"),
    RAULYN(5790, "wooooo ooo woo-woo"),
    DISCIPLE_OF_YAMA(7669, "My master wishes you not to kill his minions without an agenda. You may only slay within the Chasm that which you have been assigned by a Slayer Master."),
    HIEVE(7667, "I'm sure that's not what you're meant to be slaying.");

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
            if (this == GENERIC) {
                player.dialogue(new MessageDialogue(dialogue));
            } else if (this == BUGGY && GhostSpeak.canSpeak(player)) {
                player.dialogue(new NPCDialogue(npcId, "I didn't... create this cave for... you to do that... sorry, it's not for you."));
            } else if (this == RAULYN && GhostSpeak.canSpeak(player)) {
                player.dialogue(new NPCDialogue(npcId, "I don't think that's what you're meant to be slaying."));
            } else if (this == PEEVE && GhostSpeak.canSpeak(player)) {
                player.dialogue(new NPCDialogue(npcId, "Sneaky one! Don't touch these unless you have a Slayer task to kill them."));
            } else {
                player.dialogue(new NPCDialogue(npcId, dialogue));
            }
        }
    }
}
