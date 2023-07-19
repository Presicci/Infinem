package io.ruin.model.skills.agility.shortcut;

import com.google.common.collect.Lists;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.RegisterObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/20/2021
 */
public enum PipeShortcut {

    YANILLE_PIPE(new RegisterObject[]{ 
            new RegisterObject(23140, 2573, 9506), 
            new RegisterObject(23140, 2576, 9506) },
            49, 1, "squeeze-through",
            new Position(2578, 9506), new Position(2572, 9506), new Position(2575, 9506)),
    WITCHAVEN_DUNGEON_PIPE(new RegisterObject[]{
            new RegisterObject(18416, 2331, 5096) },
            30, 1, "climb-throu",
            new Position(2330, 5096), new Position(2333, 5096));

    private final RegisterObject[] objects;
    private final int level;
    private final double exp;
    private final String option;
    private final Position startPosition;
    private final Position endPosition;
    private final Position[] positions;

    PipeShortcut(RegisterObject[] objects, int level, double exp, String option, Position startPosition, Position endPosition, Position... positions) {
        this.objects = objects;
        this.level = level;
        this.exp = exp;
        this.option = option;
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

            Position first = posCopy.size() > 0 ? posCopy.get(0) : null;
            if(posCopy.size() == 1 || first == null){
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

    static {
        for (PipeShortcut pipe : values()) {
            for (RegisterObject object : pipe.objects) {
                ObjectAction.register(object.getObjectId(), object.getPosition(), pipe.option, pipe::traverse);
            }
        }

        // Yanille Pipe
        Tile.getObject(23140, 2576, 9506, 0).walkTo = new Position(2578, 9506, 0);
        Tile.getObject(23140, 2573, 9506, 0).walkTo =  new Position(2572, 9506, 0);
    }
}