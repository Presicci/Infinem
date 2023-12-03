package io.ruin.model.entity.npc.actions.animals;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.actions.ItemNPCAction;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
public class PetDog {
    private static final int[] DOGS = { 9195, 5920, 7771, 111 };
    private static final List<Integer> MEAT = Arrays.asList(2132, 2134, 2136, 10816, 2142);
    private static final List<Integer> BONES = Arrays.asList(526, 530, 2859, 532, 10976, 10977, 3125, 3123, 534, 536, 4812, 4834, 6812, 6729, 11943, 22124, 22780, 22783, 22786, 3179, 3180, 3181, 3182, 3183, 3185, 3186);

    static {
        for (int npcId : DOGS) {
            NPCAction.register(npcId, "pet", ((player, npc) -> {
                player.animate(827);
                player.sendMessage("You pet the dog...");
            }));
            ItemNPCAction.register(npcId, ((player, item, npc) -> {
                if (BONES.contains(item.getId())) {
                    player.startEvent(e -> {
                        player.animate(827);
                        e.delay(1);
                        player.getInventory().remove(item.getId(), 1);
                        npc.forceText("Woof woof!");
                        player.dialogue(new MessageDialogue("You give the dog some nice bones.<br>It happily gnaws on them."));
                    });
                }
                else if (MEAT.contains(item.getId())) {
                    player.startEvent(e -> {
                        player.animate(827);
                        e.delay(1);
                        player.getInventory().remove(item.getId(), 1);
                        npc.forceText("Woof woof!");
                        player.dialogue(new MessageDialogue("You give the dog a nice piece of meat.<br>It gobbles it up."));
                    });
                }
                else {
                    player.dialogue(new MessageDialogue("The dog doesn't seem interested in that."));
                }
            }));
        }
    }
}
