package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.courses.MarkOfGrace;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/3/2022
 */
public class PollnivneachCourse {
    public static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(3346, 2968, 1),
            new Position(3354, 2974, 1),
            new Position(3361, 2993, 2)
    );

    static {
        // Basket
        ObjectAction.register(14935, "climb-on", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2583);
            p.privateSound(2468);
            e.delay(1);
            p.getMovement().teleport(3351, 2962, 1);
            p.animate(2588);
            e.delay(1);
            p.animate(2583);
            p.privateSound(2468);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(3351, 2964, 1);
            p.getStats().addXp(StatType.Agility, 10, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Market stall
        Tile.getObject(14936, 3349, 2970, 1).skipReachCheck = p -> p.equals(3350, 2968, 1) || p.equals(3349, 2968, 1);
        ObjectAction.register(14936, "jump-on", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995);
            p.privateSound(1936);
            e.delay(1);
            p.animate(1603);
            e.delay(1);
            p.getMovement().teleport(3350, 2971, 2);
            e.delay(3);
            p.animate(1603);
            e.delay(1);
            p.getMovement().teleport(3352, 2973, 1);
            p.privateSound(2455);
            p.getStats().addXp(StatType.Agility, 45, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Banner
        Tile.getObject(14937, 3356, 2978, 1).skipReachCheck = p -> p.equals(3355, 2976, 1);
        ObjectAction.register(14937, "grab", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995);
            e.delay(1);
            p.animate(1603);
            e.delay(1);
            p.getMovement().teleport(3357, 2977, 2);
            p.animate(1118);
            e.delay(2);
            p.getMovement().teleport(3360, 2977, 1);
            p.animate(2588);
            p.privateSound(2455);
            p.face(Direction.EAST);
            p.getStats().addXp(StatType.Agility, 65, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Gap
        Tile.getObject(14938, 3363, 2976, 1).skipReachCheck = p -> p.equals(3362, 2977, 1);
        ObjectAction.register(14938, "leap", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995);
            e.delay(1);
            p.animate(1603);
            e.delay(1);
            p.getMovement().teleport(3365, 2976, 2);
            p.animate(2585);
            e.delay(2);
            p.getMovement().teleport(3366, 2976, 1);
            p.face(Direction.EAST);
            p.getStats().addXp(StatType.Agility, 35, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Tree
        Tile.getObject(14939, 3367, 2977, 1).skipReachCheck = p -> p.equals(3367, 2976, 1) || p.equals(3368, 2976, 1) || p.equals(3369, 2976, 1);
        ObjectAction.register(14939, "jump-to", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995);
            e.delay(1);
            p.animate(1603);
            e.delay(1);
            p.getMovement().teleport(3368, 2978, 2);
            p.animate(1122);
            p.privateSound(2468);
            e.delay(2);
            p.getMovement().teleport(3368, 2980, 2);
            p.animate(1124);
            p.privateSound(2459);
            e.delay(2);
            p.getMovement().teleport(3368, 2982, 1);
            p.animate(2588);
            p.privateSound(2455);
            p.face(Direction.NORTH);
            p.getStats().addXp(StatType.Agility, 75, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Rought wall
        ObjectAction.register(14940, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.face(Direction.NORTH);
            p.animate(828);
            e.delay(1);
            p.getMovement().teleport(3365, 2983, 2);
            p.getStats().addXp(StatType.Agility, 5, true);
            p.unlock();
        }));

        Position monkeyBarsDest = new Position(3358, 2991, 2);
        Position monkeyBarsStart = new Position(3358, 2984, 2);

        // Monkeybars
        ObjectAction.register(14941, "cross", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            e.path(p, monkeyBarsStart);
            p.face(monkeyBarsDest.getX(), monkeyBarsDest.getY());
            e.delay(1);
            p.animate(742);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.MONKEY_BARS);
            int stepsNeeded = obj.getPosition().distance(monkeyBarsDest);
            for (int step = 0; step < stepsNeeded; step++) {
                p.stepAbs(monkeyBarsDest.getX(), monkeyBarsDest.getY(), StepType.FORCE_WALK);
                p.privateSound(2451, 2, 0);
                e.delay(1);
            }
            e.delay(1);
            p.animate(743);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 55, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Tree
        Tile.getObject(14944, 3359, 2996, 2).skipReachCheck = p -> p.equals(3359, 2995, 2) || p.equals(3360, 2995, 2);
        ObjectAction.register(14944, "jump-on", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(1995);
            e.delay(1);
            p.animate(1603);
            p.privateSound(1936);
            e.delay(1);
            p.getMovement().teleport(3360, 2997, 2);
            p.animate(1603);
            e.delay(1);
            p.getMovement().teleport(3359, 3000, 2);
            p.animate(2588);
            p.privateSound(1936);
            p.getStats().addXp(StatType.Agility, 60, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
        }));

        // Drying line
        Tile.getObject(14945, 3363, 3000, 2).skipReachCheck = p -> p.equals(3362, 3002, 2);
        ObjectAction.register(14945, "jump-to", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 70, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(741);
            p.privateSound(2461);
            e.delay(1);
            p.getMovement().teleport(3363, 3000, 2);
            e.delay(1);
            p.animate(2586);
            p.privateSound(2462);
            e.delay(1);
            p.getMovement().teleport(3363, 2998, 0);
            p.getStats().addXp(StatType.Agility, 540, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            int laps = PlayerCounter.POLLNIVNEACH_ROOFTOP.increment(p, 1);
            MarkOfGrace.rollMark(p, 70, MARK_SPAWNS);
            if (!p.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                p.sendFilteredMessage("Your Pollnivneach Rooftop lap count is: " + Color.RED.wrap(laps + "") + ".");
            p.unlock();
        }));

    }
}
