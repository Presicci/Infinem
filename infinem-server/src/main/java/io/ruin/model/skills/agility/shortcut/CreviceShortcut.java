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

    HEROES_GUILD(67, 1, new Position(2899, 9902), new Position(2914, 9894)),
    FALADOR(1, 1, Position.of(3028, 9806), Position.of(3035, 9806));

    private int levelReq, xp;
    private Position startPosition, endPosition;

    public void squeeze(Player player, GameObject obj){
        player.startEvent( e -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.face(obj);
            player.animate(2594);
            Position target = player.getPosition().equals(startPosition) ? endPosition : startPosition;
            e.delay(1);
            player.getMovement().teleport(
                    (startPosition.getX() + endPosition.getX()) / 2,
                    (startPosition.getY() + endPosition.getY()) / 2,
                    startPosition.getZ());
            e.delay(2);
            player.getMovement().teleport(target);
            player.animate(2595);
            e.delay(1);
            if(World.isEco())
                player.getStats().addXp(StatType.Agility, xp, true);
            player.unlock();
        })
        ;}
}