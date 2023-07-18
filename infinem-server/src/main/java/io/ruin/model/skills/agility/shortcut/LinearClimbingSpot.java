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

    KARUULM29(34397, new Position(1324, 3778),
            34397, new Position(1324, 3784),
            29, 1),
    KARUULM62(34396, new Position(1324, 3788),
            34396, new Position(1324, 3794),
            62, 1),;

    private final int bottomId;
    private final Position bottomPos;
    private final int topId;
    private final Position topPos;
    private final int levelRequirement, experience;

    public void climb(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, levelRequirement, "attempt this"))
            return;
        int distance = bottomPos.distance(topPos) + 2;
        player.startEvent(e -> {
            e.waitForMovement(player);
            player.lock(LockType.FULL_DELAY_DAMAGE);
            player.animate(4435);
            if (object.getPosition().equals(bottomPos))
                player.getMovement().force(0, distance, 0, 0, 0, 90, Direction.NORTH);
            else
                player.getMovement().force(0, -distance, 0, 0, 0, 90, Direction.NORTH);
            e.delay(distance);
            player.animate(-1);
            player.getStats().addXp(StatType.Agility, experience, true);
            player.unlock();
        });
    }

    static {
        for (LinearClimbingSpot spot : values()) {
            ObjectAction.register(spot.bottomId, spot.bottomPos, "climb", (spot::climb));
        }
    }
}
