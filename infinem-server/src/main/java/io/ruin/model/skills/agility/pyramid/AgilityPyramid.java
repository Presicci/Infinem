package io.ruin.model.skills.agility.pyramid;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.Renders;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Items;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.val;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/8/2023
 */
public class AgilityPyramid {

    private static final int[] STAIRS = { 10857, 10858 };
    private static final int[] LEDGES = { 10860, 10886, 10887, 10888, 10889 };
    private static final int[] PLANKS = { 10867, 10868 };
    private static final int[] GAPS = { 10861, 10862, 10882, 10883, 10884, 10885 };

    static {
        for (int stairId : STAIRS) {
            ObjectAction.register(stairId, 1, AgilityPyramid::climbStairs);
        }
        for (int ledgeId : LEDGES) {
            ObjectAction.register(ledgeId, 1, AgilityPyramid::crossLedge);
        }
        for (int plankId : PLANKS) {
            ObjectAction.register(plankId, 1, AgilityPyramid::crossPlank);
        }
        for (int gapId : GAPS) {
            ObjectAction.register(gapId, 1, AgilityPyramid::crossGap);
        }
        ObjectAction.register(10865, "climb-over", AgilityPyramid::climbLowWall);
        ObjectAction.register(10859, 1, AgilityPyramid::jumpGap);
        ObjectAction.register(10855, "enter", AgilityPyramid::doorway);
        ObjectAction.register(10856, "enter", AgilityPyramid::doorway);
        ObjectAction.register(10851, "climb", AgilityPyramid::climbingRocks);
    }

    protected static boolean isSuccessful(Player player, int neverFailLevel) {
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

    private static void jumpGap(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, 30, "jump this"))
            return;
        player.startEvent(e -> {
            player.lock();
            e.delay(1);
            boolean success = isSuccessful(player, 75);
            player.privateSound(success ? 2465 : 2463, 1, 20);
            player.sendFilteredMessage("You jump the gap...");
            player.animate(success ? 3067 : 3068, 15);
            val direction = object.getFaceDirection().getCounterClockwiseDirection(6);
            boolean reverse = !Direction.similarDirection(direction, Direction.getDirection(player.getPosition(), object.getPosition()), 1);
            val destination = player.getPosition().relative(direction, success ? reverse ? -3 : 3 : reverse ? -2 : 2);
            player.getMovement().force(destination, 0, 80, reverse ? direction.getCounterClockwiseDirection(4) : direction);
            if (!success) {
                e.delay(5);
                player.resetAnimation();
                player.sendFilteredMessage("... and miss your footing.");
                Hit hit = new Hit(HitType.DAMAGE);
                hit.fixedDamage(8);
                player.hit(hit);
                player.getMovement().teleport(getLowerTile(destination.relative(object.getFaceDirection(), 2)));
                player.unlock();
                return;
            }
            e.delay(3);
            player.getMovement().teleport(destination);
            player.getStats().addXp(StatType.Agility, 22, true);
            player.sendFilteredMessage("... and make it over.");
            player.unlock();
        });
    }

    private static void crossGap(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, 30, "cross this"))
            return;
        player.startEvent(e -> {
            player.lock();
            Position startPos = new Position(
                    object.getFaceDirection() == Direction.EAST ? object.getPosition().getX() : object.getFaceDirection() == Direction.WEST ? object.getPosition().getX() + 1: player.getPosition().getX(),
                    object.getFaceDirection() == Direction.NORTH ? object.getPosition().getY() : object.getFaceDirection() == Direction.SOUTH ? object.getPosition().getY() + 1 : player.getPosition().getY(),
                    player.getPosition().getZ());
            if (!player.getPosition().equals(startPos)) {
                player.stepAbs(startPos.getX(), startPos.getY(), StepType.FORCE_WALK);
                e.delay(1);
            }
            player.face(object);
            e.delay(1);
            boolean success = isSuccessful(player, 70);
            Direction walkDirection = object.getFaceDirection().getCounterClockwiseDirection(6);
            boolean reverse = !Direction.similarDirection(walkDirection, Direction.getDirection(player.getPosition(), object.getPosition()), 1);
            Position destination = player.getPosition().relative(walkDirection, reverse ? -5 : 5);
            player.sendFilteredMessage("You get a firm grip and start to edge across...");
            player.animate(reverse ? 3053 : 3057);
            e.delay(1);
            player.stepAbs(
                    object.getFaceDirection() == Direction.WEST ? object.getPosition().getX() + 1 : object.getPosition().getX(),
                    object.getFaceDirection() == Direction.SOUTH ? object.getPosition().getY() + 1 : object.getPosition().getY(),
                    StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(reverse ? Renders.AGILITY_HANG2 : Renders.AGILITY_HANG);
            e.delay(1);
            int stepsNeeded = object.getPosition().distance(destination);
            for (int step = 0; step < stepsNeeded; step++) {
                if (step == 1 && !success) {
                    Position fallDestination = getLowerTile(player.getPosition().relative(object.getFaceDirection(), 2));
                    player.getMovement().reset();
                    player.getAppearance().removeCustomRenders();
                    player.animate(reverse ? 3055 : 3056);
                    e.delay(1);
                    player.getMovement().force(fallDestination, 0, 30, walkDirection);
                    e.delay(1);
                    player.getMovement().teleport(fallDestination);
                    Hit hit = new Hit(HitType.DAMAGE);
                    hit.fixedDamage(8);
                    player.hit(hit);
                    player.sendFilteredMessage("Your hand slips and you fall to the level below.");
                    player.unlock();
                    return;
                }
                player.stepAbs(destination.getX(), destination.getY(), StepType.FORCE_WALK);
                player.privateSound(2450, 1, 0);
                e.delay(1);
            }
            e.delay(1);
            player.getAppearance().removeCustomRenders();
            player.animate(reverse ? 3054 : 3058);
            player.getStats().addXp(StatType.Agility, 56.4, true);
            player.sendFilteredMessage("You skillfully cross the gap.");
            player.unlock();
        });
    }

    private static void crossPlank(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, 30, "cross this"))
            return;
        player.startEvent(e -> {
            player.lock();
            player.stepAbs(object.getPosition().getX(), object.getPosition().getY(), StepType.FORCE_WALK);
            e.delay(1);
            boolean success = isSuccessful(player, 70);
            Direction walkDirection = object.getFaceDirection().getCounterClockwiseDirection(4);
            boolean reverse = object.id == 10867;
            Position destination = player.getPosition().relative(walkDirection, 5);
            player.getAppearance().setCustomRenders(Renders.AGILITY_BALANCE);
            player.stepAbs(destination.getX(), destination.getY(), StepType.FORCE_WALK);
            //sound loop
            player.privateSound(2470, 1, 0, success ? 4 : 2);
            e.delay(success ? 6 : 3);
            if (!success) {
                player.getMovement().reset();
                Direction fallDirection = object.getFaceDirection().getCounterClockwiseDirection(reverse ? 2 : 6);
                Position fallDestination = player.getPosition().relative(fallDirection, 2).relative(0, 0, -1);
                player.getAppearance().removeCustomRenders();
                player.animate(reverse ? 3069 : 764);
                e.delay(1);
                player.getMovement().force(fallDestination, 0, 30, walkDirection);
                e.delay(1);
                player.getMovement().teleport(fallDestination);
                Hit hit = new Hit(HitType.DAMAGE);
                hit.fixedDamage(10);
                player.hit(hit);
                player.sendFilteredMessage("You slip and fall to the level below.");
                player.unlock();
                return;
            }
            player.getStats().addXp(StatType.Agility, 56.4, true);
            player.getAppearance().removeCustomRenders();
            player.sendFilteredMessage("You walk carefully across the slippery plank...");
            player.unlock();
        });
    }

    private static void crossLedge(Player player, GameObject object) {
        if (!player.getStats().check(StatType.Agility, 30, "attempt this"))
            return;
        player.startEvent(e -> {
            Position startPos = new Position(
                    object.getFaceDirection() == Direction.EAST ? object.getPosition().getX() : object.getFaceDirection() == Direction.WEST ? object.getPosition().getX() + 1: player.getPosition().getX(),
                    object.getFaceDirection() == Direction.NORTH ? object.getPosition().getY() : object.getFaceDirection() == Direction.SOUTH ? object.getPosition().getY() + 1 : player.getPosition().getY(),
                    player.getPosition().getZ());
            if (!player.getPosition().equals(startPos)) {
                player.stepAbs(startPos.getX(), startPos.getY(), StepType.FORCE_WALK);
                e.delay(1);
            }
            player.face(object);
            e.delay(1);
            boolean success = isSuccessful(player, 70);
            Direction walkDirection = object.getFaceDirection().getCounterClockwiseDirection(6);
            boolean reverse = !Direction.similarDirection(walkDirection, Direction.getDirection(player.getPosition(), object.getPosition()), 1);
            Position destination = player.getPosition().relative(walkDirection, reverse ? -5 : 5);
            player.sendFilteredMessage("You put your foot on the ledge and try to edge across...");
            player.animate(reverse ? 752 : 753);
            player.stepAbs(
                    object.getFaceDirection() == Direction.WEST ? object.getPosition().getX() + 1 : object.getPosition().getX(),
                    object.getFaceDirection() == Direction.SOUTH ? object.getPosition().getY() + 1 : object.getPosition().getY(),
                    StepType.FORCE_WALK);
            player.getAppearance().setCustomRenders(reverse ? Renders.AGILITY_JUMP : Renders.AGILITY_WALL);
            e.delay(2);
            int stepsNeeded = object.getPosition().distance(destination);
            for (int step = 0; step < stepsNeeded; step++) {
                if (step == 1 && !success) {
                    player.getMovement().reset();
                    e.delay(2);
                    Position fallDestination = getLowerTile(player.getPosition().relative(object.getFaceDirection(), 2));
                    player.getMovement().reset();
                    player.getAppearance().removeCustomRenders();
                    player.animate(reverse ? 3061 : 3062);
                    e.delay(1);
                    player.getMovement().force(fallDestination, 0, 30, walkDirection);
                    e.delay(1);
                    player.getMovement().teleport(fallDestination);
                    Hit hit = new Hit(HitType.DAMAGE);
                    hit.fixedDamage(10);
                    player.hit(hit);
                    player.sendFilteredMessage("You slip and fall to the level below.");
                    player.unlock();
                    return;
                }
                player.stepAbs(destination.getX(), destination.getY(), StepType.FORCE_WALK);
                player.privateSound(2451, 2, 0);
                e.delay(1);
            }
            player.getAppearance().removeCustomRenders();
            player.animate(reverse ? 758 : 759);
            player.getStats().addXp(StatType.Agility, 52, true);
            player.sendFilteredMessage("You skillfully edge across the gap.");
            player.unlock();
        });
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

    private static void doorway(Player player, GameObject object) {
        if (Config.HIDE_PYRAMID.get(player) == 0) {
            player.dialogue(
                    new MessageDialogue("You did not grab the pyramid top, are you sure you want to continue through the passge?"),
                    new OptionsDialogue("Leave?",
                            new Option("Continue.", () -> {
                                player.getMovement().teleport(3364, 2830, 0);
                                player.dialogue(new MessageDialogue("You climb down the steep passage. It leads to the base of the<br>pyramid"));
                                Config.HIDE_PYRAMID.set(player, 0);
                            }),
                            new Option("No, I want my pyramid top!"))
            );
            return;
        }
        player.getMovement().teleport(3364, 2830, 0);
        player.dialogue(new MessageDialogue("You climb down the steep passage. It leads to the base of the<br>pyramid"));
        Config.HIDE_PYRAMID.set(player, 0);
    }

    private static void climbingRocks(Player player, GameObject object) {
        player.startEvent(e -> {
            player.lock();
            player.animate(3063);
            player.privateSound(2454);
            e.delay(2);
            if (Config.HIDE_PYRAMID.get(player) == 1) {
                player.sendMessage("You find nothing on top of the pyramid.");
            } else {
                if (player.getInventory().hasFreeSlots(1)) {
                    player.dialogue(new ItemDialogue().one(Items.PYRAMID_TOP, "You find a golden pyramid!"));
                    player.getInventory().add(Items.PYRAMID_TOP);
                    int laps = PlayerCounter.AGILITY_PYRAMID.increment(player, 1);
                    if (!player.hasAttribute(AttributeKey.HIDE_AGILITY_COUNT))
                        player.sendFilteredMessage("Your Agility Pyramid lap count is: " + Color.RED.wrap(laps + "") + ".");
                    Config.HIDE_PYRAMID.set(player, 1);
                } else {
                    player.sendMessage("You don't have enough inventory space to pick up the pyramid top.");
                }
            }
            player.unlock();
        });
    }

    private static void climbStairs(Player player, GameObject object) {
        if (object.id == 10857) {
            Position destination = AgilityPyramid.getHigherTile(player.getPosition().relative(Direction.NORTH, 3));
            player.getMovement().teleport(destination);
            Config.MOVING_BLOCK.set(player, lowerPyramid.inBounds(player) ? destination.getZ() : upperPyramid.inBounds(player) ? destination.getZ() + 2 : 0);
        } else {
            Position destination = AgilityPyramid.getLowerTile(player.getPosition().relative(Direction.SOUTH, 3));
            player.getMovement().teleport(destination);
            Config.MOVING_BLOCK.set(player, lowerPyramid.inBounds(player) ? destination.getZ() : upperPyramid.inBounds(player) ? destination.getZ() + 2 : 0);
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

    protected static final Bounds lowerPyramid = Bounds.fromRegion(13356);
    protected static final Bounds upperPyramid = Bounds.fromRegion(12105);
}
