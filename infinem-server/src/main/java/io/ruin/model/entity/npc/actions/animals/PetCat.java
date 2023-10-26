package io.ruin.model.entity.npc.actions.animals;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/26/2023
 */
public class PetCat {
    private static final int[] CATS = { 8594 };

    static {
        for (int npcId : CATS) {
            NPCAction.register(npcId, "pet", ((player, npc) -> {
                player.startEvent(e -> {
                    player.animate(827);
                    player.sendMessage("You pet the cat...");
                    e.delay(1);
                    if (player.getEquipment().hasId(Items.CATSPEAK_AMULET)) {
                        player.dialogue(new NPCDialogue(npc, "Hmm... Oh yes, that's purrfect....."));
                    } else {
                        player.sendMessage("The cat softly purrs between snores.");
                    }
                });
            }));
        }
    }
}
