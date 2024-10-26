package io.ruin.model.map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/25/2024
 */
public class SinglePlusZone {

    /**
     * Adding
     */

    public static void add(int x, int y, int z) {
        set(x, y, z, true);
    }

    public static void add(Bounds... bounds) {
        set(true, bounds);
    }

    /**
     * Removing
     */

    public static void remove(int x, int y, int z) {
        set(x, y, z, false);
    }

    public static void remove(Bounds... bounds) {
        set(false, bounds);
    }

    /**
     * Setting
     */

    private static void set(boolean multi, Bounds... bounds) {
        for (Bounds b : bounds) {
            for (int x = b.swX; x <= b.neX; x++) {
                for (int y = b.swY; y <= b.neY; y++) {
                    if (b.z == -1) {
                        /**
                         * All heights
                         */
                        for (int z = 0; z < 4; z++)
                            set(x, y, z, multi);
                    } else {
                        /**
                         * Fixed height
                         */
                        set(x, y, b.z, multi);
                    }
                }
            }
        }
    }

    public static void set(int x, int y, int z, boolean singlePlus) {
        Tile.get(x, y, z, true).singlePlus = singlePlus;
    }

    /**
     * Loading
     */

    public static void load() {
        /**
         * By regions
         */
        int[] regions = {
                12701, 12702, 12703, 12957, 12958, 12959, // Revenant Caves
                7604, 7092, 6580, // Wilderness Bosses


        };
        for (int regionId : regions)
            set(true, Bounds.fromRegion(regionId));

        /**
         * By chunks
         */
        int[] chunks = {

        };
        for (int chunk : chunks) {
            int chunkAbsX = (chunk >> 16) << 3;
            int chunkAbsY = (chunk & 0xffff) << 3;
            set(true, new Bounds(chunkAbsX, chunkAbsY, chunkAbsX + 7, chunkAbsY + 7, 0));
        }
        /**
         * By bounds
         */
        Bounds[] bounds = {
                // Wilderness agility course
                new Bounds(2988, 3934, 3006, 3946, 0),
                new Bounds(2992, 3946, 3008, 3948, 0),
                new Bounds(2993, 3949, 3008, 3950, 0),
                new Bounds(2992, 3950, 3008, 3950, 0),
                new Bounds(2991, 3951, 3008, 3952, 0),
                new Bounds(2990, 3952, 3009, 3966, 0)
        };
        for (Bounds b : bounds)
            set(true, b);
    }
}

