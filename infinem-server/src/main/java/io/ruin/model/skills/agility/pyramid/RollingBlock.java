package io.ruin.model.skills.agility.pyramid;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.TileTrigger;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.val;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2023
 */
public class RollingBlock {

    @AllArgsConstructor
    private enum RollingBlockObject {
        FIRST_BLOCK(new Position(3354, 2841, 0)),
        SECOND_BLOCK(new Position(3374, 2835, 0)),
        THIRD_BLOCK(new Position(3368, 2849, 1)),
        FOURTH_BLOCK(new Position(3048, 4699, 1)),
        FIFTH_BLOCK(new Position(3044, 4699, 2));

        private final Position swTile;
    }

    private static final int diveAnim = 1115;
    private static final int slipAnim = 3064;

    static {
        for (RollingBlockObject block : RollingBlockObject.values()) {
            TileTrigger.registerPlayerTrigger(block.swTile.relative(0, 0, 1), 2, p -> {
                roll(p.player, block);
            });
        }
    }

    public static void roll(Player player, RollingBlockObject object) {
        if (player.isLocked()) {
            return;
        }
        player.lock();
        player.getMovement().reset();
        GameObject stoneObject = Tile.get(object.swTile).getObject(-1, 10, -1);
        player.startEvent(e -> {
            int xDiff = player.getPosition().getX() - object.swTile.getX();
            int yDiff = player.getPosition().getY() - object.swTile.getY();
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            e.delay(distance < 2 ? 0 : 1);
            Config.varpbit(stoneObject.getDef().varpBitId, false).set(player, 1);
            player.privateSound(1396);
            boolean success = AgilityPyramid.isSuccessful(player, 70);
            boolean forward = stoneObject.getFaceDirection() == Direction.NORTH ? player.getAbsY() == stoneObject.getPosition().getY()
                    : stoneObject.getFaceDirection() == Direction.EAST ? player.getAbsX() == stoneObject.getPosition().getX()
                    : stoneObject.getFaceDirection() == Direction.SOUTH ? player.getAbsY() == stoneObject.getPosition().getY() + 1
                    : player.getAbsX() == stoneObject.getPosition().getX() + 1;
            if (forward && success) {
                success(player, stoneObject);
            } else {
                failure(player, stoneObject, !forward);
            }
        });
    }

    private static void success(Player player, GameObject object) {
        player.startEvent(e -> {
            player.lock();
            player.privateSound(2455, 1, 25);
            player.animate(diveAnim);
            Position dest = player.getPosition().relative(object.getFaceDirection(), 2);
            player.getMovement().force(dest, 0, 30, object.getFaceDirection());
            e.delay(1);
            Config.varpbit(object.getDef().varpBitId, false).set(player, 0);
            player.getMovement().teleport(dest);
            player.getStats().addXp(StatType.Agility, 12, true);
            player.unlock();
        });
    }

    private static void failure(Player player, GameObject blockObject, boolean reverse) {
        player.startEvent(e -> {
            player.lock();
            player.animate(slipAnim);
            val dir = blockObject.direction == 1 ? Direction.WEST : blockObject.direction == 0 ? Direction.SOUTH :
                    blockObject.direction == 2 ? Direction.NORTH : Direction.EAST;
            val destination = player.getPosition().relative(dir, 2).relative(0, 0, -1);
            player.getMovement().force(destination, 0, 60, dir);
            e.delay(1);
            Config.varpbit(blockObject.getDef().varpBitId, false).set(player, 0);
            player.getMovement().teleport(AgilityPyramid.getLowerTile(destination.relative(0, 0, 1)));
            Hit hit = new Hit(HitType.DAMAGE);
            hit.fixedDamage(reverse ? 1 : 6);
            player.hit(hit);
            player.unlock();
        });
    }
}
