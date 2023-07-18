package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class Stile {

    public static void shortcut(Player p, GameObject stile, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(839);
            boolean vertical = stile.direction == 0 || stile.direction == 2;
            int diffX = vertical ? 0 : p.getAbsX() > stile.x ? -2 : 2;
            int diffY = vertical ? p.getAbsY() > stile.y ? -2 : 2 : 0;
            p.getMovement().force(diffX,  diffY, 0, 0, 0, 60, Direction.getDirection(p.getPosition(), stile.getPosition()));
            e.delay(2);
            p.getStats().addXp(StatType.Agility, levelReq > 1 ? 1 : 0.5, true);
            p.unlock();
        });
    }
    public static void shortcutN(Player p, GameObject stile, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(839);
            if(p.getAbsY() > stile.y)
                p.getMovement().force(0, -2, 0, 0, 0, 60, Direction.SOUTH);
            else
                p.getMovement().force(0, 2, 0, 0, 0, 60, Direction.NORTH);
            e.delay(2);
            p.getStats().addXp(StatType.Agility, 0.5, true);
            p.unlock();
        });
    }

}
