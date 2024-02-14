package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ObjectDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public enum Ladder {
    //UP(1, "climb-up", new Position(), new Position(), true),
    //DOWN(1, "climb-down", new Position(), new Position(), false),
    APE_ATOLL_SOUTH_LADDER_DOWN(4744, "climb-down", new Position(2803, 2734, 2), new Position(2803, 2734, 0), false),
    APE_ATOLL_SOUTH_LADDER_UP(4743, "climb-up", new Position(2803, 2734), new Position(2803, 2733, 2), true),

    FOSSIL_ISLAND_HOUSE_DOWN(30725, "climb-down", new Position(3767, 3866, 1), new Position(3767, 3868, 0), false),

    FOSSIL_ISLAND_BRIDGE_WEST_DOWN(30939, "climb down", new Position(3729, 3831, 1), new Position(3728, 3831, 0), false),
    FOSSIL_ISLAND_BRIDGE_WEST_UP(30938, "climb up", new Position(3729, 3831), new Position(3730, 3831, 1), true),

    FOSSIL_ISLAND_BRIDGE_EAST_DOWN(30941, "climb down", new Position(3746, 3831, 1), new Position(3747, 3831, 0), false),
    FOSSIL_ISLAND_BRIDGE_EAST_UP(30940, "climb up", new Position(3746, 3831), new Position(3745, 3831, 1), true),

    DORGESH_KAAN_DUNGEON_DOWN(22666, "climb-down", new Position(2719, 5241, 3), new Position(2715, 5241, 0), false),
    DORGESH_KAAN_DUNGEON_UP(22600, "climb-up", new Position(2716, 5241), new Position(2720, 5241, 3), true),

    CHAMPION_DUNGEON_UP(10560, "climb-up", new Position(3190, 9758), new Position(3191, 3355, 0), true),

    MAUSOLEUM_DOWN(5167, "push", new Position(3505, 3571), new Position(3504, 9969, 0), false),
    MAUSOLEUM_UP(17387, "climb-up", new Position(3504, 9970), new Position(3504, 3571, 0), true),

    EXPERIMENTS_DOWN(5167, "push", new Position(3578, 3527), new Position(3577, 9927, 0), false),
    EXPERIMENTS_UP(17387, "climb-up", new Position(3578, 9927), new Position(3578, 3526, 0), true),

    SHILO_GEM_MINE_DOWN(23586, 1, new Position(2825, 2998), new Position(2838, 9387), false),
    SHILO_GEM_MINE_UP(23584, "climb-up", new Position(2838, 9388), new Position(2825, 2999), true),

    LIGHTHOUSE_DOWN(4383, "climb", new Position(2509, 3644), new Position(2519, 9994, 0), false),
    LIGHTHOUSE_UP(4412, "climb", new Position(2519, 9994), new Position(2510, 3644), true),

    CRASH_ISLAND_DUNGEON_DOWN(15572, "enter", new Position(2921, 2721), new Position(3024, 5487, 0), false),
    CRASH_ISLAND_DUNGEON_UP(15571, "climb", new Position(3023, 5488), new Position(2922, 2720, 0), true),

    SOURHOG_CAVE_EXIT(40330, "climb-up", new Position(3157, 9714), new Position(3151, 3349, 0), true),

    CHAOS_TEMPLE_UP(31580, "climb", new Position(2939, 3518), new Position(2939, 3517, 1), true),
    CHAOS_TEMPLE_DOWN(31579, "climb", new Position(2939, 3518, 1), new Position(2939, 3517, 0), false),

    SMOKE_DUNGEON_ENTRANCE(6279, "climb-down", new Position(3310, 2962), new Position(3206, 9379, 0), false),
    SMOKE_DUNGEON_EXIT(6439, "climb-up", new Position(3205, 9379), new Position(3311, 2962, 0), true),

    WIZARDS_GUILD_BASEMENT_UP(17385, "climb-up", new Position(2594, 9485), new Position(2594, 3086, 0), true),
    WIZARDS_GUILD_BASEMENT_DOWN(17384, "climb-down", new Position(2594, 3085), new Position(2594, 9486, 0), false),

    TREE_GNOME_VILLAGE_DUNGEON_ENTRANCE(5250, "climb-down", new Position(2533, 3155), new Position(2597, 4434, 0), false),
    TREE_GNOME_VILLAGE_DUNGEON_EXIT(5251, "climb-up", new Position(2597, 4435), new Position(2533, 3156, 0), true),

    SHAYZIEN_CRYPT_ENTRANCE(32403, "enter", new Position(1483, 3548), new Position(1483, 9951, 3), false),
    SHAYZIEN_CRYPT_EXIT(32401, "climb-up", new Position(1483, 9950, 3), new Position(1483, 3549), true),

    SHAYZIEN_CRYPT_1_UP(32398, "climb-up", new Position(1524, 9967, 2), new Position(1524, 9966, 3), true),
    SHAYZIEN_CRYPT_1_DOWN(32397, "climb-down", new Position(1524, 9967, 3), new Position(1523, 9967, 2), false),

    SHAYZIEN_CRYPT_2_UP(32400, "climb-up", new Position(1483, 9933, 2), new Position(1483, 9934, 3), true),
    SHAYZIEN_CRYPT_2_DOWN(32399, "climb-down", new Position(1483, 9933, 3), new Position(1484, 9933, 2), false),

    LUMBRIDGE_SWAMP_CAVE_EXIT(5946, "climb", new Position(3169, 9572), new Position(3169, 3171), true),

    SHADOW_DUNGEON_ENTRANCE(6560, 1, new Position(2547, 3421), new Position(2630, 5072, 0), false),
    SHADOW_DUNGEON_EXIT(6450, "climb-up", new Position(2629, 5072), new Position(2546, 3421, 0), true),

    PETERDOMUS_BASEMENT_EAST_EXIT(17385, "climb-up", new Position(3405, 9907), new Position(3405, 3506), true),

    PORT_SARIM_RAT_UP(10309, "climb-up", new Position(2962, 9651), new Position(3017, 3232, 0), true),
    PORT_SARIM_RAT_DOWN(10321, "climb-down", new Position(3018, 3232), new Position(2962, 9650, 0), false),

    CRUMBLING_TOWER_UP(40746, "climb-up", new Position(1941, 9451, 0), new Position(2132,  2994, 0), true),
    CRUMBLING_TOWER_DOWN(40745, "climb-down", new Position(2131, 2994, 0), new Position(1940, 9451, 0), false),

    WATCHTOWER_UP(2796, "climb-up", new Position(2549, 3111, 1), new Position(2933, 4712, 2), true),
    WATCHTOWER_DOWN(2797, "climb-down", new Position(2933, 4711, 2), new Position(2549, 3112, 1), false),

    SOPHANEM_BANK_UP(20277, "climb-up", new Position(2799, 5159, 0), new Position(3315, 2796, 0), true),
    SOPHANEM_BANK_DOWN(20275, "climb-down", new Position(3315, 2797, 0), new Position(2799, 5160, 0), false),

    SOPHANEM_UP(20356, "climb-up", new Position(3308, 2803, 0), new Position(3307, 2803, 2), true),
    SOPHANEM_DOWN(20357, "climb-down", new Position(3308, 2803, 2), new Position(3309, 2803, 0), false),

    SOPHANEM_DUNGEON_UP(20281, "climb-up", new Position(3318 - 1152, 9274 - 4864, 2), new Position(2800, 5160, 0), true),
    SOPHANEM_DUNGEON_DOWN(20278, "climb-down", new Position(2800, 5159), new Position(3318 - 1152, 9273 - 4864, 2), false),

    SOPHANEM_DUNGEON_MAZE_KALEEF_UP(20286, "climb-up", new Position(2297, 4297, 0), new Position(2116, 4364, 2), true),
    SOPHANEM_DUNGEON_MAZE_KALEEF_DOWN(20287, "climb-down", new Position(2116, 4365, 2), new Position(2297, 4298, 0), false),

    SOPHANEM_DUNGEON_MAZE_1_UP(20284, "climb-up", new Position(3271 - 1152, 9274 - 4864, 0), new Position(3270 - 1152, 9274 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_1_DOWN(20285, "climb-down", new Position(3271 - 1152, 9274 - 4864, 2), new Position(3272 - 1152, 9274 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_2_UP(20284, "climb-up", new Position(3286 - 1152, 9274 - 4864, 0), new Position(3286 - 1152, 9275 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_2_DOWN(20285, "climb-down", new Position(3286 - 1152, 9274 - 4864, 2), new Position(3286 - 1152, 9273 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_3_UP(20284, "climb-up", new Position(3317 - 1152, 9250 - 4864, 0), new Position(3317 - 1152, 9249 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_3_DOWN(20285, "climb-down", new Position(3317 - 1152, 9250 - 4864, 2), new Position(3317 - 1152, 9251 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_4_UP(20284, "climb-up", new Position(3280 - 1152, 9255 - 4864, 0), new Position(3280 - 1152, 9256 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_4_DOWN(20285, "climb-down", new Position(3280 - 1152, 9255 - 4864, 2), new Position(3280 - 1152, 9254 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_5_UP(20284, "climb-up", new Position(3271 - 1152, 9235 - 4864, 0), new Position(3272 - 1152, 9235 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_5_DOWN(20285, "climb-down", new Position(3271 - 1152, 9235 - 4864, 2), new Position(3270 - 1152, 9235 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_6_UP(20284, "climb-up", new Position(3323 - 1152, 9241 - 4864, 0), new Position(3323 - 1152, 9242 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_6_DOWN(20285, "climb-down", new Position(3323 - 1152, 9241 - 4864, 2), new Position(3323 - 1152, 9240 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_7_UP(20284, "climb-up", new Position(3267 - 1152, 9221 - 4864, 0), new Position(3267 - 1152, 9220 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_7_DOWN(20285, "climb-down", new Position(3267 - 1152, 9221 - 4864, 2), new Position(3267 - 1152, 9222 - 4864, 0), false),

    SOPHANEM_DUNGEON_MAZE_8_UP(20284, "climb-up", new Position(3317 - 1152, 9224 - 4864, 0), new Position(3317 - 1152, 9225 - 4864, 2), true),
    SOPHANEM_DUNGEON_MAZE_8_DOWN(20285, "climb-down", new Position(3317 - 1152, 9224 - 4864, 2), new Position(3317 - 1152, 9223 - 4864, 0), false),

    TROLL_STRONGHOLD_EXIT(18834, "climb-up", new Position(2831, 10077, 2), new Position(2831, 3678), true),
    TROLL_STRONGHOLD_ENTRANCE(18833, "climb-down", new Position(2831, 3677, 0), new Position(2831, 10077, 2), false);

    private final int id;
    private String optionString = "";
    private int option = -1;
    private final Position objectPos, destinationPos;
    private final boolean up;

    Ladder(int id, String option, Position objectPos, Position destinationPos, boolean up) {
        this.id = id;
        this.optionString = option;
        this.objectPos = objectPos;
        this.destinationPos = destinationPos;
        this.up = up;
    }

    Ladder(int id, int option, Position objectPos, Position destinationPos, boolean up) {
        this.id = id;
        this.option = option;
        this.objectPos = objectPos;
        this.destinationPos = destinationPos;
        this.up = up;
    }

    public static void climb(Player player, Position position, boolean climbingUp, boolean animate, boolean tileCheck) {
        climb(player, position.getX(), position.getY(), position.getZ(), climbingUp, animate, tileCheck);
    }

    public static void climb(Player player, int x, int y, int height, boolean climbingUp, boolean animate, boolean tileCheck) {
        if (tileCheck) {
           if(!climbingUp && player.getHeight() == 0) {
               player.dialogue(new PlayerDialogue("I don't think this ladder leads anywhere."));
               return;
           }
        }
        if (animate) {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(climbingUp ? 828 : 827);
                e.delay(1);
                player.getMovement().teleport(x, y, height);
                player.unlock();
            });
        } else {
            player.startEvent(e -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.getMovement().teleport(x, y, height);
                e.delay(1);
                player.unlock();
            });
        }
    }

    static {
        for (Ladder entry : values()) {
            if (entry.option > 0) {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.option, ((player, obj) -> climb(player, entry.destinationPos, entry.up, true, false)));
            } else {
                ObjectAction.register(entry.id, entry.objectPos.getX(), entry.objectPos.getY(), entry.objectPos.getZ(), entry.optionString, ((player, obj) -> climb(player, entry.destinationPos, entry.up, true, false)));
            }
        }
        ObjectDef.forEach(def -> {
            if ((def.name.equalsIgnoreCase("ladder") || def.name.equalsIgnoreCase("bamboo ladder"))
                    && def.defaultActions == null) {
                /**
                 * Climb up
                 */
                ObjectAction.register(def.id, "climb-up", (p, obj) -> {
                    climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() + 1, true, true, true);
                });
                /**
                 * Climb down
                 */
                ObjectAction.register(def.id, "climb-down", (p, obj) -> {
                    climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() - 1, false, true, true);
                });
                /**
                 * Climb
                 */
                ObjectAction.register(def.id, "climb", (p, obj) -> {
                    p.dialogue(
                            new OptionsDialogue("Climb up or down the ladder?",
                                    new Option("Climb up the ladder.", () -> climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() + 1, true, true, true)),
                                    new Option("Climb down the ladder.", () -> climb(p, p.getAbsX(), p.getAbsY(), p.getHeight() - 1, false, true, true))
                            ));
                });
            }
        });

        /**
         * Individually handled ladders
         */
        //Edgeville dungeon
        ObjectAction.register(17385, 3097, 9867, 0, "climb-up", (player, obj) -> climb(player, 3096, 3468, 0, true, true, false));

        //Edgeville -> Air Obelisk
        ObjectAction.register(17385, 3088, 9971, 0, "climb-up", (player, obj) -> climb(player, 3089, 3571, 0, true, true, false));

        //Air Obelisk -> Edgeville
        ObjectAction.register(16680, 3088, 3571, 0, "climb-down", (player, obj) -> climb(player, 3087, 9971, 0, false, true, false));

        //Camelot spinning wheel ladder
        ObjectAction.register(26118, 2715, 3472, 1, "climb-up", (player, obj) -> climb(player, 2714, 3472, 3, true, true, false));

        //Camelot spinning wheel roof ladder down
        ObjectAction.register(26119, 2715, 3472, 3, "climb-down", (player, obj) -> climb(player, 2714, 3472, 1, false, true, false));

        //Pirates' cove
        ObjectAction.register(16960, 2213, 3795, 0, "climb", ((player, obj) -> climb(player, 2213, 3796, 1, true, true, false)));
        ObjectAction.register(16962, 2213, 3795, 1, "climb", ((player, obj) -> climb(player, 2213, 3794, 0, false, true, false)));
        ObjectAction.register(16959, 2214, 3801, 1, "climb", ((player, obj) -> climb(player, 2214, 3800, 2, true, true, false)));
        ObjectAction.register(16961, 2214, 3801, 2, "climb", ((player, obj) -> climb(player, 2214, 3802, 1, false, true, false)));
        ObjectAction.register(16962, 2212, 3809, 1, "climb", ((player, obj) -> climb(player, 2211, 3809, 0, false, true, false)));
        ObjectAction.register(16960, 2212, 3809, 0, "climb", ((player, obj) -> climb(player, 2213, 3809, 1, true, true, false)));

        //Lunar Isle
        ObjectAction.register(16961, 2127, 3893, 2, "climb", ((player, obj) -> climb(player, 2126, 3893, 1, false, true, true)));
        ObjectAction.register(16959, 2127, 3893, 1, "climb", ((player, obj) -> climb(player, 2128, 3893, 2, true, true, true)));
        ObjectAction.register(16962, 2118, 3894, 1, "climb", ((player, obj) -> climb(player, 2118, 3895, 0, false, true, true)));
        ObjectAction.register(16960, 2118, 3894, 0, "climb", ((player, obj) -> climb(player, 2118, 3893, 1, true, true, true)));

        //Draynor Sewer
        ObjectAction.register(17385, 3084, 9672, 0, "climb", (player, obj) -> climb(player, 3084, 3273, 0, true, true, false));

        /**
         * Stairs
         */
        //Lucien's house (there is a clue upstairs)
        ObjectAction.register(16671, 2572, 3325, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2574, 3325, 1));
        ObjectAction.register(16673, 2573, 3325, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2573, 3324, 0));

        //Home
        //ObjectAction.register(25801, 2021, 3566, 1, "climb-down", (player, obj) -> player.getMovement().teleport(2021, 3567, 0));
        //ObjectAction.register(25935, 2020, 3565, 0, "climb-up", (player, obj) -> player.getMovement().teleport(2021, 3567, 1));

        /**
         * Ladders we don't want to work!
         */
        ObjectAction.register(17028, 3154, 3743, 0, 1, (player, obj) -> player.sendFilteredMessage("This ladder looks broken.. maybe I shouldn't climb up."));

    }
}
