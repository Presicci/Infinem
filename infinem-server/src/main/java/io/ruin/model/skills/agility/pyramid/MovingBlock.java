package io.ruin.model.skills.agility.pyramid;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;
import lombok.AllArgsConstructor;
import lombok.val;

import java.util.EnumMap;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
@AllArgsConstructor
public enum MovingBlock {
    FIRST_LEVEL_BLOCK(5788, 10872, new Position(3372, 2847, 1), Direction.EAST),
    THIRD_LEVEL_BLOCK(5788, 10873, new Position(3366, 2845, 3), Direction.NORTH);

    private final int npcId, objectId;
    private final Position spawn;
    private final Direction direction;

    private static final EnumMap<MovingBlock, MovingBlockNPC> map = new EnumMap<>(MovingBlock.class);

    static {
        for (MovingBlock block : values()) {
            MovingBlockNPC npc = new MovingBlockNPC(block.npcId, block.spawn, block.direction, 2);
            map.put(block, npc);
        }
    }

    protected static void moveBlocks() {
        for (val entry : map.entrySet()) {
            MovingBlock key = entry.getKey();
            MovingBlockNPC value = entry.getValue();
            value.slide(key.direction);
            value.publicSound(1395);
            World.startEvent(e -> {
                e.delay(6);
                value.slide(Direction.getOppositeDirection(key.direction));
            });
        }
    }
}
