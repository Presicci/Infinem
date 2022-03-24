package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/24/2022
 */
public class CoordinateClue extends Clue {

    private final String clue;

    public CoordinateClue(ClueType type, String clue) {
        super(type);
        this.clue = clue;
    }

    @Override
    public void open(Player player) {
        player.getPacketSender().sendString(203, 2, clue);
        player.openInterface(InterfaceType.MAIN, 203);
    }

    private static void registerDig(ClueType type, Position position) {
        /**
         * int x2 = 2440;
         * 		int y2 = 3161;
         *
         * 		x2 += degX * 32 + Math.round(minX / 1.875);
         * 		y2 += degY * 32 + Math.round(minY / 1.875);
         *
         * 		return new WorldPoint(x2, y2, 0);
         */
        int x = position.getX();
        int y = position.getY();
        int obsX = 2440;
        int obsY = 3161;
        boolean north = y > obsY;
        boolean east = x > obsX;
        int degX = east ? (x - obsX) / 32 : (obsX - x) / 32;
        int degY = north ? (y - obsY) / 32 : (obsY - y) / 32;
        int minX = (int) (east ? Math.floor(((x - obsX) % 32) * 1.875) : Math.floor(((obsX - x) % 32) * 1.875));
        int minY = (int) (north ? Math.floor(((y - obsY) % 32) * 1.875) : Math.floor(((obsY - y) % 32) * 1.875));
        String clue = twoDigitInt(degY) + " degrees " + twoDigitInt(minY) + " minutes " + (north ? "north" : "south") + ",<br>"
                + twoDigitInt(degX) + " degrees " + twoDigitInt(minX) + " minutes " + (east ? "east" : "west");
        CoordinateClue cryptic = new CoordinateClue(type, clue);
        Tile.get(x, y, position.getZ(), true).digAction = cryptic::advance;
    }

    private static String twoDigitInt(int number) {
        return number > 9 ? String.valueOf(number) : "0" + number;
    }

    static {
        // Medium
        registerDig(ClueType.MEDIUM, new Position(2479, 3158, 0)); // South of fruit tree patch, west of Tree Gnome Village.
        registerDig(ClueType.MEDIUM, new Position(2887, 3154, 0)); // West of Banana plantation on Karamja.
        registerDig(ClueType.MEDIUM, new Position(2743, 3151, 0)); // Entrance of Brimhaven dungeon.
        registerDig(ClueType.MEDIUM, new Position(3184, 3150, 0)); // South of Lumbridge Swamp.
        registerDig(ClueType.MEDIUM, new Position(3217, 3177, 0)); // East of Lumbridge Swamp.
        registerDig(ClueType.MEDIUM, new Position(3007, 3144, 0)); // Near the entrance to the Asgarnian Ice Dungeon, south of Port Sarim (AIQ).
        registerDig(ClueType.MEDIUM, new Position(2896, 3119, 0)); // Near Karambwan fishing spot (DKP).
        registerDig(ClueType.MEDIUM, new Position(2697, 3207, 0)); // Centre of Moss Giant Island, west of Brimhaven.
        registerDig(ClueType.MEDIUM, new Position(2679, 3110, 0)); // North of Hazelmere's house (CLS).
        registerDig(ClueType.MEDIUM, new Position(3510, 3074, 0)); // East of Uzer (DLQ).
        registerDig(ClueType.MEDIUM, new Position(3160, 3251, 0)); // West of trapdoor leading to H.A.M Hideout.
        registerDig(ClueType.MEDIUM, new Position(2643, 3252, 0)); // South of Ardougne Zoo, North of Tower of Life (DJP).
        registerDig(ClueType.MEDIUM, new Position(2322, 3061, 0)); // South-west of Castle wars (BKP).
        registerDig(ClueType.MEDIUM, new Position(2875, 3046, 0)); // North of nature altar, north of Shilo Village (CKR).
        registerDig(ClueType.MEDIUM, new Position(2849, 3033, 0)); // West of nature altar, north of Shilo Village (CKR).
        registerDig(ClueType.MEDIUM, new Position(2848, 3296, 0)); // North of Crandor island.
        registerDig(ClueType.MEDIUM, new Position(2583, 2990, 0)); // Feldip Hills, south-east of Gu'Thanoth (AKS).
        registerDig(ClueType.MEDIUM, new Position(3179, 3344, 0)); // In the cow pen north of the Lumbridge windmill.
        registerDig(ClueType.MEDIUM, new Position(2383, 3370, 0)); // West of the outpost
        registerDig(ClueType.MEDIUM, new Position(3312, 3375, 0)); // North-west of Exam Centre, on the hill.
        registerDig(ClueType.MEDIUM, new Position(3121, 3384, 0)); // North-east of Draynor Manor, near River Lum.
        registerDig(ClueType.MEDIUM, new Position(3430, 3388, 0)); // West of Mort Myre Swamp.
        registerDig(ClueType.MEDIUM, new Position(2920, 3403, 0)); // South-east of Taverley, near Lady of the Lake.
        registerDig(ClueType.MEDIUM, new Position(2594, 2899, 0)); // South-east of Feldip Hills, by the crimson swifts (AKS).
        registerDig(ClueType.MEDIUM, new Position(2387, 3435, 0)); // West of Tree Gnome Stronghold, near the pen containing terrorbirds.
        registerDig(ClueType.MEDIUM, new Position(2512, 3467, 0)); // Baxtorian Falls (Bring rope).
        registerDig(ClueType.MEDIUM, new Position(2381, 3468, 0)); // West of Tree Gnome Stronghold, north of the pen with terrorbirds.
        registerDig(ClueType.MEDIUM, new Position(3005, 3475, 0)); // Ice Mountain, west of Edgeville.
        registerDig(ClueType.MEDIUM, new Position(2585, 3505, 0)); // By the shore line north of the Coal Trucks.
        registerDig(ClueType.MEDIUM, new Position(3443, 3515, 0)); // South of Slayer Tower.
        registerDig(ClueType.MEDIUM, new Position(2416, 3516, 0)); // Tree Gnome Stronghold, west of Grand Tree, near swamp.
        registerDig(ClueType.MEDIUM, new Position(3429, 3523, 0)); // South of Slayer Tower.
        registerDig(ClueType.MEDIUM, new Position(2363, 3531, 0)); // North-east of Eagles' Peak.
        registerDig(ClueType.MEDIUM, new Position(2919, 3535, 0)); // East of Burthorpe pub.
        registerDig(ClueType.MEDIUM, new Position(3548, 3560, 0)); // Inside Fenkenstrain's Castle.
        registerDig(ClueType.MEDIUM, new Position(1456, 3620, 0)); // Graveyard west of Shayzien (DJR).
        registerDig(ClueType.MEDIUM, new Position(2735, 3638, 0)); // East of Rellekka, north-west of Golden Apple Tree (AJR).
        registerDig(ClueType.MEDIUM, new Position(2681, 3653, 0)); // Rellekka, in the garden of the south-east house.
        registerDig(ClueType.MEDIUM, new Position(2537, 3881, 0)); // Miscellania.
        registerDig(ClueType.MEDIUM, new Position(2828, 3234, 0)); // Southern coast of Crandor.
        registerDig(ClueType.MEDIUM, new Position(1247, 3726, 0)); // Just inside the Farming Guild
        registerDig(ClueType.MEDIUM, new Position(3770, 3898, 0)); // On the small island north-east of Fossil Island's mushroom forest.
        // Hard
        registerDig(ClueType.HARD, new Position(2209, 3161, 0)); // North-east of Tyras Camp.
        registerDig(ClueType.HARD, new Position(2181, 3206, 0)); // South of Elf Camp.
        registerDig(ClueType.HARD, new Position(3081, 3209, 0)); // Small Island (CLP).
        registerDig(ClueType.HARD, new Position(3374, 3250, 0)); // Duel Arena combat area.
        registerDig(ClueType.HARD, new Position(2699, 3251, 0)); // Little island (AIR).
        registerDig(ClueType.HARD, new Position(3546, 3251, 0)); // North-east of Burgh de Rott.
        registerDig(ClueType.HARD, new Position(3544, 3256, 0)); // North-east of Burgh de Rott.
        registerDig(ClueType.HARD, new Position(2841, 3267, 0)); // Crandor island.
        registerDig(ClueType.HARD, new Position(3168, 3041, 0)); // Bedabin Camp.
        registerDig(ClueType.HARD, new Position(2542, 3031, 0)); // Gu'Tanoth, may require 20gp.
        registerDig(ClueType.HARD, new Position(2581, 3030, 0)); // Gu'Tanoth island, enter cave north-west of Feldip Hills (AKS).
        registerDig(ClueType.HARD, new Position(2961, 3024, 0)); // Ship yard (DKP).
        registerDig(ClueType.HARD, new Position(2339, 3311, 0)); // East of Prifddinas on Arandar mountain pass.
        registerDig(ClueType.HARD, new Position(3440, 3341, 0)); // Nature Spirit's grotto.
        registerDig(ClueType.HARD, new Position(2763, 2974, 0)); // Cairn Isle, west of Shilo Village.
        registerDig(ClueType.HARD, new Position(3138, 2969, 0)); // West of Bandit Camp in Kharidian Desert.
        registerDig(ClueType.HARD, new Position(2924, 2963, 0)); // On the southern part of eastern Karamja.
        registerDig(ClueType.HARD, new Position(2838, 2914, 0)); // Kharazi Jungle, near water pool.
        registerDig(ClueType.HARD, new Position(3441, 3419, 0)); // Mort Myre Swamp.
        registerDig(ClueType.HARD, new Position(2950, 2902, 0)); // South-east of Kharazi Jungle.
        registerDig(ClueType.HARD, new Position(2775, 2891, 0)); // South-west of Kharazi Jungle.
        registerDig(ClueType.HARD, new Position(3113, 3602, 0)); // Wilderness. North of Edgeville (level 11).
        registerDig(ClueType.HARD, new Position(2892, 3675, 0)); // On the summit of Trollheim.
        registerDig(ClueType.HARD, new Position(3168, 3677, 0)); // Wilderness. Graveyard of Shadows.
        registerDig(ClueType.HARD, new Position(2853, 3690, 0)); // Entrance to the troll Stronghold.
        registerDig(ClueType.HARD, new Position(3305, 3692, 0)); // Wilderness. West of eastern green dragon.
        registerDig(ClueType.HARD, new Position(3055, 3696, 0)); // Wilderness. Bandit Camp.
        registerDig(ClueType.HARD, new Position(3302, 3696, 0)); // Wilderness. West of eastern green dragon.
        registerDig(ClueType.HARD, new Position(1479, 3696, 0)); // Lizardman Canyon.
        registerDig(ClueType.HARD, new Position(2712, 3732, 0)); // North-east of Rellekka.
        registerDig(ClueType.HARD, new Position(2970, 3749, 0)); // Wilderness. Forgotten Cemetery.
        registerDig(ClueType.HARD, new Position(3094, 3764, 0)); // Wilderness. Mining site north of Bandit Camp.
        registerDig(ClueType.HARD, new Position(3311, 3769, 0)); // Wilderness. North of Venenatis.
        registerDig(ClueType.HARD, new Position(1460, 3782, 0)); // Lovakengj, near burning man.
        registerDig(ClueType.HARD, new Position(3244, 3792, 0)); // Wilderness. South-east of Lava Dragon Isle by some Chaos Dwarves.
        registerDig(ClueType.HARD, new Position(3140, 3804, 0)); // Wilderness. North of Ruins.
        registerDig(ClueType.HARD, new Position(2946, 3819, 0)); // Wilderness. Chaos Temple (level 38).
        registerDig(ClueType.HARD, new Position(3771, 3825, 0)); // Fossil Island. East of Museum Camp.
        registerDig(ClueType.HARD, new Position(3013, 3846, 0)); // Wilderness. West of Lava Maze, before KBD's lair.
        registerDig(ClueType.HARD, new Position(3058, 3884, 0)); // Wilderness. Near runite ore north of Lava Maze.
        registerDig(ClueType.HARD, new Position(3290, 3889, 0)); // Wilderness. Demonic Ruins.
        registerDig(ClueType.HARD, new Position(3770, 3897, 0)); // Small Island north of Fossil Island.
        registerDig(ClueType.HARD, new Position(2505, 3899, 0)); // Small Island north-east of Miscellania (AJS).
        registerDig(ClueType.HARD, new Position(3285, 3942, 0)); // Wilderness. Rogues' Castle.
        registerDig(ClueType.HARD, new Position(3159, 3959, 0)); // Wilderness. North of Deserted Keep, west of Resource Area.
        registerDig(ClueType.HARD, new Position(3039, 3960, 0)); // Wilderness. Pirates' Hideout.
        registerDig(ClueType.HARD, new Position(2987, 3963, 0)); // Wilderness. West of Wilderness Agility Course.
        registerDig(ClueType.HARD, new Position(3189, 3963, 0)); // Wilderness. North of Resource Area, near magic axe hut.
        registerDig(ClueType.HARD, new Position(2341, 3697, 0)); // North-east of the Piscatoris Fishing Colony bank.
        registerDig(ClueType.HARD, new Position(3143, 3774, 0)); // In level 32 Wilderness, by the black chinchompa hunting area.
        registerDig(ClueType.HARD, new Position(2992, 3941, 0)); // Wilderness Agility Course, past the log balance.
        // Elite
        registerDig(ClueType.ELITE, new Position(2357, 3151, 0)); // Lletya.
        registerDig(ClueType.ELITE, new Position(3587, 3180, 0)); // Meiyerditch.
        registerDig(ClueType.ELITE, new Position(2820, 3078, 0)); // Tai Bwo Wannai. Hardwood Grove.
        registerDig(ClueType.ELITE, new Position(3811, 3060, 0)); // Small island north-east of Mos Le'Harmless.
        registerDig(ClueType.ELITE, new Position(2180, 3282, 0)); // North of Elf Camp.
        registerDig(ClueType.ELITE, new Position(2870, 2997, 0)); // North-east of Shilo Village.
        registerDig(ClueType.ELITE, new Position(3302, 2988, 0)); // On top of a cliff to the west of Pollnivneach.
        registerDig(ClueType.ELITE, new Position(2511, 2980, 0)); // Just south of Gu'Tanoth, west of gnome glider.
        registerDig(ClueType.ELITE, new Position(2732, 3372, 0)); // Legends' Guild.
        registerDig(ClueType.ELITE, new Position(3573, 3425, 0)); // North of Dessous's tomb from Desert Treasure.
        registerDig(ClueType.ELITE, new Position(3828, 2848, 0)); // East of Harmony Island.
        registerDig(ClueType.ELITE, new Position(3225, 2838, 0)); // South of Desert Treasure pyramid.
        registerDig(ClueType.ELITE, new Position(1773, 3510, 0)); // Ruins north of the Hosidius mine.
        registerDig(ClueType.ELITE, new Position(3822, 3562, 0)); // North-east of Dragontooth Island.
        registerDig(ClueType.ELITE, new Position(3603, 3564, 0)); // North of the wrecked ship, outside of Port Phasmatys.
        registerDig(ClueType.ELITE, new Position(2936, 2721, 0)); // Eastern shore of Crash Island.
        registerDig(ClueType.ELITE, new Position(2697, 2705, 0)); // South-west of Ape Atoll.
        registerDig(ClueType.ELITE, new Position(2778, 3678, 0)); // Mountain Camp.
        registerDig(ClueType.ELITE, new Position(2827, 3740, 0)); // West of the entrance to the Ice Path, where the Troll child resides.
        registerDig(ClueType.ELITE, new Position(2359, 3799, 0)); // Neitiznot.
        registerDig(ClueType.ELITE, new Position(2194, 3807, 0)); // Pirates' Cove.
        registerDig(ClueType.ELITE, new Position(2700, 3808, 0)); // Northwestern part of the Trollweiss and Rellekka Hunter area (DKS).
        registerDig(ClueType.ELITE, new Position(3215, 3835, 0)); // Wilderness. Lava Dragon Isle.
        registerDig(ClueType.ELITE, new Position(3369, 3894, 0)); // Wilderness. Fountain of Rune.
        registerDig(ClueType.ELITE, new Position(2065, 3923, 0)); // Outside the western wall on Lunar Isle.
        registerDig(ClueType.ELITE, new Position(3188, 3933, 0)); // Wilderness. Resource Area.
        registerDig(ClueType.ELITE, new Position(2997, 3953, 0)); // Wilderness. Inside Agility Training Area.
        registerDig(ClueType.ELITE, new Position(3380, 3963, 0)); // Wilderness. North of Volcano.
        registerDig(ClueType.ELITE, new Position(3051, 3736, 0)); // East of the Wilderness Obelisk in 28 Wilderness.
        registerDig(ClueType.ELITE, new Position(2316, 3814, 0)); // West of Neitiznot, near the bridge.
        registerDig(ClueType.ELITE, new Position(2872, 3937, 0)); // Weiss.
        // Master
        registerDig(ClueType.MASTER, new Position(2178, 3209, 0)); // South of Elf Camp.
        registerDig(ClueType.MASTER, new Position(2155, 3100, 0)); // South of Port Tyras (BJS).
        registerDig(ClueType.MASTER, new Position(2217, 3092, 0)); // Poison Waste island (DLR).
        registerDig(ClueType.MASTER, new Position(3830, 3060, 0)); // Small island located north-east of Mos Le'Harmless.
        registerDig(ClueType.MASTER, new Position(2834, 3271, 0)); // Crandor island.
        registerDig(ClueType.MASTER, new Position(2732, 3284, 0)); // Witchaven.
        registerDig(ClueType.MASTER, new Position(3622, 3320, 0)); // Meiyerditch. Outside mine.
        registerDig(ClueType.MASTER, new Position(2303, 3328, 0)); // East of Prifddinas.
        registerDig(ClueType.MASTER, new Position(3570, 3405, 0)); // North of Dessous's tomb from Desert Treasure.
        registerDig(ClueType.MASTER, new Position(2840, 3423, 0)); // Water Obelisk Island.
        registerDig(ClueType.MASTER, new Position(3604, 3564, 0)); // North of the wrecked ship, outside of Port Phasmatys (ALQ).
        registerDig(ClueType.MASTER, new Position(3085, 3569, 0)); // Wilderness. Obelisk of Air.
        registerDig(ClueType.MASTER, new Position(2934, 2727, 0)); // Eastern shore of Crash Island.
        registerDig(ClueType.MASTER, new Position(1451, 3695, 0)); // West side of Lizardman Canyon with Lizardman shaman.
        registerDig(ClueType.MASTER, new Position(2538, 3739, 0)); // Waterbirth Island. Bring a pet rock and rune thrownaxe.
        registerDig(ClueType.MASTER, new Position(1698, 3792, 0)); // Arceuus church.
        registerDig(ClueType.MASTER, new Position(2951, 3820, 0)); // Wilderness. Chaos Temple (level 38).
        registerDig(ClueType.MASTER, new Position(2202, 3825, 0)); // Pirates' Cove, between Lunar Isle and Rellekka.
        registerDig(ClueType.MASTER, new Position(1761, 3853, 0)); // Arceuus essence mine.
        registerDig(ClueType.MASTER, new Position(2090, 3863, 0)); // South of Lunar Isle, west of Astral altar.
        registerDig(ClueType.MASTER, new Position(1442, 3878, 0)); // Sulphur Mine.
        registerDig(ClueType.MASTER, new Position(3380, 3929, 0)); // Wilderness. Near Volcano.
        registerDig(ClueType.MASTER, new Position(3188, 3939, 0)); // Wilderness. Resource Area.
        registerDig(ClueType.MASTER, new Position(3304, 3941, 0)); // Wilderness. East of Rogues' Castle.
        registerDig(ClueType.MASTER, new Position(2994, 3961, 0)); // Wilderness. Inside Agility Training Area.
        registerDig(ClueType.MASTER, new Position(1248, 3751, 0)); // In the north wing of the Farming Guild.
    }
}
