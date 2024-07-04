package io.ruin.model.skills.construction;

import io.ruin.cache.def.ObjectDefinition;
import io.ruin.model.entity.player.Player;

import java.util.function.Predicate;

public enum HouseStyle {
    /**
     * 1856, 5696
     * ROOM HEIGHTS FOR FIRST CHUNK
     *  0 = basic wood
     *  1 = basic stone
     *  2 = whitewashed stone
     *  3 = fremennik wood
     */
    BASIC_WOOD(1, 5_000, 13098, 13099, 13100, 13101, 13048),
    BASIC_STONE(10, 100_000, 1902, 13091, 13094, 13096, 13079),
    WHITEWASHED_STONE(20, 150_000, 1415, 13005, 13006, 13007, 13072),
    FREMENNIK_WOOD(30, 200_000, 13111, 13112, 13109, 13107, 13086),
    /**
     * 1920, 5696
     * ROOM HEIGHTS FOR SECOND CHUNK
     *  0 = tropical wood
     *  1 = fancy stone
     *  2 = deathly mansion
     *  3 = twisted
     */
    TROPICAL_WOOD(40, 250_000, 13011, 10816, 13016, 13015, 13058),
    FANCY_STONE(50, 300_000, 13116, 13117, 13119, 13118, 13065),
    DEATHLY_MANSION(25, 1_000_000, 27082, 27083, 27084, 27085, 13065),
    TWISTED(1, 5000, 37467, 37469, 37465, 37463, 37478, player -> false),
    /**
     * 1984, 5696
     * ROOM HEIGHTS FOR THIRD CHUNK
     *  0 = hosidius
     *  1 = cozy cabin
     */
    HOSIDIUS(1, 5000, 39883, 39884, 37457, 37455, 39889, player -> false),
    COZY_CABIN(1, 35000, 40862, 40863, 40860, 40858, 39889, player -> false);

    public final int level;

    public final int cost;

    public final int wallId, windowId;

    public final int doorId1, doorId2;

    public final int changeX, changeZ;

    public final int dungeonWallId;

    public final Predicate<Player> canUse;

    HouseStyle(int level, int cost, int wallId, int windowId, int doorId1, int doorId2, int dungeonWallId) {
        this(level, cost, wallId, windowId, doorId1, doorId2, dungeonWallId, player -> true);
    }

    HouseStyle(int level, int cost, int wallId, int windowId, int doorId1, int doorId2, int dungeonWallId, Predicate<Player> canUse) {
        this.level = level;
        this.cost = cost;
        this.wallId = wallId;
        this.windowId = windowId;
        this.doorId1 = doorId1;
        this.doorId2 = doorId2;
        this.dungeonWallId = dungeonWallId;
        this.canUse = canUse;
        if (ordinal() > 7)
            changeX = 16;
        else if (ordinal() > 3)
            changeX = 8;
        else
            changeX = 0;
        changeZ = ordinal() % 4;
    }

    static {
        for (HouseStyle style : values()) {
            if (style == DEATHLY_MANSION)
                continue;
            ObjectDefinition.get(style.doorId1).reversedConstructionDoor = true;
            ObjectDefinition.get(style.doorId2).reversedConstructionDoor = true;
        }
    }
}