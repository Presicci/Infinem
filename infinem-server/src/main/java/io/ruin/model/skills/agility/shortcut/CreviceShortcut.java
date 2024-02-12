package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum CreviceShortcut {

    FALADOR(42, 1, Position.of(3028, 9806), Position.of(3035, 9806), true),
    HEROES_GUILD(67, 1, new Position(2899, 9902), new Position(2914, 9894), true),
    ZANARIS_46(46, 1, new Position(2400, 4404, 0), new Position(2400, 4402, 0), false),
    ZANARIS_66(46, 1, new Position(2409, 4402, 0), new Position(2409, 4400, 0), false),
    ;

    private final int levelReq, xp;
    private final Position startPosition, endPosition;
    private final boolean useMidPoint;

    public void squeeze(Player player, GameObject obj){
        if (!player.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        player.startEvent( e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.face(obj);
            player.animate(2594);
            Position target = player.getPosition().equals(startPosition) ? endPosition : startPosition;
            e.delay(1);
            if (useMidPoint) {
                player.getMovement().teleport(
                        (startPosition.getX() + endPosition.getX()) / 2,
                        (startPosition.getY() + endPosition.getY()) / 2,
                        startPosition.getZ());
                e.delay(2);
            }
            player.getMovement().teleport(target);
            player.animate(2595);
            e.delay(1);
            player.getStats().addXp(StatType.Agility, xp, true);
            player.unlock();
        });
    }
}