package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.AttributeKey;
import io.ruin.cache.Color;
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
import io.ruin.model.stat.StatType;

public class GnomeStrongholdCourse {

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(2473, 3425, 0), true).getObject(23134, 10, 0),
            Tile.get(new Position(2473, 3422, 1), true).getObject(23559, 10, 0),
            Tile.get(new Position(2478, 3420, 2), true).getObject(23557, 22, 1),
            Tile.get(new Position(2486, 3419, 2), true).getObject(23560, 10, 0),
            Tile.get(new Position(2485, 3426, 0), true).getObject(23135, 10, 2),
            Tile.get(new Position(2484, 3431, 0), true).getObject(23138, 10, 1)
    };

    static {
        /**
         * Log balance
         */
        ObjectAction.register(23145, "walk-across", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.sendFilteredMessage("You walk carefully across the slippery log...");
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.stepAbs(2474, 3429, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            p.sendFilteredMessage("...You make it safely to the other side.");
            p.getStats().addXp(StatType.Agility, 7.5, true);
            p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[0]);
        }));
        Tile.get(2474, 3430, 0).flagUnmovable();
        /**
         * Obstacle net
         */
        ObjectAction.register(23134, "climb-over", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.sendFilteredMessage("You climb the netting...");
            p.animate(828);
            e.delay(1);
            p.getMovement().teleport(p.getAbsX(), 3424, 1);
            p.getStats().addXp(StatType.Agility, 7.5, true);
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23145)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[1]);
        }));
        /**
         * Tree branch
         */
        ObjectAction.register(23559, "climb", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.sendFilteredMessage("You climb the tree...");
            p.animate(828);
            e.delay(1);
            p.sendFilteredMessage("...To the platform above.");
            p.getMovement().teleport(2473, 3420, 2);
            p.getStats().addXp(StatType.Agility, 5.0, true);
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23134)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[2]);
        }));
        /**
         * Balancing rope
         */
        ObjectAction.register(23557, "walk-on", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.sendFilteredMessage("You carefully cross the tightrope.");
            p.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            p.stepAbs(2483, 3420, StepType.FORCE_WALK);
            e.waitForMovement(p);
            p.getAppearance().removeCustomRenders();
            p.getStats().addXp(StatType.Agility, 7.5, true);
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23559)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[3]);
        }));
        /**
         * Tree branch
         */
        ObjectAction.register(23560, "climb-down", (p, obj) -> p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.sendFilteredMessage("You climb down the tree...");
            p.animate(828);
            e.delay(1);
            p.sendFilteredMessage("You land on the ground.");
            p.getMovement().teleport(2485, 3419, 0);
            p.getStats().addXp(StatType.Agility, 5.0, true);
            if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23557)
                p.putAttribute("LAST_AGIL_OBJ", obj.id);
            p.unlock();
            e.delay(1);
            TricksterAgility.attemptNext(p, OBSTACLES[4]);
        }));
        /**
         * Obstacle net
         */
        ObjectAction.register(23135, "climb-over", (p, obj) -> {
            if(p.getAbsY() != 3425) {
                p.sendMessage("You can not do that from here.");
                return;
            }
            p.startEvent(e -> {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.sendMessage("You climb the netting...");
                p.animate(828);
                e.delay(2);
                p.getMovement().teleport(p.getAbsX(), 3428, 0);
                p.getStats().addXp(StatType.Agility, 4.0, true);
                if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23560)
                    p.putAttribute("LAST_AGIL_OBJ", obj.id);
                p.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(p, OBSTACLES[5]);
            });
        });
        /**
         * Obstacle pipe
         */
        ObjectAction pipeAction = (p, obj) -> {
            if(obj.y >= 3435) {
                p.sendMessage("You can't enter the pipe from this side.");
                return;
            }
            p.startEvent(e -> {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(749, 30);
                p.getMovement().force(0, 3, 0, 0, 33, 126, Direction.NORTH);
                e.delay(3);
                p.getMovement().teleport(obj.x, obj.y + 2, 0);
                e.delay(3);
                p.stepAbs(p.getAbsX(), p.getAbsY() + 1, StepType.FORCE_WALK);
                e.delay(1);
                p.animate(749, 30);
                p.getMovement().force(0, 3, 0, 0, 33, 126, Direction.NORTH);
                e.delay(3);
                p.getMovement().teleport(obj.x, obj.y + 6, 0);
                if(p.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23135) {
                    p.getStats().addXp(StatType.Agility, 46.5, true);
                    int laps = PlayerCounter.GNOME_STRONGHOLD_COURSE.increment(p, 1);
                    AgilityPet.rollForPet(p, 35609);
                    p.getTaskManager().doLookupByUUID(559, 1);  // Complete the Gnome Stronghold Agility Course
                    if (!p.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                        p.sendFilteredMessage("Your Gnome Agility lap count is: " + Color.RED.wrap(laps + "") + ".");
                } else {
                    p.getStats().addXp(StatType.Agility, 7.5, true);
                }
                p.removeAttribute("LAST_AGIL_OBJ");
                p.unlock();
            });
        };
        ObjectAction.register(23138, "squeeze-through", pipeAction);
        Tile.getObject(23138, 2484, 3431, 0).walkTo = new Position(2484, 3430, 0);

        ObjectAction.register(23139, "squeeze-through", pipeAction);
        Tile.getObject(23139, 2487, 3431, 0).walkTo = new Position(2487, 3430, 0);
    }

}
