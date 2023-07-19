package io.ruin.model.skills.agility;

import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.agility.shortcut.*;

public class Shortcuts {
    static {
        // (Grapple) Over the River Lum to Al Kharid (FUCK THAT)

        // (Grapple) Scale Falador wall
        ObjectAction.register(17050, 3032, 3389, 0, "grapple", (p, obj) -> Grappling.grapple(p, obj, 11, 19, 37, 4455, 760, 10, Position.of(3032, 3388, 0), Position.of(3032, 3389, 1)));
        ObjectAction.register(17049, 3033, 3390, 0, "grapple", (p, obj) -> Grappling.grapple(p, obj, 11, 19, 37, 4455, 760, 10, Position.of(3033, 3390, 0), Position.of(3033, 3389, 1)));
        ObjectAction.register(17051, 3033, 3390, 1, "jump", ((player, obj) -> {
            player.startEvent(event -> {
                player.animate(2586);
                event.delay(1);
                player.getMovement().teleport(3033, 3390, 0);
            });
        }));
        ObjectAction.register(17052, 3032, 3388, 1, "jump", ((player, obj) -> {
            player.startEvent(event -> {
                player.animate(2586);
                event.delay(1);
                player.getMovement().teleport(3032, 3388, 0);
            });
        }));

        // Stepping stones in Brimhaven Dungeon
        // Varrock south fence jump
        // Scale Goblin Village wall
        // Monkey bars under Edgeville
        // Yanille Agility Shortcut
        ObjectAction.register(16519, "climb-under", (p, obj) -> UnderwallTunnel.shortcutVertical(p, obj, 16, Direction.NORTH, 5));
        ObjectAction.register(16520, "climb-into", (p, obj) -> UnderwallTunnel.shortcutVertical(p, obj, 16, Direction.SOUTH, 5));
        // Piscatoris entrance
        ObjectAction.register(12656, 2344, 3651, 0, "enter", (p, obj) -> UnderwallTunnel.shortcutVertical(p, obj, 1, Direction.NORTH, 5));
        ObjectAction.register(12656, 2344, 3654, 0, "enter", (p, obj) -> UnderwallTunnel.shortcutVertical(p, obj, 1, Direction.SOUTH, 5));
        // Watchtower wall climb
        // Coal Truck log balance

        // Grand Exchange Agility Shortcut
        ObjectAction.register(16529, "climb-into", (p, obj) -> UnderwallTunnel.shortcutNWToSE(p, obj, 21));
        ObjectAction.register(16530, "climb-into", (p, obj) -> UnderwallTunnel.shortcutNWToSE(p, obj, 21));

        // Pipe contortion in Brimhaven Dungeon
        // Eagles' Peak Agility Shortcut
        // Underwall tunnel	Falador Agility Shortcut
        ObjectAction.register(16528, "climb-into", (p, obj) -> UnderwallTunnel.shortcutVertical(p, obj, 26, Direction.SOUTH));
        ObjectAction.register(16527, "climb-into", (p, obj) -> UnderwallTunnel.shortcutVertical(p, obj, 26, Direction.NORTH));
        // Stepping stones in Brimhaven Dungeon
        // Draynor Manor stones to Champions' Guild
        // (Grapple) Scale Catherby cliffside
        // Cairn Isle rock slide climb
        // Ardougne log balance shortcut
        // Pipe contortion in Brimhaven Dungeon
        // Trollweiss/Rellekka Hunter area cliff scramble
        // (Grapple) Escape from the Water Obelisk Island
        // Gnome Stronghold Shortcut

        // Al Kharid mining pit cliffside scramble


        // (Grapple) Scale Yanille wall
        // Yanille Agility dungeon balance ledge
        // Kourend lake isle jump
        // Trollheim easy cliffside scramble
        // Dwarven Mine narrow crevice
        // Draynor narrow tunnel
        // Trollheim medium cliffside scramble
        // Trollheim advanced cliffside scramble
        // Kourend river jump
        // Tirannwn log balance
        // Cosmic Temple - medium narrow walkway
        // Deep Wilderness Dungeon narrow shortcut
        // Trollheim hard cliffside scramble
        // Log balance to Fremennik Province
        // Contortion in Yanille Dungeon small room
        // Arceuus essence mine boulder leap
        // Stepping stone into Morytania near the Nature Grotto
        // Pipe from Edgeville dungeon to Varrock Sewers
        // Arceuus essence mine eastern scramble
        // (Grapple) Karamja Volcano
        // Motherlode Mine wall shortcut
        // Stepping stone by Miscellania docks
        // Monkey bars under Yanille
        // Stepping stones in the Cave Kraken lake
        // Rellekka east fence shortcut
        // Port Phasmatys Ectopool Shortcut
        // Elven Overpass (Arandar) easy cliffside scramble
        // Wilderness from God Wars Dungeon area climb
        // Squeeze through to God Wars Dungeon surface access
        // Estuary crossing on Mos Le'Harmless
        // Slayer Tower medium spiked chain climb
        // Fremennik Slayer Dungeon narrow crevice
        // Taverley Dungeon lesser demon fence
        // Trollheim Wilderness Route
        // Temple on the Salve to Morytania shortcut
        // Cosmic Temple advanced narrow walkway
        // Lumbridge Swamp to Al Kharid narrow crevice
        // Heroes' Guild tunnel
        ObjectAction.register(9739, 2899, 9901, 0, "use", CreviceShortcut.HEROES_GUILD::squeeze);
        ObjectAction.register(9740, 2914, 9895, 0, "use", CreviceShortcut.HEROES_GUILD::squeeze);
        // Yanille Dungeon's rubble climb
        // Elven Overpass (Arandar) medium cliffside scramble
        // Arceuus essence mine northern scramble
        // Taverley Dungeon pipe squeeze to Blue dragon lair
        // (Grapple) Cross cave, south of Dorgesh-Kaan
        // Rope descent to Saradomin's Encampment
        // Slayer Tower advanced spiked chain climb
        // Stronghold Slayer Cave wall-climb
        // Troll Stronghold wall-climb
        // Arceuus essence mine western descent
        // Lava Dragon Isle jump
        // Island crossing near Zul-Andra
        // Shilo Village stepping stones over the river
        // Shilo Village stepping stones over the river
        // Cave crossing south of Dorgesh-Kaan
        // Taverley Dungeon spiked blades jump
        // Fremennik Slayer Dungeon chasm jump
        // Lava Maze northern jump
        // Brimhaven Dungeon eastern stepping stones
        // Elven Overpass (Arandar) advanced cliffside scramble
        // Kalphite Lair wall shortcut
        // Brimhaven Dungeon vine to baby green dragons
        // Dwarven mine crevice
        ObjectAction.register(16543, "Squeeze-through", CreviceShortcut.FALADOR::squeeze);


        /**
         *                                              STEPPING STONES
         * _____________________________________________________________________________________________________________
         */
        // Stepping Stone Lv. 12
        ObjectAction.register(21738, 2649, 9561, 0, "Jump-from", SteppingStone.BRIMHAVEN_STONES12::traverse);
        ObjectAction.register(21739, 2647, 9558, 0, "Jump-from", SteppingStone.BRIMHAVEN_STONES12::traverse);

        // Stepping Stone Lv. 15
        Tile.getObject(31809, 1981, 8996, 1).skipReachCheck = p -> p.equals(1981, 8994, 1) || p.equals(1981, 8998, 1);
        ObjectAction.register(31809, "Jump-to", SteppingStone.CORSAIR_COVE_STONES15::traverse);

        // Stepping Stone Lv. 30
        ObjectAction.register(23645, "Cross", SteppingStone.KARAMJA_STONES30::traverse);
        ObjectAction.register(23647, "Cross", SteppingStone.KARAMJA_STONES30::traverse);

        // Stepping Stone Lv. 31
        ObjectAction.register(16533, "Jump-onto", SteppingStone.DRAYNOR_STONES31::traverse);

        // Stepping Stone Lv. 40 (EAST STONE)
        Tile.getObject(29729, 1612, 3570, 0).skipReachCheck = p -> p.equals(1610, 3570) || p.equals(1614, 3570);
        ObjectAction.register(29729, "Cross", SteppingStone.ZEAH_E_STONES40::traverse);

        // Stepping Stone Lv. 40 (WEST STONE)
        Tile.getObject(29730, 1605, 3571, 0).skipReachCheck = p -> p.equals(1607, 3571) || p.equals(1603, 3571);
        ObjectAction.register(29730, "Cross", SteppingStone.ZEAH_W_STONES40::traverse);

        // Stepping Stone Lv. 45
        Tile.getObject(29728, 1722, 3551, 0).skipReachCheck = p -> p.equals(1720, 3551) || p.equals(1724, 3551);
        ObjectAction.register(29728, "Cross", SteppingStone.ZEAH_STONES45::traverse);

        // Stepping Stone Lv. 50
        Tile.getObject(13504, 3419, 3325, 0).skipReachCheck = p -> p.equals(3417, 3325) || p.equals(3421, 3323);
        ObjectAction.register(13504, "Cross", SteppingStone.MORTMYRE_STONES50::traverse);

        // Stepping Stone Lv. 55
        Tile.getObject(11768, 2573, 3861, 0).skipReachCheck = p -> p.equals(2573, 3859) || p.equals(2575, 3861);
        ObjectAction.register(11768, "Cross", SteppingStone.MISCELLANIA_STONES55::traverse);

        // Stepping Stone Lv. 60
        Tile.getObject(19042, 3711, 2969, 0).skipReachCheck = p -> p.equals(3708, 2969) || p.equals(3715, 2969);
        ObjectAction.register(19042, "Jump-to", SteppingStone.MOS_LEHARMLESS_STONES60::traverse);

        // Stepping Stone Lv. 66
        Tile.getObject(16513, 3214, 3135, 0).skipReachCheck = p -> p.equals(3212, 3137) || p.equals(3214, 3132);
        ObjectAction.register(16513, "Jump-to", SteppingStone.LUMBRIDGE_STONES66::traverse);

        // Stepping Stone Lv. 74
        Tile.getObject(14918, 3201, 3808, 0).skipReachCheck = p -> p.equals(3201, 3810) || p.equals(3201, 3807);
        ObjectAction.register(14918, "Cross", SteppingStone.WILDERNESS_LAVADRAG_STONES74::traverse);

        // Stepping Stone Lv. 76
        Tile.getObject(10663, 2157, 3072, 0).skipReachCheck = p -> p.equals(2160, 3072) || p.equals(2154, 3072);
        ObjectAction.register(10663, "Cross", SteppingStone.ZULRAH_STONES76::traverse);

        // Stepping Stone Lv. 77
        Tile.getObject(16466, 2863, 2974, 0).skipReachCheck = p -> p.equals(2863, 2971) || p.equals(2863, 2976);
        Tile.getObject(16466, 2863, 2974, 0).nearPosition = (p, obj) -> {
            int val = Integer.compare(p.getPosition().distance(Position.of(2863, 2971)), p.getPosition().distance(Position.of(2863, 2976)));
            return val < 0 ? Position.of(2863, 2971) : Position.of(2863, 2976);
        };
        ObjectAction.register(16466, "Cross", SteppingStone.SHILO_STONES77::traverse);

        // Stepping Stone Lv. 82
        Tile.getObject(14917, 3092, 3879, 0).skipReachCheck = p -> p.equals(3091, 3882) || p.equals(3093, 3879);
        ObjectAction.register(14917, "Cross", SteppingStone.WILDERNESS_LAVAMAZE_STONES82::traverse);

        // Stepping Stone Lv. 83 (NORTH STONES)
        Tile.getObject(19040, 2684, 9548, 0).skipReachCheck = p -> p.equals(2682, 9548);
        Tile.getObject(19040, 2688, 9547, 0).skipReachCheck = p -> p.equals(2690, 9547);
        ObjectAction.register(19040, 2684, 9548, 0, "Cross", SteppingStone.BRIMHAVEN_N_STONES83::traverse);
        ObjectAction.register(19040, 2688, 9547, 0, "Cross", SteppingStone.BRIMHAVEN_N_STONES83::traverse);

        // Stepping Stone Lv. 83 (SOUTH STONES)
        Tile.getObject(19040, 2695, 9531, 0).skipReachCheck = p -> p.equals(2695, 9533);
        Tile.getObject(19040, 2696, 9527, 0).skipReachCheck = p -> p.equals(2697, 9525);
        ObjectAction.register(19040, 2695, 9531, 0, "Cross", SteppingStone.BRIMHAVEN_S_STONES83::traverse);
        ObjectAction.register(19040, 2696, 9527, 0, "Cross", SteppingStone.BRIMHAVEN_S_STONES83::traverse);

        /**
         *                                              LOG BALANCE
         * _____________________________________________________________________________________________________________
         */

        // Lob Balance Lv. 1
        ObjectAction.register(23644, "Cross", LogBalance.KARAMJA_LOG1::traverse);

        // Log Balance Lv. 20
        ObjectAction.register(23274, "Walk-across", LogBalance.CAMELOT_LOG20::traverse);

        // Log Balance Lv. 30
        ObjectAction.register(20882, "Walk-across", LogBalance.BRIMHAVEN_LOG30::traverse);
        ObjectAction.register(20884, "Walk-across", LogBalance.BRIMHAVEN_LOG30::traverse);

        // Log Balance Lv. 33
        ObjectAction.register(16548, "Walk-across", LogBalance.ARDY_LOG33::traverse);
        ObjectAction.register(16546, "Walk-across", LogBalance.ARDY_LOG33::traverse);

        // Log Balance Lv. 45
        ObjectAction.register(3931, "Cross", LogBalance.ISAFDAR_1_LOG45::traverse);
        ObjectAction.register(3932, "Cross", LogBalance.ISAFDAR_2_LOG45::traverse);
        ObjectAction.register(3933, "Cross", LogBalance.ISAFDAR_3_LOG45::traverse);

        // Log Balance Lv. 48
        ObjectAction.register(16542, "Walk-across", LogBalance.CAMELOT_LOG48::traverse);
        ObjectAction.register(16540, "Walk-across", LogBalance.CAMELOT_LOG48::traverse);

        /**
         * Rope swings
         */
        // Rope swing to Moss Giant Island
        Tile.getObject(23568, 2705, 3209, 0).walkTo = new Position(2709, 3209, 0);
        ObjectAction.register(23568, 2705, 3209, 0, "swing-on", RopeSwing.MOSS_GIANT_ISLAND_TO::traverse);

        // Rope swing from Moss Giant Island
        Tile.getObject(23569, 2703, 3205, 0).walkTo = new Position(2705, 3205, 0);
        ObjectAction.register(23569, 2703, 3205, 0, "swing-on", RopeSwing.MOSS_GIANT_ISLAND_BACK::traverse);

        // Ogre island
        ObjectAction.register(23570,2511,3090, 0, "Swing-on", RopeSwing.OGRE_ISLAND::traverse);

        /**
         * Balancing Ledges
         */
        // Yanille dungeon
        ObjectAction.register(23548, 2580, 9519, 0, "Walk-across", BalancingLedge.YANILLE_DUNGEON_ENTRANCE_1::traverse);
        ObjectAction.register(23548, 2580, 9513, 0, "Walk-across", BalancingLedge.YANILLE_DUNGEON_ENTRANCE_2::traverse);

        // Weiss
        ObjectAction.register(33190, 2854, 3961, 0, "Cross", (player, obj) -> {
            if (player.getAbsX() > obj.getPosition().getX())
                BalancingLedge.WEISS_DOCK_2.traverse(player, obj);
            else
                BalancingLedge.WEISS_DOCK_1.traverse(player, obj);
        });

        Tile.getObject(31852, 2446, 3158, 0).walkTo =  new Position(2449, 3155, 0);
        ObjectAction.register(31852, 2446, 3158, 0, 1, (p, obj) -> {
            if (Config.OBSERVATORY_ROPE.get(p) == 1) {
                Traveling.fadeTravel(p, 2444, 3165, 0);
                return;
            }
            p.addEvent(e -> {
                Grappling.grapple(p, obj, 24, 24, 28, 4455, -1, 4, Position.of(2449, 3155, 0), Position.of(2444, 3165, 0));
                e.delay(4);
                Config.OBSERVATORY_ROPE.set(p, 1);
                e.delay(3);
                p.animate(-1);
            });
        });
        ObjectAction.register(31849, 2448, 3156, 0, 1, (p, obj) -> Traveling.fadeTravel(p, 2444, 3165, 0));

    }
}
