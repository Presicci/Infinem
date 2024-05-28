package io.ruin.model.content.tasksystem.areas.rewards;

import io.ruin.model.content.tasksystem.areas.AreaReward;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.object.actions.impl.Ladder;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/26/2024
 */
public class AncientPyramid {

    private static void passDoor(Player player, GameObject object) {
        if (!AreaReward.ANCIENT_MAGIC.checkReward(player, "enter the ancient pyramid.")) return;
        player.startEvent(e -> {
            player.lock();
            e.delay(1);
            Position target = player.getPosition().relative(0, player.getAbsY() > object.y ? -1 : 1);
            player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
            e.delay(1);
            player.unlock();
        });
    }

    private static void passChamberDoor(Player player, GameObject object) {
        player.startEvent(e -> {
            player.lock();
            e.delay(1);
            Position target = player.getPosition().relative(0, player.getAbsY() > object.y ? -2 : 2);
            player.stepAbs(target.getX(), target.getY(), StepType.FORCE_WALK);
            e.delay(1);
            player.unlock();
        });
    }

    static {
        ObjectAction.register(6547, 3232, 2899, 0, "open", AncientPyramid::passDoor);
        ObjectAction.register(6545, 3233, 2899, 0, "open", AncientPyramid::passDoor);
        ObjectAction.register(6497, 3233, 2897, 0, "climb-down", (player, obj) -> {
            if (!AreaReward.ANCIENT_MAGIC.checkReward(player, "enter the ancient pyramid.")) return;
            Ladder.climb(player, new Position(2913, 4954, 3), false, true, false);
        });
        ObjectAction.register(6481, 3233, 2888, 0, "enter", (player, obj) -> {
            if (!AreaReward.ANCIENT_MAGIC.checkReward(player, "enter the ancient pyramid.")) return;
            Traveling.fadeTravel(player, 3233, 9319, 0);
        });
        ObjectAction.register(6555, 3233, 9324, 0, "open", AncientPyramid::passChamberDoor);
        ObjectAction.register(6553, 3234, 9324, 0, "open", AncientPyramid::passChamberDoor);
        ObjectAction.register(6550, 3233, 9312, 0, 1, (player, obj) -> Traveling.fadeTravel(player, new Position(3233, 2909, 0)));
    }
}
