package io.ruin.model.skills.mining;

import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.model.World;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Geode;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.DecimalFormat;

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
    private static int spawnMinutes = 60;    // 1 hour
    private static StarLocation starLocation = getLocation();
    private static double splitMultiplier = 1;
    private static ShootingStar activeStar = null;
    public static GameObject starObject = null;
    private static boolean found = false;
    private static int stardustLeft;

    public static void spawnStar() {
        found = false;
        if (starObject != null && !starObject.isRemoved()) {
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
        starObject.spawn();
        stardustLeft = star.stardust;
        activeStar = star;
        starLocation = getLocation();
        spawnMinutes = 60;
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
        splitMultiplier = Random.get();
        return StarLocation.values()[Random.get(StarLocation.values().length - 1)];
    }

    private static void mine(final ShootingStar star, Player player, GameObject starObject) {
        if (!found) {
            found = true;
            player.dialogue(new MessageDialogue("Congratulations! You were the first person to find this star!"));
            player.incrementNumericAttribute(AttributeKey.SHOOTING_STARS_FOUND, 1);
            return;
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
                if (starObject.isRemoved()) {
                    player.resetAnimation();
                    return;
                }
                if (starObject.id != currentStar.starId) {
                    currentStar = activeStar;
                }
                if (!player.getInventory().hasRoomFor(STARDUST)) {
                    player.resetAnimation();
                    player.privateSound(2277);
                    player.sendMessage("Your inventory is too full to hold any more stardust.");
                    return;
                }
                final int miningAnimation = pickaxe.regularAnimationID;
                if (attempts == 0) {
                    player.sendFilteredMessage("You swing your pick at the rock.");
                    player.animate(miningAnimation);
                    attempts++;
                } else if (attempts % 2 == 0 && Random.get(100) <= chance(Mining.getEffectiveLevel(player), currentStar, pickaxe)) {
                    int stardustQuantity = Random.rollPercent(currentStar.doubleDustChance) ? 2 : 1;
                    player.collectResource(new Item(STARDUST, stardustQuantity));
                    player.getInventory().add(STARDUST, stardustQuantity);
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
                        nextTier();
                    }
                    player.sendFilteredMessage("You manage to mine some stardust.");
                }

                if (attempts++ % 4 == 0)
                    player.animate(miningAnimation);

                event.delay(1);
            }
        });
    }

    private static void prospect(Player player, ShootingStar star) {
        player.dialogue(new MessageDialogue("This is a size-" + (star.getLevelRequirement() / 10) + " star. A Mining level of at least "
                + star.getLevelRequirement() + " is required to mine this layer. It has been mined "
                + new DecimalFormat("#.##").format((double) (star.stardust - stardustLeft) / star.stardust * 100) + "% of the way to the next layer"));
    }

    private static double chance(int level, ShootingStar star, Pickaxe pickaxe) {
        double points = ((level - star.getLevelRequirement()) + 1 + (double) pickaxe.points);
        double denominator = (double) 350;
        return (Math.min(0.80, points / denominator) * 100);
    }

    public static String getTelescopeString(int timeWindow) {
        String location = StringUtils.fixCaps(starLocation.toString().toLowerCase().replace("_", " "));
        int split = (int) (timeWindow * splitMultiplier); // starLocation
        return "You see a shooting star! The star looks like it will land in " +
                ((starLocation == StarLocation.FELDIP_HILLS_AND_THE_ISLE_OF_SOULS || starLocation == StarLocation.FREMENNIK_LANDS_AND_LUNAR_ISLE || starLocation == StarLocation.KEBOS_LOWLANDS
                        || starLocation == StarLocation.KHARIDIAN_DESERT || starLocation == StarLocation.WILDERNESS) ? "the " : "")
                + location + " in the next " + (spawnMinutes - (timeWindow - split)) + " to " + (spawnMinutes + split) + " minutes.";
    }

    @Getter
    public enum StarLocation {
        ASGARNIA(
                new Position(3018, 3443, 0),    // Dwarven mine
                new Position(3030, 3349, 0),    // Mining guild
                new Position(2906, 3355, 0),    // West Fally mine
                new Position(2881, 3475, 0),    // Taverly
                new Position(2940, 3280, 0),    // Crafting guild
                new Position(2973, 3241, 0)     // Rimmington mine
        ),
        CRANDOR_AND_KARAMJA(
                new Position(2822, 3238, 0),    // South crandor mine
                new Position(2835, 3296, 0),    // North crandor mine
                new Position(2734, 3221, 0),    // North brimhaven mine
                new Position(2741, 3142, 0),    // South brimhaven mine
                new Position(2844, 3038, 0),    // Karamja jungle mine
                new Position(2827, 2998, 0)     // Shilo village mine
        ),
        FELDIP_HILLS_AND_THE_ISLE_OF_SOULS(
                new Position(2571, 2964, 0),    // Feldip hills
                new Position(2630, 2994, 0),    // Rantz's cave
                new Position(2567, 2859, 0),    // Corsair cove
                new Position(2482, 2886, 0),    // Corsair cove resource area
                new Position(2468, 2843, 0),    // Myth's guild
                new Position(2199, 2793, 0)     // Isle of souls mine
        ),
        FOSSIL_ISLAND_AND_MOS_LEHARMLESS(
                new Position(3774, 3814, 0),    // Fossil island mine
                new Position(3817, 3803, 0),    // Volcanic mine entrance
                new Position(3686, 2970, 0)     // Mos Le'Harmless
        ),
        FREMENNIK_LANDS_AND_LUNAR_ISLE(
                new Position(2683, 3699, 0),    // Rellekka mine
                new Position(2726, 3683, 0),    // Keldagrim entrance mine
                new Position(2528, 3887, 0),    // Miscellania mine
                new Position(2392, 3814, 0),    // Jatizso mine entrance
                new Position(2374, 3832, 0),    // Central Fremennik isles mine
                new Position(2137, 3940, 0)     // Lunar isle mine entrance
        ),
        GREAT_KOUREND(
                new Position(1778, 3493, 0),// Hosidius mine
                new Position(1593, 3647, 0),// Shayzien mine
                new Position(1768, 3711, 0),// Port piscarilius mine
                new Position(1760, 3856, 0),// Dense essence mine
                new Position(1438, 3840, 0),// Lavakite mine
                new Position(1532, 3748, 0)// Lovakengj
        ),
        KANDARIN(
                new Position(2805, 3435, 0),    // Catherby bank
                new Position(2603, 3087, 0),    // Yanille bank
                new Position(2624, 3142, 0),    // Port khazard mine
                new Position(2704, 3333, 0),    // Legend's guild mine
                new Position(2589, 3478, 0),    // Coal trucks
                new Position(2608, 3233, 0)     // South-east ardy mine
        ),
        KEBOS_LOWLANDS(
                new Position(1209, 3650, 0),    // Kebow lowlands mine
                new Position(1279, 3818, 0),    // Mount karuulm mine
                new Position(1322, 3816, 0),    // Mount karuulm bank
                new Position(1258, 3564, 0)     // Mount quidamortem
        ),
        KHARIDIAN_DESERT(
                new Position(3297, 3299, 0),    // Al kharid mine
                new Position(3276, 3164, 0),    // Al kharid bank
                new Position(3424, 3160, 0),    // Uzer mine
                new Position(3170, 2911, 0),    // Desert quarry
                new Position(3318, 2869, 0),    // Agility pyramid mine
                new Position(3435, 2890, 0)     // Nardah
        ),
        MISTHALIN(
                new Position(3230, 3154, 0),    // East lumbridge swamp mine
                new Position(3152, 3152, 0),    // West lumbridge swamp mine
                new Position(3094, 3237, 0),    // Draynor village
                new Position(3257, 3409, 0),    // Varrock
                new Position(3287, 3355, 0),    // South-east varrock mine
                new Position(3176, 3361, 0)     // South-west varrock mine
        ),
        MORYTANIA(
                new Position(3505, 3485, 0),    // Canifis
                new Position(3499, 3219, 0),    // Burgh de Rott
                new Position(3449, 3232, 0),    // Abandoned mine
                new Position(3650, 3213, 0),    // Ver sinhaza
                new Position(3635, 3340, 0)     // Daeyalt essence mine
        ),
        PISCATORIS_AND_THE_GNOME_STRONGHOLD(
                new Position(2341, 3636, 0),    // Piscatoris mine
                new Position(2444, 3490, 0),    // Grand tree
                new Position(2448, 3436, 0)     // Tree gnome stronghold
        ),
        TIRANNWN(
                new Position(2269, 3159, 0),    // Isafdar mine
                new Position(2319, 3268, 0),    // Arandar mine
                new Position(2329, 3163, 0),    // Lletya
                new Position(3273, 6054, 0),    // Trahaearn mine
                new Position(2175, 3410, 0)     // Mynydd mine
        ),
        WILDERNESS(
                new Position(3108, 3570, 0),    // South wilderness mine
                new Position(3018, 3593, 0),    // South-west wilderness mine
                new Position(3091, 3755, 0),    // Bandit camp mine
                new Position(3057, 3887, 0),    // Lava maze runite mine
                new Position(3188, 3932, 0),    // Resource area
                new Position(3092, 3962, 0),    // Mage arena
                new Position(3049, 3941, 0)     // Pirate's hideout mine
        );

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
                if (--spawnMinutes <= 0) {
                    spawnStar();
                }
                event.delay(100);
            }
        });
    }
}
