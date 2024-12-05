package io.ruin.model.entity.npc.actions.animals;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.skills.Tool;

public class Sheep {

    private static final int[][] SHEEP = {
            {2693, 2691},
            {2694, 2692},
            {2695, 2691},
            {2696, 2692},
            {2697, 2691},
            {2698, 2692},
            {2699, 2691},
            {2787, 2691},
            {12986, 12987},
            {12988, 12989}
    };

    private static void shear(Player player, NPC npc, int replacement) {
        Item shears = player.getInventory().findItem(Tool.SHEARS);
        if (shears == null) {
            player.sendFilteredMessage("You need a set of shears to do this.");
            return;
        }

        if(player.getInventory().isFull()) {
            player.sendFilteredMessage("You need a free inventory slot to do this.");
            return;
        }

        player.startEvent(event -> {
            player.lock();
            player.animate(893);
            player.privateSound(761);
            event.delay(2);
            player.privateSound(2053);
            npc.forceText("Baa!");
            if (Random.rollDie(3, 1)) {
                player.sendFilteredMessage("The " + npc.getDef().name.toLowerCase() + " manages to get away from you.");
            } else {
                player.sendFilteredMessage("You get some wool.");
                player.getInventory().add(1737, 1);
                if (npc.getDef().name.equalsIgnoreCase("alpaca")) player.getTaskManager().doLookupByUUID(1119); // Shear an Alpaca
                World.startEvent(e -> {
                    int origId = npc.getId();
                    npc.transform(replacement);
                    e.delay(30);
                    npc.transform(origId);
                });
            }
            player.unlock();
        });
    }

    static {
        for (int[] sheep : SHEEP)
            NPCAction.register(sheep[0], "shear", (player, npc) -> shear(player, npc, sheep[1]));
    }
}
