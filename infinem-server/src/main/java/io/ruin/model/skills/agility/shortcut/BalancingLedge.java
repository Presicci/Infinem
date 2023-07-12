package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum BalancingLedge {

    WEISS_DOCK_1(68, 1, Position.of(2857, 3961), true),
    WEISS_DOCK_2(68, 1, Position.of(2853, 3961), false),
    YANILLE_DUNGEON_ENTRANCE_1(40, 1, Position.of(2580, 9512), false),
    YANILLE_DUNGEON_ENTRANCE_2(40, 1, Position.of(2580, 9520), true),
    ;

    private final int level;
    private final double exp;
    private final Position to;
    private final boolean reverse;

    public void traverse(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, level, "walk-across"))
            return;
        player.startEvent((event) -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            Direction dir = Direction.getDirection(object.getPosition(), to);
            player.face(dir);
            event.delay(1);
            player.animate(reverse ? 753 : 752);
            player.stepAbs(object.getPosition().getX(), object.getPosition().getY(), StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(reverse ? Renders.AGILITY_WALL : Renders.AGILITY_JUMP);
            event.delay(2);
            int stepsNeeded = player.getPosition().distance(to);
            for (int step = 0; step < stepsNeeded; step++) {
                player.stepAbs(to.getX(), to.getY(), StepType.FORCE_WALK);
                player.privateSound(2451, 2, 0);
                event.delay(1);
            }
            player.animate(reverse ? 759 : 758);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, exp, true);
            player.unlock();
        });
    }

}