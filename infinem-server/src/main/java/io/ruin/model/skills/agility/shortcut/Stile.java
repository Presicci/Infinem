package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public enum Stile {
    LUMBERYARD(2618, 1),
    LUMBRIDGE(12892, 1),
    ARDOUGNE(993, 1);

    private final int id, levelRequirement;
    private final Position objectPos;

    Stile(int id, int levelRequirement) {
        this(id, levelRequirement, null);
    }

    Stile(int id, int levelRequirement, Position objectPos) {
        this.id = id;
        this.levelRequirement = levelRequirement;
        this.objectPos = objectPos;
    }

    static {
        for (Stile stile : values()) {
            if (stile.objectPos == null) {
                ObjectAction.register(stile.id, "climb-over", (player, obj) -> shortcut(player, obj, stile.levelRequirement));
            } else {
                ObjectAction.register(stile.id, stile.objectPos, "climb-over", (player, obj) -> shortcut(player, obj, stile.levelRequirement));
            }
        }
    }

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
