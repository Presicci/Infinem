package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;

public class Hole {

    public static void shortcut(Player p, GameObject wall, int levelReq) {
        if (!p.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        p.startEvent(e -> {
            p.lock(LockType.FULL_DELAY_DAMAGE);
            p.animate(839); // TODO Find proper animation
            if(p.getAbsY() > wall.y)
                p.getMovement().force(0, -5, 0, 0, 0, 120, Direction.SOUTH);
            else
                p.getMovement().force(0, 5, 0, 0, 0, 120, Direction.NORTH);
            e.delay(2);
            p.getStats().addXp(StatType.Agility, 0.5, true);
            p.unlock();
        });
    }
}
