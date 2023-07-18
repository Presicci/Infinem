package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LooseRailing {
    MCGRUBOR_WOODS(51, 1, new Position(2662, 3500)),
    OGRE_PEN(19171, 1, new Position(2522, 3375));

    private final int id, levelRequirement;
    private final Position objectPos;

    static {
        for (LooseRailing railing : values()) {
            ObjectAction.register(railing.id, railing.objectPos, "squeeze-through", (player, obj) -> shortcut(player, obj, railing.levelRequirement));
        }
    }

    public static void shortcut(Player player, GameObject obj, int levelReq) {
        if (!player.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        int objDir = obj.direction;
        boolean onObjectSide = (objDir == 0 && player.getAbsX() == obj.getPosition().getX())
                || (objDir == 2 && player.getAbsX() <= obj.getPosition().getX())
                || (objDir == 3 && player.getAbsY() >= obj.getPosition().getY())
                || (objDir == 1 && player.getAbsY() <= obj.getPosition().getY());
        int diffX = objDir == 0 ? player.getAbsX() >= obj.getPosition().getX() ? -1 : 1
                : objDir == 2 ? player.getAbsX() <= obj.getPosition().getX() ? 1 : -1 : 0;
        int diffY = objDir == 3 ? player.getAbsY() >= obj.getPosition().getY() ? -1 : 1
                : objDir == 1 ? player.getAbsY() <= obj.getPosition().getY() ? 1 : -1 : 0;
        player.lock(LockType.FULL_DELAY_DAMAGE);
        player.startEvent(e -> {
            if (onObjectSide && !player.getPosition().equals(obj.getPosition())) {
                player.stepAbs(obj.getPosition().getX(), obj.getPosition().getY(), StepType.FORCE_WALK);
                e.delay(1);
                player.face(obj);
                e.delay(1);
            }
            player.animate(1237);
            Direction direction = Direction.getDirection(player.getPosition(), obj.getPosition());
            if (direction == null)
                direction = Direction.fromDoorDirection(objDir);
            player.getMovement().force(player.getPosition().relative(diffX, diffY), 0, 60, direction);
            e.delay(2);
            player.unlock();
        });

    }

}
