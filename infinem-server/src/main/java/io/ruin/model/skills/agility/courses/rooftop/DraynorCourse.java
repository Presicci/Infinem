package io.ruin.model.skills.agility.courses.rooftop;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
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

public class DraynorCourse {

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(3098, 3277, 3), true).getObject(11405, 22, 3),
            Tile.get(new Position(3092, 3276, 3), true).getObject(11406, 22, 2),
            Tile.get(new Position(3089, 3264, 3), true).getObject(11430, 10, 0),
            Tile.get(new Position(3088, 3256, 3), true).getObject(11630, 10, 0),
            Tile.get(new Position(3095, 3255, 3), true).getObject(11631, 10, 0),
            Tile.get(new Position(3102, 3261, 3), true).getObject(11632, 10, 0)
    };

    private static final List<Position> MARK_SPAWNS = Arrays.asList(
            new Position(3099, 3280, 3),
            new Position(3089, 3274, 3),
            new Position(3094, 3266, 3),
            new Position(3088, 3259, 3),
            new Position(3092, 3255, 3),
            new Position(3099, 3257, 3),
            new Position(3098, 3259, 3)
    );

    static {
        /*
         * Rough wall
         */
        ObjectAction.register(11404, "climb", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 10, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(828, 15);
            e.delay(2);
            p.getMovement().teleport(3102, 3279, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 5.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[0]);
        }));

        /*
         * Tightrope
         */
        ObjectAction.register(11405, "cross", (p, obj) -> p.startEvent(e -> {
            if (p.getMovement().hasMoved()) p.sendMessage("moved");
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3090, 3277, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.waitForTile(p, 3090, 3277);
            p.getAppearance().removeCustomRenders();
            p.stepAbs(3090, 3276, StepType.FORCE_WALK);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 8.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[1]);
        }));

        /*
         * Tightrope
         */
        ObjectAction.register(11406, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3092, 3276, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.stepAbs(3092, 3266, StepType.FORCE_WALK);
            e.waitForTile(p, 3092, 3267);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 7.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[2]);
        }));

        /*
         * Narrow wall
         */
        ObjectAction.register(11430, "balance", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(753);
            p.getAppearance().setCustomRenders(Renders.AGILITY_WALL);
            p.stepAbs(3089, 3262, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.stepAbs(3088, 3261, StepType.FORCE_WALK);
            e.waitForTile(p, 3088, 3261);
            p.getAppearance().removeCustomRenders();
            p.animate(759);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 7.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[3]);
        }));

        /*
         * Wall
         */
        ObjectAction.register(11630, "jump-up", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.stepAbs(3088, 3256, StepType.FORCE_WALK);
            p.animate(2583, 20);
            p.getMovement().force(0, -1, 0, 0, 25, 30, Direction.SOUTH);
            e.delay(1);
            p.animate(2585);
            e.delay(1);
            p.stepAbs(3088, 3255, StepType.FORCE_WALK);
            p.getMovement().force(0, -1, 0, 0, 17, 26, Direction.SOUTH);
            p.getStats().addXp(StatType.Agility, 10.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[4]);
        }));

        /*
         * Gap
         */
        ObjectAction.register(11631, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3096, 3256, 3);
            p.animate(2588);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 4.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[5]);
        }));

        /*
         * Crate jump
         */
        ObjectAction.register(11632, "climb-down", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3102, 3261, 1);
            p.animate(2588);
            e.delay(1);
            p.resetAnimation();
            e.delay(1);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(3103, 3261, 0);
            p.animate(2588);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 79.0, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            int laps = PlayerCounter.DRAYNOR_ROOFTOP.increment(p, 1);
            p.getTaskManager().doLookupByCategory(TaskCategory.ROOFTOP, "draynor");
            AgilityPet.rollForPet(p, 33005);
            MarkOfGrace.rollMark(p, 10, MARK_SPAWNS);
            if (!p.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                p.sendFilteredMessage("Your Draynor Rooftop lap count is: " + Color.RED.wrap(laps + "") + ".");
            p.unlock();
        }));
    }
}
