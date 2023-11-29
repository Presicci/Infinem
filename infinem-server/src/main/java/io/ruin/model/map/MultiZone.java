package io.ruin.model.map;

import io.ruin.model.activities.combat.bosses.zulrah.Zulrah;
import io.ruin.model.activities.wilderness.BloodyChest;

public class MultiZone {

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
        for(Bounds b : bounds) {
            for(int x = b.swX; x <= b.neX; x++) {
                for(int y = b.swY; y <= b.neY; y++) {
                    if(b.z == -1) {
                        /**
                         * All heights
                         */
                        for(int z = 0; z < 4; z++)
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

    public static void set(int x, int y, int z, boolean multi) {
        Tile.get(x, y, z, true).multi = multi;
    }

    /**
     * Loading
     */

    public static void load() {
        /**
         * By regions
         */
        int[] regions = {
                /** Safe: **/
                11828, 11829, //Falador
                12341, //Barbarian Village
                8253, 8252, 8508, 8509, 8254, //Lunar Isle:
                9273, 9017, //Piscatoris Fishing Colony
                9532, 9276, //Fremennik Isles
                10549, //Ranging Guild
                10034, //Battlefield
                10029, //Feldip hills
                11318, //White wolf mountain
                11050, 11051, 10794, 10795,//Apeatoll
                12590, //Bandit camp
                13105, //Al Kharid
                11602, 11603, 11346, 11347, //Godwars Dungeon
                13131, 13387, //FFA clan wars, top half
                11844, //Corporeal beast
                11589, 11588, //Dagannoths
                14682, //Kraken cave
                8023, //Gnome Stronghold crash site (monkey madness)
                13972, // Kalphite queen lair
                13204, 13205, // Kalphite cave
                12363, 12362, 12106, 11851, 11850, // Abyssal Sire
                14938, 14939, // Smokedevil room in Nieve's cave + kalphite hive room
                9023, // vorkath island
                12889, // olm chamber
                7322,   // Sarachnis
                7991,   // Home
                12690,  // Smoke dungeon task only area
                14129,  // South-east Burgh de Rott
                13876,  // The Hollows in mort myre
                8763,   // Pirates' cove
                8493, 8749, 9005,   // Soul wars
                9520,   // Castle wars
                11325, 11581,   // Weiss
                5941,   // Land's End
                6710,   // Charcoal burners
                /** Wildy: (uses 8x8 chunks for some sections as well as chunks) **/
                12599, //Wilderness Ditch
                12855, 12856, //Mammoths (lvl 9)
                13111, 13112, 13113, 13114, 13115, 13116, 13117, //Varrock -> GDZ
                12857, 12858, 12859,12860,12861, //East graveyard (lvl 17)
                13372, 13373, //East of Callisto (lvl 41)
                12604, //Black chins (lvl 33)
                12088, 12089, //North of dark warriors (lvl 17)
                12961, //Scorpia pit
                9033, // KBD zone
                9551, //Fight caves
                9043, //Inferno
                12107, //Abyss
                9619, //Smoke devil dungeon
                12960, 12958, 12957,
                6810, // Skotizo lair
                10536, // Pest Control battlegrounds
        };
        for(int regionId : regions)
            set(true, Bounds.fromRegion(regionId));
            // Lava maze dungeon (for the bloody chest)
            set(true, BloodyChest.BLOODY_DUNGEON);
        /*
         * By chunks
         */
        int[] chunks = {
                // Chaos temple - Crazy Arch 44s
                24117725, 24117726, 24183261, 24183262,

                //Black chins
                25756120, 25756121, 25756122, 25756123, 25756124, 25756125, 25756126, 25756127,
                25821656, 25821657, 25821658, 25821659, 25821660, 25821661, 25821662, 25821663,
                25887192, 25887193, 25887194, 25887195, 25887196, 25887197, 25887198, 25887199,
                25952728, 25952729, 25952730, 25952731, 25952732, 25952733, 25952734, 25952735,
                26018264, 26018265, 26018266, 26018267, 26018268, 26018269, 26018270, 26018271,
                26083800, 26083801, 26083802, 26083803, 26083804, 26083805, 26083806, 26083807,
                26149336, 26149337, 26149338, 26149339, 26149340, 26149341, 26149342, 26149343,


                //KBD Cage
                24642018, 24642019, 24642020, 24642021, 24642022, 24642023,
                24707554, 24707555, 24707556, 24707557, 24707558, 24707559,
                24773090, 24773091, 24773092, 24773093, 24773094, 24773095,
                24838626, 24838627, 24838628, 24838629, 24838630, 24838631,
                24904162, 24904163, 24904164, 24904165, 24904166, 24904167,

                //Rune rocks north of KBD cage
                24969699, 24969703, 25035239, 25100775,

                // Wilderness agility course at 55 wilderness
                24445417, 24510953, 24576489,
                24445418, 24510954, 24576490,

                // Death plateau
                23331266, 23396802, 23462338, 23527874,

                // Trollheim
                23855565, 23790029, 23724493,

                // North of godwars
                23724502, 23790038,

                // Arandar
                19464607, 19530143,

                // Jiggig
                20119932, 20119933, 20185468, 20185469, 20251004,
                20251005, 20316540, 20316541, 20382076, 20382077,

                // Necromancer tower
                21823892, 21823893, 21889428, 21889429,

                // Tower of magic
                12779997, 12845532, 12845533, 12845534, 12911067, 12911068, 12911069,
                12911070, 12911071, 12976604, 12976605, 12976606, 13042141,

                // Hosidius market
                14352833, 14352834, 14418369, 14418370
        };
        for(int chunk : chunks) {
            int chunkAbsX = (chunk >> 16) << 3;
            int chunkAbsY = (chunk & 0xffff) << 3;
            set(true, new Bounds(chunkAbsX, chunkAbsY, chunkAbsX + 7, chunkAbsY + 7, 0));
            set(true, new Bounds(chunkAbsX, chunkAbsY, chunkAbsX + 7, chunkAbsY + 7, 1));
        }
        /**
         * By bounds
         */
        Bounds[] bounds = {
                /* wilderness agility area */
                new Bounds(2984, 3912, 3007, 3927, 0),
                /* waterbirth dungeon */
                new Bounds(2433, 10115, 2560, 10177, 0),
                new Bounds(1792, 4330, 1984, 4452, 0),
                new Bounds(1792, 4330, 1984, 4452, 1),
                new Bounds(1792, 4330, 1984, 4452, 2),
                new Bounds(1792, 4330, 1984, 4452, 3),

                /* catacombs of kourend */
                new Bounds(1598, 9963, 1766, 10067, -1),
                new Bounds(1638, 10067, 1737, 10111, -1),

                /* Kraken boss room */
                new Bounds(2270, 10019, 2293, 10045, -1),

                /* Zulrah arena */
                Zulrah.SHRINE_BOUNDS,

                /* Wilderness Godwars */
                new Bounds(3013, 10108, 3078, 10177, 0),

                /* Raids source area */
                new Bounds(3264, 5152, 3400, 5727, -1),

                /* Revs caves */
                new Bounds(3233, 10229, 3235, 10231, -1),
                new Bounds(3136, 10061, 3263, 10228, -1),
                new Bounds(3208, 10048, 3263, 10082, -1),

                new Bounds(1357, 10193, 1378, 10220, 1),

                // South of port phasmatys
                new Bounds(3584, 3408, 3667, 3453, 0),
                new Bounds(3682, 3437, 3704, 3451, 0),

                // Draynor jail
                new Bounds(3110, 3236, 3131, 3258, 0),

                // Wizard tower
                new Bounds(3094, 3148, 3127, 3175, 0),

                // South falador
                new Bounds(2944, 3304, 3015, 3327, 0),

                // Falador cow pen
                new Bounds(3014, 3297, 3043, 3313, 0),

                // Burthorpe
                new Bounds(2880, 3520, 2903, 3543, 0),

                // Chaos temple
                new Bounds(2930, 3513, 2940, 3518, 0),

                // Ferox chunk
                new Bounds(3152, 3584, 3199, 3623, 0),
                new Bounds(3144, 3597, 3151, 3619, 0),

                // Ferox bridge
                new Bounds(3176, 3640, 3191, 3647, 0),
                new Bounds(3183, 3635, 3199, 3639, 0),
                new Bounds(3184, 3624, 3199, 3631, 0),
                new Bounds(3188, 3632, 3199, 3634, 0),

                // Boneyard
                new Bounds(3192, 3640, 3199, 3751, 0),
                new Bounds(3152, 3752, 3199, 3775, 0),

                // Center wildy north of lava maze
                new Bounds(3112, 3872, 3135, 3879, 0),
                new Bounds(3072, 3880, 3135, 3903, 0),
                new Bounds(3008, 3896, 3071, 3903, 0),
                new Bounds(3008, 3856, 3047, 3895, 0),
                new Bounds(3021, 3854, 3023, 3855, 0),

                // Trollheim
                new Bounds(2880, 3696, 2911, 3719, 0),
                new Bounds(2888, 3720, 2919, 3759, 0),

                // Rellekka
                new Bounds(2653, 3712, 2735, 3735, 0),

                // Neitiznot
                new Bounds(2304, 3821, 2381, 3839, 0),

                // Fossil island SE beach
                new Bounds(3799, 3748, 3823, 3767, 0),

                // Fossil island N beach
                new Bounds(3704, 3840, 3759, 3855, 0),
                new Bounds(3704, 3856, 3743, 3903, 0),
                new Bounds(3680, 3896, 3703, 3901, 0),

                // Fossil island NW beach
                new Bounds(3654, 3867, 3663, 3887, 0),

                // Piscarilius
                new Bounds(1742, 3661, 1855, 3764, 0),
                new Bounds(1743, 3726, 1773, 3801, 0),

                // Arceuus
                new Bounds(1616, 3712, 1735, 3775, 0),
                new Bounds(1664, 3776, 1718, 3805, 0),

                // Lovakengj
                new Bounds(1514, 3830, 1520, 3837, -1), // Thirus's shop
                new Bounds(1551, 3794, 1558, 3796, 0),
                new Bounds(1552, 3797, 1554, 3798, 0),
                new Bounds(1553, 3780, 1559, 3787, 0),
                new Bounds(1540, 3780, 1547, 3782, 0),
                new Bounds(1562, 3757, 1570, 3765, 0),  // Bar
                new Bounds(1570, 3757, 1574, 3758, 0),
                new Bounds(1565, 3752, 1569, 3756, 0),
                new Bounds(1502, 3752, 1511, 3754, 0),  // Furnace building
                new Bounds(1505, 3755, 1508, 3766, 0),
                new Bounds(1499, 3767, 1514, 3773, 0),

                // Lizardman Canyon
                new Bounds(1418, 3697, 1456, 3724, 0),
                new Bounds(1475, 3691, 1512, 3722, 0),

                // Settlement ruins
                new Bounds(1536, 3864, 1599, 3919, 0),

                // Mount Guidamortem
                new Bounds(1194, 3553, 1208, 3567, 0),
                new Bounds(1199, 3533, 1215, 3552, 0),
                new Bounds(1207, 3524, 1246, 3533, 0),
                new Bounds(1207, 3480, 1250, 3523, 0),
                new Bounds(1251, 3486, 1284, 3511, 0),
                new Bounds(1285, 3498, 1302, 3510, 0),

                // Shayziens' Wall
                new Bounds(1350, 3487, 1415, 3591, 0),
                new Bounds(1336, 3576, 1391, 3599, 0),

                // Graveyard
                new Bounds(1425, 3584, 1444, 3597, 0),

                // Graveyard of heroes
                new Bounds(1475, 3544, 1519, 3579, 0),

                // Woodcutting guild
                new Bounds(1563, 3477, 1600, 3503, 0),
                new Bounds(1601, 3497, 1606, 3500, 0),
                new Bounds(1607, 3487, 1623, 3519, 0),
                new Bounds(1624, 3493, 1632, 3519, 0),
                new Bounds(1633, 3489, 1663, 3519, 0),

                // Saltpetre area
                new Bounds(1664, 3520, 1727, 3562, 0),
                new Bounds(1662, 3562, 1684, 3590, 0),
                new Bounds(1603, 3508, 1663, 3560, 0),

                // Barbarian hut
                new Bounds(1575, 3428, 1588, 3437, 0),

                // Sandcrab shore
                new Bounds(1728, 3456, 1799, 3476, 0),
                new Bounds(1796, 3436, 1855, 3471, 0),
                new Bounds(1840, 3464, 1887, 3535, 0),
                new Bounds(1840, 3535, 1887, 3583, 0),

                // Crabclaw isle
                new Bounds(1743, 3399, 1801, 3454, 0),

                // Monk camp
                new Bounds(1732, 3486, 1756, 3502, 0),

                // Goblin/bat ruins
                new Bounds(1766, 3501, 1786, 3515, 0),

                // Onion field
                new Bounds(1746, 3521, 1759, 3527, 0),

                // Cabbage field
                new Bounds(1746, 3543, 1757, 3556, 0),

                // Hosidius bar
                new Bounds(1737, 3610, 1750, 3622, 0),

                // Hosidius cow pen
                new Bounds(1745, 3634, 1766, 3646, 0),

                // Hosidius pig pen
                new Bounds(1677, 3634, 1689, 3642, 0),

                // Kourend castle
                new Bounds(1592, 3656, 1658, 3690, 0)
        };
        for(Bounds b : bounds)
            set(true, b);
    }

}
