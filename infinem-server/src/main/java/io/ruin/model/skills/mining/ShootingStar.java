package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Geode;
import io.ruin.model.item.actions.impl.skillcapes.MiningSkillCape;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/30/2021
 */
@Getter
@AllArgsConstructor
public enum ShootingStar {
    SIZE_9(41020, 90, 244, 15, 87840, 90, 3),
    SIZE_8(41021, 80, 162, 40, 118035, 72, 5),
    SIZE_7(41223, 70, 123, 80, 148230, 56, 9),
    SIZE_6(41224, 60, 74, 120, 244305, 42, 12),
    SIZE_5(41225, 50, 48, 175, 373320, 30, 17),
    SIZE_4(41226, 40, 31, 250, 521550, 20, 20),
    SIZE_3(41227, 30, 26, 430, 554390, 12, 18),
    SIZE_2(41228, 20, 22, 700, 609390, 6, 16),
    SIZE_1(41229, 10, 12, 1200, 911340, 2, 0);

    private final int starId, levelRequirement;
    private final double experience;
    private final int stardust, petOdds, doubleDustChance, crashWeight;

    private static final int STARDUST = 25527;
    private static final int SPAWN_TICKS = 6000;
    private static StarLocation starLocation = getLocation();
    private static ShootingStar activeStar = null;
    private static GameObject starObject = null;
    private static boolean found = false;
    private static int stardustLeft;

    private static void spawnStar() {
        found = false;
        if (starObject != null) {
            despawnStar();
        }
        int roll = Random.get(100);
        ShootingStar star = null;
        for (ShootingStar s : values()) {
            if ((roll -= s.getCrashWeight()) <= 0) {
                star = s;
                break;
            }
        }
        if (star == null) {
            star = ShootingStar.SIZE_2;
        }
        Position spawnPos = starLocation.positions[starLocation.positions.length - 1];
        starObject = new GameObject(star.starId, spawnPos, 10, 0);
        stardustLeft = star.stardust;
        activeStar = star;
        starLocation = getLocation();
    }

    private static void nextTier() {
        if (activeStar == ShootingStar.SIZE_1) {
            despawnStar();
            return;
        }
        activeStar = ShootingStar.values()[activeStar.ordinal() + 1];
        stardustLeft = activeStar.stardust;
        starObject.setId(activeStar.starId);
    }

    private static void despawnStar() {
        starObject.remove();
    }

    private static StarLocation getLocation() {
        return StarLocation.values()[Random.get(StarLocation.values().length - 1)];
    }

    private static void mine(final ShootingStar star, Player player, GameObject starObject) {
        if (starObject.isRemoved()) {
            player.resetAnimation();
            return;
        }
        if (!found) {
            found = true;
            player.dialogue(new MessageDialogue("Congratulations! You were the first person to find this star!"));
            ++player.shootingStarsFound;
            //TODO Noticeboard
        }
        Pickaxe pickaxe = Pickaxe.find(player);
        if (pickaxe == null) {
            player.dialogue(new MessageDialogue("You need a pickaxe to mine the star. You do not have a pickaxe which " +
                    "you have the Mining level to use."));
            player.privateSound(2277);
            return;
        }
        Stat stat = player.getStats().get(StatType.Mining);
        if (stat.currentLevel < star.getLevelRequirement()) {
            player.sendMessage("You need a Mining level of " + star.getLevelRequirement() + " to mine this level of star.");
            player.privateSound(2277);
            return;
        }

        if (!player.getInventory().hasRoomFor(STARDUST)) {
            player.privateSound(2277);
            player.sendMessage("Your inventory is too full to hold any more stardust.");
            return;
        }
        player.startEvent(event -> {
            int attempts = 0;
            ShootingStar currentStar = star;
            while (true) {
                if (starObject.id == currentStar.starId) {
                    currentStar = activeStar;
                }

                if(!player.getInventory().hasRoomFor(STARDUST)) {
                    player.resetAnimation();
                    player.privateSound(2277);
                    player.sendMessage("Your inventory is too full to hold any more stardust.");
                    return;
                }
                final int miningAnimation = pickaxe.regularAnimationID;
                if(attempts == 0) {
                    player.sendFilteredMessage("You swing your pick at the rock.");
                    player.animate(miningAnimation);
                    attempts++;
                } else if (attempts % 2 == 0 && Random.get(100) <= chance(Mining.getEffectiveLevel(player), currentStar, pickaxe)) {
                    int stardustQuantity = Random.rollPercent(currentStar.doubleDustChance) ? 2 : 1;
                    player.collectResource(new Item(STARDUST, stardustQuantity));
                    player.getInventory().add(STARDUST, stardustQuantity);
                    if (player.dragonPickaxeSpecial > 0 && Random.rollPercent(50)) {
                        player.getInventory().add(STARDUST, 1);
                        player.sendFilteredMessage("Your pickaxe's buff allows you to mine an additional stardust!");
                    } else if (MiningSkillCape.wearsMiningCape(player) && Random.rollPercent(5)) {
                        player.getInventory().add(STARDUST, 1);
                        player.sendFilteredMessage("You manage to mine an additional stardust.");
                    }
                    if (Random.rollDie(currentStar.petOdds - (player.getStats().get(StatType.Mining).currentLevel * 25)))
                        Pet.ROCK_GOLEM.unlock(player);
                    /* Rolling for a Geode clue scroll */
                    if (Random.rollDie(250, 1)) {
                        player.getInventory().addOrDrop(Geode.getRandomGeode(), 1);
                        PlayerCounter.MINED_GEODE.increment(player, 1);
                    }
                    PlayerCounter.MINED_STARDUST.increment(player, 1);
                    player.getStats().addXp(StatType.Mining, currentStar.experience, true);
                    if (--stardustLeft <= 0) {

                    }
                    player.sendFilteredMessage("You manage to mine some stardust.");
                }

                if(attempts++ % 4 == 0)
                    player.animate(miningAnimation);

                event.delay(1);
            }
        });
    }

    private static void prospect(Player player, ShootingStar star) {
        player.dialogue(new MessageDialogue("This is a size-" + (star.getLevelRequirement() / 10) + " star. A Mining level of at least "
                + star.getLevelRequirement() + " is required to mine this layer. It has been mined "
                + (double)(stardustLeft/star.stardust) * 100 + "% of the way to the next layer"));
    }

    private static double chance(int level, ShootingStar star, Pickaxe pickaxe) {
        double points = ((level - star.getLevelRequirement()) + 1 + (double) pickaxe.points);
        double denominator = (double) 150;
        return (Math.min(0.80, points / denominator) * 100);
    }

    @Getter
    public enum StarLocation {
        ASGARNIA(new Position(3018, 3443, 1));

        private final Position[] positions;

        StarLocation(Position... postitions) {
            this.positions = postitions;
        }
    }

    static {
        for (ShootingStar star : values()) {
            ObjectAction.register(star.starId, "mine", (player, object) -> mine(star, player, object));
            ObjectAction.register(star.starId, "prospect", (player, object) -> prospect(player, star));
        }

        //  Spawn event loop
        World.startEvent(event -> {
            while (true) {
                event.delay(SPAWN_TICKS);
                spawnStar();
            }
        });
    }
}
