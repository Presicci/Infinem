package io.ruin.model.map.object.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.skills.mining.Pickaxe;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/17/2024
 */
public class RockSlide {

    private static void mine(Player player, GameObject object) {
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine this rock. You do not have a pickaxe which " +
                    "you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }
        player.startEvent(e -> {
            player.lock();
            player.animate(pickaxe.regularAnimationID);
            e.delay(4);
            player.unlock();
            object.setId(-1);
            player.getStats().addXp(StatType.Mining, 1, true);
            player.sendFilteredMessage("You mine the rocks.");
            player.animate(-1);
            World.startEvent(we -> {
                we.delay(120);
                object.setId(object.originalId);
            });
        });
    }

    static {
        ObjectAction.register(2634, 2838, 3517, 0, "mine", RockSlide::mine);
        ObjectAction.register(2634, 2838, 3517, 0, "investigate", (player, obj) -> player.dialogue(new MessageDialogue("The rocks look very loose, they could easily be broken apart with a pickaxe.")));
    }
}
