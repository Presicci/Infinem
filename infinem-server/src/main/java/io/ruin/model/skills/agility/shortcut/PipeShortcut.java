package io.ruin.model.skills.agility.shortcut;

import com.google.common.collect.Lists;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import javafx.geometry.Pos;

import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public enum PipeShortcut {

    YANILLE_PIPE(49, 1, Position.of(2578, 9506), Position.of(2572, 9506), Position.of(2575, 9506)),
    WITCHAVEN_DUNGEON_PIPE(30, 1, Position.of(2330, 5096), Position.of(2333, 5096), Position.of(2331, 5096));

    private final int level;
    private final double exp;
    private final Position startPosition;
    private final Position endPosition;
    private final Position[] positions;

    PipeShortcut(int level, double exp, Position startPosition, Position endPosition, Position... positions) {
        this.level = level;
        this.exp = exp;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.positions = positions;
    }

    public void traverse(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, level, "squeeze-through"))
            return;
        player.startEvent((event) -> {

            player.lock(LockType.FULL_DELAY_DAMAGE);

            if (object.walkTo != null)
                event.path(player, object.walkTo);

            List<Position> posCopy = Lists.newArrayList(positions);

            Position first = posCopy.get(0);
            if(posCopy.size() == 1){
                if(startPosition.equals(player.getPosition())){
                    posCopy.add(endPosition);
                } else {
                    posCopy.add(startPosition);
                }
            } else if(object.getPosition().equals(first)){
                posCopy.add(endPosition);
            } else {
                Collections.reverse(posCopy);
                posCopy.add(startPosition);
            }

            for (Position pos : posCopy) {
                int xDiff = pos.getX() - player.getPosition().getX();
                int yDiff = pos.getY() - player.getPosition().getY();
                player.getMovement().force(xDiff, yDiff, 0, 0, 5, 95, Direction.getDirection(player.getPosition(), pos));
                player.animate(749);
                event.delay(4);
            }

            player.getStats().addXp(StatType.Agility, exp, true);
            player.unlock();

        });
    }
}