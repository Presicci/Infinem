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

public class SeersCourse {

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(2720, 3492, 3), true).getObject(14928, 10, 0),
            Tile.get(new Position(2710, 3489, 2), true).getObject(14932, 22, 2),
            Tile.get(new Position(2710, 3476, 2), true).getObject(14929, 10, 0),
            Tile.get(new Position(2700, 3469, 3), true).getObject(14930, 10, 0),
            Tile.get(new Position(2703, 3461, 2), true).getObject(14931, 10, 0)
    };

    private static final List<Position> MARK_SPAWNS = Arrays.asList(new Position(2726, 3492, 3), new Position(2728, 3495, 3), new Position(2707, 3493, 2), new Position(2708, 3489, 2), new Position(2712, 3481, 2), new Position(2710, 3478, 2), new Position(2710, 3472, 3), new Position(2702, 3474, 3), new Position(2698, 3462, 2));

    static {
        /**
         * Wall climb
         */
        ObjectAction.register(14927, "climb-up", (p, obj) -> p.startEvent(e -> {
            if (!p.getStats().check(StatType.Agility, 60, "attempt this"))
                return;
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(737, 15);
            e.delay(2);
            p.getMovement().teleport(2729, 3488, 1);
            p.animate(1118);
            e.delay(2);
            p.getMovement().teleport(2729, 3491, 3);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 45, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[0]);
        }));
        /**
         * Rooftop jump
         */
        ObjectAction.register(14928, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2462, 1, 15);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(2719, 3495, 2);
            p.animate(2588);
            e.delay(1);
            p.privateSound(2462, 1, 15);
            p.animate(2586, 15);
            e.delay(1);
            p.getMovement().teleport(2713, 3494, 2);
            p.animate(2588);
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 20, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[1]);
        }));
        /**
         * Tightrope
         */
        ObjectAction.register(14932, "cross", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.privateSound(2495, 6, 0);
            p.stepAbs(2710, 3481, StepType.FORCE_WALK);
            e.delay(1);
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            e.delay(1);
            p.getStats().addXp(StatType.Agility, 20, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 14928)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[2]);
        }));
        /**
         * Gap
         */
        ObjectAction.register(14929, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.getMovement().force(0, -3, 0, 0, 25, 30, Direction.SOUTH);
            e.delay(1);
            p.animate(2585);
            e.delay(1);
            p.getMovement().teleport(p.getAbsX(), 3474, 3);
            p.getMovement().force(0, -2, 0, 0, 17, 27, Direction.SOUTH);
            e.delay(1);
            p.getMovement().teleport(p.getAbsX(), 3472, 3);
            p.getStats().addXp(StatType.Agility, 35, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 14932)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[3]);
        }));
        /**
         * Small gap
         */
        ObjectAction.register(14930, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2586, 15);
            p.privateSound(2462, 0, 15);
            e.delay(1);
            p.animate(2588);
            p.getMovement().teleport(2702, 3465, 2);
            e.delay(1);
            p.resetAnimation();
            p.getStats().addXp(StatType.Agility, 15, true);
            p.getMovement().restoreEnergy(Random.get(1, 2));
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 14929)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[4]);
        }));
        /**
         * Final jump
         */
        ObjectAction.register(14931, "jump", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(2462, 15);
            p.privateSound(2586, 0, 15);
            e.delay(1);
            p.getMovement().teleport(2704, 3464, 0);
            p.animate(2588);
            e.delay(1);
            p.resetAnimation();
            if (p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 14930) {
                p.getStats().addXp(StatType.Agility, 435, true);
                p.getMovement().restoreEnergy(Random.get(1, 2));
                int laps = PlayerCounter.SEERS_ROOFTOP.increment(p, 1);
                AgilityPet.rollForPet(p, 35205);
                MarkOfGrace.rollMark(p, 60, MARK_SPAWNS);
                p.getTaskManager().doLookupByCategory(TaskCategory.ROOFTOP, "seers");
                Agility.completeLap(p);
                if (!p.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                    p.sendFilteredMessage("Your Seer's Village Rooftop lap count is: " + Color.RED.wrap(laps + "") + ".");
            }
            p.removeAttribute("LAST_AGIL_OBJ");
            p.unlock();
        }));
    }
}
