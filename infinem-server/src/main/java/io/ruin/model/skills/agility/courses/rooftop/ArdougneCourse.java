package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.model.skills.agility.Agility;
import io.ruin.utility.Color;
import io.ruin.model.content.tasksystem.tasks.TaskCategory;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.TricksterAgility;
import io.ruin.model.skills.agility.courses.AgilityPet;
import io.ruin.model.skills.agility.courses.MarkOfGrace;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class ArdougneCourse {

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(2670, 3310, 3), true).getObject(15609, 10, 3),
            Tile.get(new Position(2661, 3318, 3), true).getObject(26635, 22, 3),
            Tile.get(new Position(2653, 3317, 3), true).getObject(15610, 10, 2),
            Tile.get(new Position(2653, 3308, 3), true).getObject(15611, 10, 2),
            Tile.get(new Position(2654, 3300, 3), true).getObject(28912, 11, 1),
            Tile.get(new Position(2656, 3296, 3), true).getObject(15612, 10, 1)
    };

    private static final List<Position> MARK_SPAWNS = Arrays.asList(new Position(2671, 3304, 3), new Position(2663, 3318, 3), new Position(2654, 3318, 3), new Position(2653, 3313, 3), new Position(2653, 3302, 3));

    static {
        /**
         * Wall climb
         */
        ObjectAction.register(15608, "climb-up", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 90, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(737, 15);
            e.delay(1);
            p.getMovement().teleport(2673, 3298, 1);
            p.animate(737);
            e.delay(1);
            p.getMovement().teleport(2673, 3298, 2);
            p.animate(737);
            e.delay(1);
            p.getMovement().teleport(2671, 3299, 3);
            p.animate(2588);
            p.getStats().addXp(StatType.Agility, 43, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            e.delay(1);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[0]);
        }));
        /**
         * Jump down roof
         */
        ObjectAction.register(15609, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            p.privateSound(2462, 0, 10);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2667, 3311, 1);
            e.delay(1);
            p.animate(2586, 15);
            p.privateSound(2462, 0, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2665, 3315, 1);
            e.delay(1);
            p.animate(2583, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2665, 3318, 3);
            p.getStats().addXp(StatType.Agility, 65, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            e.delay(1);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[1]);
        }));
        /**
         * Plank
         */
        ObjectAction.register(26635, "walk-on", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2495, 4, 0);
            p.stepAbs(2656, 3318, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 50, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[2]);
        }));
        /**
         * Gap
         */
        ObjectAction.register(15610, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            p.privateSound(2462, 0, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2653, 3314, 3);
            p.getStats().addXp(StatType.Agility, 21, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            e.delay(1);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[3]);
        }));
        /**
         * Gap
         */
        ObjectAction.register(15611, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(7133, 15);
            p.privateSound(2462, 0, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2651, 3309, 3);
            p.getStats().addXp(StatType.Agility, 28, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            e.delay(1);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[4]);
        }));
        /**
         * Steep roof
         */
        ObjectAction.register(28912, "balance-across", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(753);
            p.getAppearance().setCustomRenders(Renders.AGILITY_WALL);
            e.delay(1);
            p.stepAbs(2654, 3299, StepType.FORCE_WALK);
            p.stepAbs(2656, 3297, StepType.FORCE_WALK);
            e.waitForMovement(p);
            e.delay(1);
            p.getAppearance().removeCustomRenders();
            p.animate(759);
            p.getStats().addXp(StatType.Agility, 57, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            e.delay(1);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[5]);
        }));
        /**
         * Gap
         */
        ObjectAction.register(15612, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            p.privateSound(2462, 0, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2658, 3298, 1);
            e.delay(2);
            p.stepAbs(2661, 3298, StepType.FORCE_WALK);
            e.waitForTile(p, 2661, 3298);
            e.delay(2);
            p.animate(741);
            p.getMovement().force(2, -1, 0, 0, 15, 30, Direction.EAST);
            e.delay(1);
            p.getMovement().teleport(2663, 3297, 1);
            e.delay(1);
            p.stepAbs(2666, 3297, StepType.FORCE_WALK);
            e.waitForTile(p, 2666, 3297);
            e.delay(3);
            p.animate(741);
            p.getMovement().force(1, 0, 0, 0, 15, 30, Direction.EAST);
            e.delay(1);
            p.getMovement().teleport(2667, 3297, 1);
            p.animate(2586);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2668, 3297, 0);
            p.getStats().addXp(StatType.Agility, 529, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            AgilityPet.rollForPet(p, 34440);
            int laps = PlayerCounter.ARDOUGNE_ROOFTOP.increment(p, 1);
            p.getTaskManager().doLookupByCategory(TaskCategory.ROOFTOP, "ardy");
            Agility.completeLap(p);
            MarkOfGrace.rollMark(p, 90, MARK_SPAWNS);
            if (!p.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                p.sendFilteredMessage("Your Ardougne Rooftop lap count is: " + Color.RED.wrap(laps + "") + ".");
            e.delay(1);
            p.unlock();
        }));
    }
}
