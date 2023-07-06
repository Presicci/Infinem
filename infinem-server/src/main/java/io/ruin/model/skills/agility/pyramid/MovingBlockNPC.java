package io.ruin.model.skills.agility.pyramid;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import javafx.geometry.Pos;
import lombok.val;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class MovingBlockNPC extends NPC {

    private static final int longFallAnim = 3066;
    private static final int shortFallAnim = 3065;

    private final Direction spawnDirection;

    public MovingBlockNPC(int id, Position position, Direction facing, int size) {
        super(id);
        spawn(position);
        this.face(facing);
        spawnDirection = facing;
        this.setSize(size);
        this.clip = true;
    }

    @Override
    public void onMovement() {
        for (Player player : localPlayers()) {
            if (!player.isLocked()
                    && player.getPosition().getZ() == getPosition().getZ()
                    && Position.collides(player.getAbsX(), player.getAbsY(), player.getSize(), getAbsX(), getAbsY(), getSize())) {
                boolean horizontal = spawnDirection == Direction.EAST || spawnDirection == Direction.WEST;
                Position destTile = new Position(horizontal ? (spawnPosition.getX() + 4)
                        : player.getAbsX(), horizontal ? player.getAbsY()
                        : (spawnPosition.getY() + 4), player.getPosition().getZ() - 1);
                push(player, destTile);
            }
        }
    }

    private void push(Player player, Position destination) {
        player.startEvent(e -> {
            player.sendMessage(destination.getX() + "," + destination.getY() + "," + destination.getZ());
            player.lock();
            player.resetActions(false, true, true);
            int ticks = player.getPosition().getDistance(destination);
            player.sendMessage("" + ticks);
            player.animate(ticks == 1 ? shortFallAnim : longFallAnim);
            Direction backwardsDirection = Direction.getOppositeDirection(spawnDirection);
            player.face(backwardsDirection);
            player.getMovement().force(destination, 0, ticks * 30, backwardsDirection);
            e.delay(ticks);
            player.getMovement().teleport(destination);
            Hit hit = new Hit(HitType.DAMAGE);
            hit.fixedDamage(8);
            player.hit(hit);
            player.unlock();
        });
    }

    public void slide(Direction direction) {
        Position destination = getPosition().relative(direction, 2);
        stepAbs(destination.getX(), destination.getY(), StepType.FORCE_WALK);
    }
}
