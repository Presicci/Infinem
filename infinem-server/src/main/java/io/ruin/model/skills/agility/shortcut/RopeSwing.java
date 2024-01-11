package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
@AllArgsConstructor
public enum RopeSwing {

    OGRE_ISLAND(1, 1, Position.of(2511, 3092), Position.of(2511, 3096)),

    MOSS_GIANT_ISLAND_TO(10, 1, Position.of(2709, 3209), Position.of(2704, 3209)),

    MOSS_GIANT_ISLAND_BACK(10, 1, Position.of(2704, 3205), Position.of(2709, 3205)),

    ;

    private int levelReq;
    private int xp;
    private Position startPosition, endPosition;

    public void traverse(Player player, GameObject obj){
        if (!player.getStats().check(StatType.Agility, levelReq, "attempt this"))
            return;
        if (!player.getPosition().isWithinDistance(startPosition, 2)) {
            return;
        }
        player.startEvent(event -> {
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.face(endPosition.getX(), endPosition.getY());
            obj.animate(54);
            player.animate(751);

            int xDiff = endPosition.getX() - player.getPosition().getX();
            int yDiff = endPosition.getY() - player.getPosition().getY();
            player.getMovement().force(xDiff, yDiff, 0, 0, 30, 60, Direction.getDirection(startPosition, endPosition));
            event.delay(1);
            obj.animate(55);
            player.getStats().addXp(StatType.Agility, xp, true);
            player.unlock();
        });
    }

}
