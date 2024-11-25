package io.ruin.model.activities.wilderness;

import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class Misc {

    private static void crossWildernessDitch(Player player, GameObject obj) {
        player.startEvent(event -> {
            Direction dir;
            int diffX = 0, diffY = 0;
            if(obj.direction == 0 || obj.direction == 2) {
                if(player.getAbsY() == 3520) {
                    dir = Direction.NORTH;
                    diffY = 3;
                } else {
                    dir = Direction.SOUTH;
                    diffY -= 3;
                }
            } else {
                if(player.getAbsX() == 2995) {
                    dir = Direction.EAST;
                    diffX = 3;
                } else {
                    dir = Direction.WEST;
                    diffX = -3;
                }
            }
            player.lock();
            player.animate(6132);
            player.privateSound(2462, 1, 25);
            player.getMovement().force(diffX, diffY, 0, 0, 33, 60, dir);
            event.delay(2);
            player.getTaskManager().doLookupByUUID(839); // Cross the Wilderness Ditch
            player.unlock();
        });
    }

    static {
        /**
         * Lava dragon shortcut
         */
        ObjectAction.register(14918, "cross", (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 74, "use this shortcut"))
                return;
            p.startEvent(e -> {
                p.lock();
                if(p.getAbsY() >= 3810) {
                    p.animate(741);
                    p.getMovement().force(0, -2, 0, 0, 5, 35, Direction.SOUTH);
                    e.delay(1);
                    p.animate(741);
                    p.getMovement().force(0, -1, 0, 0, 5, 35, Direction.SOUTH);
                    e.delay(1);
                } else {
                    p.animate(741);
                    p.getMovement().force(0, 1, 0, 0, 5, 35, Direction.NORTH);
                    e.delay(1);
                    p.animate(741);
                    p.getMovement().force(0, 2, 0, 0, 5, 35, Direction.NORTH);
                    e.delay(1);
                }
                p.unlock();
            });
        });
        /**
         * Lava maze shortcut
         */
        ObjectAction.register(14917, "cross", (p, obj) -> {
            if(!p.getStats().check(StatType.Agility, 82, "use this shortcut"))
                return;
            p.startEvent(e -> {
                p.lock();
                if(p.getAbsY() > 3879) {
                    p.animate(741);
                    p.getMovement().force(1, -2, 0, 0, 5, 35, Direction.SOUTH_EAST);
                    e.delay(1);
                    p.animate(741);
                    p.getMovement().force(1, -1, 0, 0, 5, 35, Direction.SOUTH_EAST);
                    e.delay(1);
                } else {
                    p.animate(741);
                    p.getMovement().force(-1, 1, 0, 0, 5, 35, Direction.NORTH_WEST);
                    e.delay(1);
                    p.animate(741);
                    p.getMovement().force(-1, 2, 0, 0, 5, 35, Direction.NORTH_WEST);
                    e.delay(1);
                }
                p.unlock();
            });
        });
        /**
         * Ditch
         */
        ObjectAction.register(23271, "cross", Misc::crossWildernessDitch);
        ObjectAction.register(50652, "cross", Misc::crossWildernessDitch);
        /**
         * Deep dungeon
         */
        ObjectAction.register(16664, 3044, 3924, 0, "climb-down", (player, obj) -> player.getMovement().teleport(3044, 10322));
        ObjectAction.register(16665, 3044, 10324, 0, "climb-up", (player, obj) -> player.getMovement().teleport(3044, 3927));
        /**
         * Deep dungeon shortcut
         */
        ObjectAction.register(19043, 3046, 10327, 0, "use", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 46, "use this shortcut"))
                return;
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3048, 10336);
            player.unlock();
        }));
        ObjectAction.register(19043, 3048, 10335, 0, "use", (player, obj) -> player.startEvent(event -> {
            if (!player.getStats().check(StatType.Agility, 46, "use this shortcut"))
                return;
            player.lock();
            player.animate(2796);
            event.delay(2);
            player.resetAnimation();
            player.getMovement().teleport(3046, 10326);
            player.unlock();
        }));

        /**
         * Dark castle tile door tile
         */
        Tile.get(3035, 3628, 0).unflagUnmovable();
    }

}
