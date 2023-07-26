package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public class UnderwallTunnel {

    public static void shortcutNWToSE(Player p, GameObject wall, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            if(wall.id == 16530) {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(2589);
                p.getMovement().force(-1, 0, 0, 0, 0, 50, Direction.WEST);
                e.delay(2);
                p.animate(2590);
                p.getMovement().force(-3, 3, 0, 0, 0, 100, Direction.WEST);
                e.delay(3);
                p.animate(2591);
                p.getMovement().force(-1, 0, 0, 0, 15, 33, Direction.WEST);
                e.delay(1);
                p.getStats().addXp(StatType.Agility, 1, true);
                p.unlock();
            } else {
                p.lock(LockType.FULL_DELAY_DAMAGE);
                p.animate(2589);
                p.getMovement().force(1, 0, 0, 0, 0, 50, Direction.EAST);
                e.delay(2);
                p.animate(2590);
                p.getMovement().force(3, -3, 0, 0, 0, 100, Direction.EAST);
                e.delay(3);
                p.animate(2591);
                p.getMovement().force(1, 0, 0, 0, 15, 33, Direction.EAST);
                e.delay(1);
                p.getStats().addXp(StatType.Agility, 1, true);
                p.unlock();
            }
        });
    }

    public static void shortcut(Player p, int levelReq, Direction moveDirection) {
        shortcut(p, levelReq, moveDirection, 4);
    }

    public static void shortcut(Player player, int levelReq, Direction moveDirection, int moveTiles) {
        if (!player.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        int verticalMult = moveDirection == Direction.NORTH ? 1 : moveDirection == Direction.SOUTH ? -1 : 0;
        int horizontalMult = moveDirection == Direction.EAST ? 1 : moveDirection == Direction.WEST ? -1 : 0;
        player.startEvent(e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            e.delay(1);
            player.animate(2589);
            player.getMovement().force(horizontalMult * moveTiles, verticalMult * moveTiles, 0, 0, 0, 180, moveDirection);
            e.delay(2);
            player.animate(2590);
            e.delay(2);
            player.animate(2591);
            e.delay(1);
            if (levelReq > 1)
                player.getStats().addXp(StatType.Agility, 1, true);
            player.unlock();
        });
    }
}
