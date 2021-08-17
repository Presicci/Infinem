package io.ruin.model.entity.npc.actions.animals;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/16/2021
 */
public class CowMilk {

    private static final int GILLIE_GROATS = 4628;

    private static void startDialogue(Player player) {
        player.dialogue(
                new NPCDialogue(GILLIE_GROATS, "Tee hee! You've never milked a cow before, have you?").animate(605),
                new PlayerDialogue("Erm... No. How could you tell?").animate(554),
                new NPCDialogue(GILLIE_GROATS, "Because you're spilling milk all over the floor. What a<br>waste! You need something to hold the milk.").animate(606),
                new PlayerDialogue("Ah yes, I really should have guessed that one, shouldn't<br>I?").animate(589),
                new NPCDialogue(GILLIE_GROATS, "You're from the city, aren't you... Try it again with an<br>empty bucket.").animate(606),
                new PlayerDialogue("Right, I'll do that.")
        );
    }

    static {
        ObjectAction.register("Dairy cow", "Milk", (player, npc) -> {
            if (player.getInventory().contains(Items.BUCKET, 1)) {
                player.startEvent(e -> {
                    player.lock();
                    player.privateSound(372, 1, 1);
                    player.animate(2305);
                    e.delay(7);
                    player.getInventory().remove(Items.BUCKET, 1);
                    player.getInventory().add(Items.BUCKET_OF_MILK, 1);
                    player.sendMessage("You milk the cow.");
                    player.unlock();
                });
            } else {
                startDialogue(player);
            }
        });
        ItemObjectAction.register(Items.BUCKET, "Dairy cow", (player, item, npc) -> {
            if (player.getInventory().contains(item)) {
                player.startEvent(e -> {
                    player.lock();
                    player.privateSound(372, 1, 1);
                    player.animate(2305);
                    e.delay(2);
                    player.getInventory().remove(item);
                    player.getInventory().add(Items.BUCKET_OF_MILK, 1);
                    player.sendMessage("You milk the cow.");
                    player.unlock();
                });
            } else {
                startDialogue(player);
            }
        });

        ObjectAction.register("Dairy cow", "Steal-cowbell", (player, npc) -> {
            if (player.isStunned()) {
                return;
            }
            if (Random.rollDie(3)) {
                player.startEvent(e -> {
                    player.stun(5, true, false);
                    player.sendMessage("The cow kicks you and stuns you!");
                });
            } else {
                player.startEvent(e -> {
                    player.lock();
                    player.animate(832);
                    e.delay(1);
                    player.privateSound(2581, 1, 1);
                    player.sendMessage("You steal a cowbell.");
                    player.getInventory().addOrDrop(Items.COWBELLS, 1);
                    player.getStats().addXp(StatType.Thieving, 2.0, true);
                    player.unlock();
                });
            }
        });
    }
}
