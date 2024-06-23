package io.ruin.model.activities.cluescrolls.impl;

import io.ruin.model.activities.cluescrolls.Clue;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.activities.cluescrolls.StepType;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Tile;

public class MapClue extends Clue {

    private final int interfaceId;

    private MapClue(int interfaceId, ClueType type) {
        super(type, StepType.MAP);
        this.interfaceId = interfaceId;
    }

    @Override
    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, interfaceId);
    }

    /**
     * Register
     */

    private static void registerDig(int interfaceId, int x, int y, int z, ClueType type) {
        MapClue clue = new MapClue(interfaceId, type);
        Tile.get(x, y, z, true).digAction = clue::advance;
    }

    private static void registerObj(int interfaceId, int objectId, int x, int y, int z, ClueType type) {
        Tile.getObject(objectId, x, y, z).mapClue = new MapClue(interfaceId, type);
    }

    static {
        // Beginner
        registerDig(346, 3166, 3360, 0, ClueType.BEGINNER);     // Champion's guild
        registerDig(351, 3043, 3399, 0, ClueType.BEGINNER);     // NE Falador
        registerDig(348, 3091, 3227, 0, ClueType.BEGINNER);     // Draynor
        registerDig(347, 3289, 3374, 0, ClueType.BEGINNER);     // Varrock east mine
        registerDig(356, 3110, 3152, 0, ClueType.BEGINNER);     // Wizard tower
        // Easy
        registerDig(346, 3166, 3360, 0, ClueType.EASY);     // Champion's guild
        registerDig(87, 3300, 3290, 0, ClueType.EASY);      // Al kharid
        registerDig(356, 3110, 3152, 0, ClueType.EASY);     // Wizard tower
        registerDig(354, 2612, 3482, 0, ClueType.EASY);     // Galahad's house
        registerDig(351, 3043, 3399, 0, ClueType.EASY);     // NE Falador
        registerDig(347, 3289, 3374, 0, ClueType.EASY);     // Varrock east mine
        registerDig(337, 2970, 3414, 0, ClueType.EASY);     // N Falador
        // Medium
        registerObj(361, 354, 2565, 3248, 0, ClueType.MEDIUM);  // Clock tower
        registerObj(355, 357, 2658, 3488, 0, ClueType.MEDIUM);  // Mcgrubor's wood
        registerDig(343, 2578, 3597, 0, ClueType.MEDIUM);   // West of Rellekka
        registerDig(342, 2455, 3230, 0, ClueType.MEDIUM);   // Ourania altar
        registerDig(341, 3434, 3265, 0, ClueType.MEDIUM);   // Mort'ton
        registerDig(360, 2651, 3231, 0, ClueType.MEDIUM);   // South of Ardy
        registerDig(340, 2536, 3865, 0, ClueType.MEDIUM);   // Miscellania
        registerDig(362, 2924, 3209, 0, ClueType.MEDIUM);   // Rimmington
        registerDig(352, 2906, 3293, 0, ClueType.MEDIUM);   // Crafting guild
        registerDig(344, 2666, 3562, 0, ClueType.MEDIUM);   // North of Seers
        registerDig(348, 3091, 3227, 0, ClueType.MEDIUM);   // Draynor
        // Hard
        registerObj(350, 2620, 3309, 3503, 0, ClueType.HARD);  // Lumber yard
        registerObj(358, 18506, 2457, 3182, 0, ClueType.HARD);  // Observatory
        registerObj(359, 354, 3026, 3628, 0, ClueType.HARD);    // Dark Warrior's fortress
        registerDig(353, 2616, 3077, 0, ClueType.HARD);     // Yanille
        registerDig(338, 3021, 3912, 0, ClueType.HARD);     // 50 wildy
        registerDig(339, 2723, 3338, 0, ClueType.HARD);     // South of legends
        registerDig(357, 2488, 3308, 0, ClueType.HARD);     // West ardy
        // Elite
        registerDig(317, 2202, 3062, 0, ClueType.ELITE);    // Zul-andra
        registerDig(86, 2449, 3130, 0, ClueType.ELITE);     // NE of Castle wars
        // MUDSKIPPER CAVE - inter 314
    }
}

