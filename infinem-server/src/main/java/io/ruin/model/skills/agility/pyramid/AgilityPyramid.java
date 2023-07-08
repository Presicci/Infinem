package io.ruin.model.skills.agility.pyramid;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.val;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/8/2023
 */
public class AgilityPyramid {

    private static final int[] STAIRS = { 10857, 10858 };

    static {
        for (int stairId : STAIRS) {
            ObjectAction.register(stairId, 1, AgilityPyramid::climbStairs);
        }
        ObjectAction.register(10865, "climb-over", AgilityPyramid::climbLowWall);
    }

    private static boolean isSuccessful(Player player, int neverFailLevel) {
        val level = player.getStats().get(StatType.Agility).currentLevel;
        val baseRequirement = 30;
        val baseChance = 75;//Base chance % to not fail minimum level.
        val adjustmentPercentage = 100 - baseChance;
        val successPerLevel = (float) adjustmentPercentage / ((float) neverFailLevel - baseRequirement);
        val successChance = baseChance + Math.max(0, (level - baseRequirement)) * successPerLevel;
        if (player.debug)
            player.sendMessage("Chance:" + successChance);
        return Random.get(100) < successChance;
    }

    private static void climbLowWall(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, 30, "climb-over"))
            return;
        player.startEvent(e -> {
            player.lock();
            player.privateSound(2453, 1, 30);
            player.sendFilteredMessage("You climb the low wall...");
            if (!isSuccessful(player, 70)) {
                e.delay(2);
                player.sendFilteredMessage("... and your foot slips.");
                Hit hit = new Hit(HitType.DAMAGE);
                hit.fixedDamage(4);
                player.hit(hit);
                player.unlock();
                return;
            }
            player.animate(840, 15);
            boolean forward = object.getFaceDirection() == Direction.NORTH ? player.getAbsY() < object.getPosition().getY()
                    : object.getFaceDirection() == Direction.EAST ? player.getAbsX() < object.getPosition().getX()
                    : object.getFaceDirection() == Direction.SOUTH ? player.getAbsY() > object.getPosition().getY()
                    : player.getAbsX() > object.getPosition().getX();
            val direction = forward ? object.getFaceDirection() : Direction.getOppositeDirection(object.getFaceDirection());
            val destination = player.getPosition().relative(direction, 2);
            player.getMovement().force(destination, 0, 80, direction);

            e.delay(2);
            player.getStats().addXp(StatType.Agility, 8, true);
            player.sendFilteredMessage("... and make it over.");
            player.unlock();

        });
    }

    private static void climbStairs(Player player, GameObject object) {
        if (object.id == 10857) {
            player.getMovement().teleport(AgilityPyramid.getHigherTile(player.getPosition().relative(Direction.NORTH, 3)));
        } else {
            player.getMovement().teleport(AgilityPyramid.getLowerTile(player.getPosition().relative(Direction.SOUTH, 3)));
        }
    }

    public static Position getHigherTile(Position position) {
        if(position.getZ() == 3) {
            return position.relative(-320, 1856, -1);
        }
        return position.relative(0, 0, 1);
    }

    public static Position getLowerTile(Position position) {
        if (position.getY() >= 4686 && position.getY() <= 4709 && position.getZ() == 2) {
            return position.relative(320, -1856, 1);
        }
        return position.relative(0, 0, -1);
    }
}
