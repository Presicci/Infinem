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
import io.ruin.model.map.object.actions.impl.Ladder;
import io.ruin.model.skills.agility.TricksterAgility;
import io.ruin.model.stat.StatType;

public class BarbarianCourse {

    private static final GameObject[] OBSTACLES = {
            Tile.get(new Position(2550, 3546, 0), true).getObject(23144, 22, 3),
            Tile.get(new Position(2538, 3545, 0), true).getObject(20211, 10, 3),
            Tile.get(new Position(2535, 3547, 1), true).getObject(23547, 22, 1),
            Tile.get(new Position(2532, 3545, 1), true).getObject(42487, 10, 2),
            Tile.get(new Position(2536, 3553, 0), true).getObject(1948, 10, 2),
            Tile.get(new Position(2539, 3553, 0), true).getObject(1948, 10, 2),
            Tile.get(new Position(2542, 3553, 0), true).getObject(1948, 10, 2)
    };

    static {
        /**
         * Ropeswing
         */
        ObjectAction.register(23131, "swing-on", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 35, "attempt this"))
                return;
            player.lock(LockType.FULL_DELAY_DAMAGE);
            obj.animate(54);
            player.animate(751);
            player.getMovement().force(0, -5, 0, 0, 30, 50, Direction.SOUTH);
            event.delay(1);
            obj.animate(55);
            player.getStats().addXp(StatType.Agility, 22.0, true);
            player.putAttribute("LAST_AGIL_OBJ", obj.id);
            player.unlock();
            event.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[0]);
        }));
        Tile.getObject(23131, 2551, 3550, 0).walkTo = new Position(2551, 3554, 0);
        /**
         * Log balance
         */
        ObjectAction.register(23144, "walk-across", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.sendMessage("You walk carefully across the slippery log...");
            player.stepAbs(2541, 3546, StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            event.delay(10);
            player.getAppearance().removeCustomRenders();
            player.getStats().addXp(StatType.Agility, 13.7, true);
            player.sendMessage("...you make it safely to the other side.");
            if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23131)
                player.putAttribute("LAST_AGIL_OBJ", obj.id);
            player.unlock();
            event.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[1]);
        }));
        Tile.getObject(23144, 2550, 3546, 0).walkTo = new Position(2551, 3546, 0);
        /**
         * Obstacle net
         */
        ObjectAction.register(20211, "climb-over", (player, obj) -> player.startEvent(event -> {
            if (player.getAbsX() < obj.x) {
                return;
            }
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(828);
            event.delay(1);
            player.getMovement().teleport(player.getAbsX() - 2, player.getAbsY(), 1);
            player.getStats().addXp(StatType.Agility, 9.2, true);
            if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23144)
                player.putAttribute("LAST_AGIL_OBJ", obj.id);
            player.unlock();
            event.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[2]);
        }));
        /**
         * Balancing ledge
         */
        ObjectAction.register(23547, "walk-across", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(753);
            player.stepAbs(2532, 3547, StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(Renders.AGILITY_WALL);
            event.delay(4);
            player.stepAbs(2532, 3546, StepType.FORCE_WALK);
            player.getAppearance().removeCustomRenders();
            event.delay(1);
            player.getStats().addXp(StatType.Agility, 22.0, true);
            if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 20211)
                player.putAttribute("LAST_AGIL_OBJ", obj.id);
            player.unlock();
            event.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[3]);
        }));
        /**
         * Ladder
         */
        ObjectAction.register(42487, "climb-down", (player, obj) -> {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(827);
                e.delay(1);
                player.getMovement().teleport(2532, 3546, 0);
                player.unlock();
                e.delay(1);
                TricksterAgility.attemptNext(player, OBSTACLES[4]);
            });
        });
        ObjectAction.register(16683, "climb-up", (player, obj) -> player.sendMessage("Why would you want to go backwards?"));
        /**
         * Crumbling wall one!
         */
        ObjectAction.register(1948, 2536, 3553, 0, "climb-over", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(1);
            player.getStats().addXp(StatType.Agility, 13.7, true);
            if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 23547)
                player.putAttribute("LAST_AGIL_OBJ", obj.id);
            player.unlock();
            event.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[5]);
        }));
        Tile.getObject(1948, 2536, 3553, 0).walkTo = new Position(2535, 3553, 0);
        /**
         * Crumbling wall two!
         */
        ObjectAction.register(1948, 2539, 3553, 0, "climb-over", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(1);
            player.getStats().addXp(StatType.Agility, 13.7, true);
            player.unlock();
            event.delay(1);
            TricksterAgility.attemptNext(player, OBSTACLES[6]);
        }));
        Tile.getObject(1948, 2539, 3553, 0).walkTo = new Position(2538, 3553, 0);
        /**
         * Last crumbling wall! :- )
         */
        ObjectAction.register(1948, 2542, 3553, 0, "climb-over", (player, obj) -> player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(839);
            player.getMovement().force(2, 0, 0, 0, 0, 60, Direction.EAST);
            event.delay(1);
            if (player.getAttributeIntOrZero("LAST_AGIL_OBJ") == 1948) {
                player.getStats().addXp(StatType.Agility, 46.2, true);
                int laps = PlayerCounter.BARBARIAN_COURSE.increment(player, 1);
                AgilityPet.rollForPet(player, 44376);
                player.getTaskManager().doLookupByUUID(571, 1);  // Complete the Barbarian Outpost Agility Course
                if (!player.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                    player.sendFilteredMessage("Your Barbarian Agility lap count is: " + Color.RED.wrap(laps + "") + ".");
            } else {
                player.getStats().addXp(StatType.Agility, 13.7, true);

            }
            player.removeAttribute("LAST_AGIL_OBJ");
            player.unlock();
        }));
        Tile.getObject(1948, 2542, 3553, 0).walkTo = new Position(2541, 3553, 0);
    }

}
