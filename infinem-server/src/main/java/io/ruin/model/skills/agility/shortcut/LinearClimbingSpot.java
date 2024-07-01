package io.ruin.model.skills.agility.shortcut;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

/**
 * @author ReverendDread on 3/16/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@AllArgsConstructor
public enum LinearClimbingSpot {
    ICE_MOUNTAIN_68(47571, new Position(2998, 3483),
            47570, new Position(3001, 3483),
            68, 1, Direction.EAST),
    KARUULM29(34397, new Position(1324, 3778),
            34397, new Position(1324, 3784),
            29, 1, Direction.NORTH),
    KARUULM62(34396, new Position(1324, 3788),
            34396, new Position(1324, 3794),
            62, 1, Direction.NORTH),;

    private final int bottomId;
    private final Position bottomPos;
    private final int topId;
    private final Position topPos;
    private final int levelRequirement, experience;
    private final Direction faceDirection;

    public void climb(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, levelRequirement, "attempt this"))
            return;
        int distance = bottomPos.distance(topPos) + 2;
        Direction direction = player.getAbsX() > object.x ? Direction.WEST
                : player.getAbsX() < object.x ? Direction.EAST
                : player.getAbsY() < object.y ? Direction.NORTH
                : Direction.SOUTH;
        player.startEvent(e -> {
            e.waitForMovement(player);
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(4435);
            player.getMovement().force(
                    direction == Direction.EAST ? distance
                            : direction == Direction.WEST ? -distance
                            : 0,
                    direction == Direction.NORTH ? distance
                            : direction == Direction.SOUTH ? -distance
                            : 0,
                    0, 0, 0, distance * 25, faceDirection);
            e.delay(distance - 1);
            player.animate(-1);
            player.getStats().addXp(StatType.Agility, experience, true);
            player.unlock();
        });
    }

    static {
        for (LinearClimbingSpot spot : values()) {
            ObjectAction.register(spot.bottomId, spot.bottomPos, "climb", (spot::climb));
            ObjectAction.register(spot.topId, spot.topPos, "climb", (spot::climb));
        }
    }
}
