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
        // Ardy stile
        Tile.getObject(993, 2647, 3375, 0).skipReachCheck = p -> p.equals(2649, 3375) || p.equals(2646, 3375);
        Tile.getObject(993, 2647, 3375, 0).nearPosition = (p, obj) -> {
            int val = Integer.compare(p.getPosition().distance(Position.of(2649, 3375)), p.getPosition().distance(Position.of(2646, 3375)));
            return val < 0 ? Position.of(2649, 3375) : Position.of(2646, 3375);
        };
        ObjectAction.register(993,2647,3375, 0, "Climb-over", JumpShortcut.ARDY_JUMP1::traverse);

        // Draynor Stile into cabbage field
        Tile.getObject(7527, 3063, 3282, 0).skipReachCheck = p -> p.equals(3063, 3281) || p.equals(3063, 3284);
        Tile.getObject(7527, 3063, 3282, 0).nearPosition = (p, obj) -> {
            int val = Integer.compare(p.getPosition().distance(Position.of(3063, 3281)), p.getPosition().distance(Position.of(3063, 3284)));
            return val < 0 ? Position.of(3063, 3281) : Position.of(3063, 3284);
        };
        ObjectAction.register(7527,3063,3282, 0, "Climb-over", JumpShortcut.CABBAGE_JUMP1::traverse);

        // lumberyard stile
        ObjectAction.register(2618, "climb-over", (p, obj) -> Stile.shortcutN(p, obj, 1));

        // Ardy stiles
        ObjectAction.register(993, "climb-over", (p, obj) -> Stile.shortcut(p, obj, 1));

        // Lumbridge Stile into sheep farm
        ObjectAction.register(12892, "climb-over", (p, obj) -> Stile.shortcut(p, obj, 1));

        // Falador Agility Shortcut
        ObjectAction.register(24222,2935,3355, 0, "Climb-over", JumpShortcut.FALADOR_JUMP5::traverse);

        // Camelot loose railing
        ObjectAction.register(51, 2662, 3500, 0, "squeeze-through", (p, obj) -> LooseRailing.shortcut(p, obj, 1));

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
        // Kharazi Jungle vine climb
        ObjectAction.register(26886, 2899, 2938, 0, 1, ClimbingSpots.KHARAZI_VINE::traverse);
        ObjectAction.register(26884, 2899, 2941, 0, 1, ClimbingSpots.KHARAZI_VINE::traverse);
        // Cave crossing south of Dorgesh-Kaan
        // Taverley Dungeon spiked blades jump
        // Fremennik Slayer Dungeon chasm jump
        // Lava Maze northern jump
        // Brimhaven Dungeon eastern stepping stones
        // Elven Overpass (Arandar) advanced cliffside scramble
        // Kalphite Lair wall shortcut
        // Brimhaven Dungeon vine to baby green dragons


        ObjectAction.register(34397, 1324, 3778, 0, "climb", ClimbingRocks::climb);
        ObjectAction.register(34397, 1324, 3784, 0, "climb", ClimbingRocks::climb);
        ObjectAction.register(34396, 1324, 3788, 0, "climb",  ClimbingRocks::climb);
        ObjectAction.register(34396, 1324, 3794, 0, "climb",  ClimbingRocks::climb);

        // SW of Trollheim rocks
        ObjectAction.register(3748,2834,3628, 0, "Climb", JumpShortcut.TROLL_E_JUMP1::traverse);
        ObjectAction.register(3748,2833,3628, 0, "Climb", JumpShortcut.TROLL_W_JUMP1::traverse);
        ObjectAction.register(3748,2821,3635, 0, "Climb", JumpShortcut.TROLL_FAR_W_JUMP1::traverse);

        // Lumbridge sheep pen
        Tile.getObject(12982, 3197, 3276, 0).skipReachCheck = p -> p.equals(3197, 3278) || p.equals(3197, 3275);
        Tile.getObject(12982, 3197, 3276, 0).nearPosition = (p, obj) -> {
            int val = Integer.compare(p.getPosition().distance(Position.of(3197, 3278)), p.getPosition().distance(Position.of(3197, 3275)));
            return val < 0 ? Position.of(3197, 3278) : Position.of(3197, 3275);
        };
        ObjectAction.register(12982,3197,3276, 0, "Climb-over", JumpShortcut.SHEEP_JUMP1::traverse);

        // Burgh de Rott low fence
        ObjectAction.register(12776,3474,3221, 0, "Jump-over", JumpShortcut.BURGH_JUMP1::traverse);

        // Corsair cove rocks
        ObjectAction.register(31757,2546,2872, 0, "Climb", JumpShortcut.CORSAIR_JUMP10::traverse);

        // Lumbridge to varrock fence
        ObjectAction.register(16518,3240,3335, 0, "Jump-over", JumpShortcut.VARROCK_JUMP13::traverse);

        // Trollheim
        ObjectAction.register(3748,2910,3687, 0, "Climb", JumpShortcut.TROLL_N_JUMP44::traverse);
        ObjectAction.register(3748,2910,3686, 0, "Climb", JumpShortcut.TROLL_S_JUMP44::traverse);

        // Zeah runecrafting
        ObjectAction.register(27990,1776,3883, 0, "Jump", JumpShortcut.ZEAH_JUMP49::traverse);
        ObjectAction.register(34741,1761,3873, 0, "Jump", JumpShortcut.ZEAH_JUMP69::traverse);

        // Dwarven mine crevice
        ObjectAction.register(16543, "Squeeze-through", CreviceShortcut.FALADOR::squeeze);

        // Death plateau
        ObjectAction.register(3748,2856,3612, 0, "Climb", JumpShortcut.DEATH_PLATEAU_JUMP1::traverse);
        ObjectAction.register(3748,2857,3612, 0, "Climb", JumpShortcut.DEATH_PLATEAU_JUMP2::traverse);

        // Weiss
        ObjectAction.register(33312,2851,3936, 0, "cross", JumpShortcut.WEISS::traverse);

        /**
         *                                              CLIMBING ROCKS
         * _____________________________________________________________________________________________________________
         */
        // Climbing Rocks Lv. 1
        ObjectAction.register(2231, 2792, 2978, 0, "Climb", ClimbingSpots.CAIRN_S_CLIMB1::traverse);
        ObjectAction.register(2231, 2794, 2978, 0, "Climb", ClimbingSpots.CAIRN_S_CLIMB1::traverse);
        ObjectAction.register(2231, 2792, 2979, 0, "Climb", ClimbingSpots.CAIRN_M_CLIMB1::traverse);
        ObjectAction.register(2231, 2794, 2979, 0, "Climb", ClimbingSpots.CAIRN_M_CLIMB1::traverse);
        ObjectAction.register(2231, 2792, 2980, 0, "Climb", ClimbingSpots.CAIRN_N_CLIMB1::traverse);
        ObjectAction.register(2231, 2794, 2980, 0, "Climb", ClimbingSpots.CAIRN_N_CLIMB1::traverse);

        // Climbing Rocks Lv.25
        ObjectAction.register(19849, 2323, 3497, 0, "Climb", ClimbingSpots.EAGLES_TOP_W_CLIMB25::traverse);
        ObjectAction.register(19849, 2324, 3498, 0, "Climb", ClimbingSpots.EAGLES_TOP_E_CLIMB25::traverse);
        ObjectAction.register(19849, 2322, 3501, 0, "Climb", ClimbingSpots.EAGLES_TOP_W_CLIMB25::traverse);

        // Climbing Rocks Lv. 37
        ObjectAction.register(16535, 2489, 3520, 0, "Climb", ClimbingSpots.GNOME_CLIMB37::traverse);
        ObjectAction.register(16534, 2487, 3515, 0, "Climb", ClimbingSpots.GNOME_CLIMB37::traverse);

        // Climbing Rocks Lv. 38
        ObjectAction.register(16550, 3303, 3315, 0, "Climb", ClimbingSpots.AL_KHARID_CLIMB38::traverse);
        ObjectAction.register(16549, 3305, 3315, 0, "Climb", ClimbingSpots.AL_KHARID_CLIMB38::traverse);

        //Climbing Rocks Lv. 41
        ObjectAction.register(16521, 2870, 3671, 0, "Climb", ClimbingSpots.TROLL_CLIMB41::traverse);
        ObjectAction.register(16521, 2871, 3671, 0, "Climb", ClimbingSpots.TROLL_CLIMB41::traverse);

        //Climbing Rocks Lv. 43
        ObjectAction.register(16522, 2878, 3666, 0, "Climb", ClimbingSpots.TROLL_1_CLIMB43::traverse);
        ObjectAction.register(16522, 2878, 3667, 0, "Climb", ClimbingSpots.TROLL_1_CLIMB43::traverse);

        //Climbing Rocks Lv. 43
        ObjectAction.register(3803, 2888, 3661, 0, "Climb", ClimbingSpots.TROLL_3_CLIMB43::traverse);
        ObjectAction.register(3804, 2887, 3661, 0, "Climb", ClimbingSpots.TROLL_2_CLIMB43::traverse);

        //Climbing Rocks Lv. 43
        ObjectAction.register(3803, 2885, 3683, 0, "Climb", ClimbingSpots.TROLL_5_CLIMB43::traverse);
        ObjectAction.register(3804, 2885, 3684, 0, "Climb", ClimbingSpots.TROLL_4_CLIMB43::traverse);

        //Climbing Rocks Lv. 44
        ObjectAction.register(16523, 2908, 3682, 0, "Climb", ClimbingSpots.TROLL_W_CLIMB44::traverse);
        ObjectAction.register(16523, 2909, 3683, 0, "Climb", ClimbingSpots.TROLL_S_CLIMB44::traverse);

        //Climbing Rocks Lv. 47
        ObjectAction.register(16524, 2902, 3680, 0, "Climb", ClimbingSpots.TROLL_CLIMB47::traverse);
        ObjectAction.register(16524, 2901, 3680, 0, "Climb", ClimbingSpots.TROLL_CLIMB47::traverse);

        // Wilderness Rocks Lv. 64
        ObjectAction.register(16545, 2916, 3672, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS1::traverse);
        ObjectAction.register(16545, 2917, 3672, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS1::traverse);
        ObjectAction.register(16545, 2923, 3673, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS2::traverse);
        ObjectAction.register(16545, 2922, 3672, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS2::traverse);
        ObjectAction.register(16545, 2947, 3678, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS3::traverse);
        ObjectAction.register(16545, 2948, 3679, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS3::traverse);
        ObjectAction.register(16545, 2949, 3680, 0, "Climb", ClimbingSpots.TROLL_WILDERNESS4::traverse);

        // Climbing Rocks Lv. 59
        ObjectAction.register(16515, 2344, 3295, 0, "Climb", ClimbingSpots.ARANDAR_CLIMB59::traverse);
        ObjectAction.register(16514, 2346, 3299, 0, "Climb", ClimbingSpots.ARANDAR_CLIMB59::traverse);

        // Climb Rocks Lv. 68
        ObjectAction.register(16515, 2338, 3285, 0, "Climb", ClimbingSpots.ARANDAR_CLIMB68::traverse);
        ObjectAction.register(16514, 2338, 3282, 0, "Climb", ClimbingSpots.ARANDAR_CLIMB68::traverse);

        //Climbing Rocks Lv. 73
        ObjectAction.register(16464, 2843, 3693, 0, "Climb", ClimbingSpots.TROLL_HERB_CLIMB73::traverse);
        ObjectAction.register(16464, 2839, 3693, 0, "Climb", ClimbingSpots.TROLL_HERB_CLIMB73::traverse);

        // Climb Rocks Lv. 85
        ObjectAction.register(16515, 2337, 3253, 0, "Climb", ClimbingSpots.ARANDAR_CLIMB85::traverse);
        ObjectAction.register(16514, 2333, 3252, 0, "Climb", ClimbingSpots.ARANDAR_CLIMB85::traverse);

        // Climb vine Lv. 79
        ObjectAction.register(26884, 2899, 2941, 0, "Climb", ClimbingSpots.KHARAZI_VINE::traverse);
        ObjectAction.register(26886, 2899, 2938, 0, "Climb", ClimbingSpots.KHARAZI_VINE::traverse);

        //Death Plateau Lv. 0
        ObjectAction.register(3791, 2878, 3623, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_EAST1::traverse);
        ObjectAction.register(3790, 2879, 3623, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_EAST1::traverse);
        ObjectAction.register(3791, 2878, 3622, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_EAST2::traverse);
        ObjectAction.register(3790, 2879, 3622, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_EAST2::traverse);

        //Death Plateau Lv. 0
        ObjectAction.register(3791, 2860, 3627, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_WEST1::traverse);
        ObjectAction.register(3790, 2859, 3627, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_WEST1::traverse);
        ObjectAction.register(3791, 2860, 3626, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_WEST2::traverse);
        ObjectAction.register(3790, 2859, 3626, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_WEST2::traverse);

        //Death Plateau Lv. 0
        ObjectAction.register(3722, 2880, 3595, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_NORTH1::traverse);
        ObjectAction.register(3723, 2880, 3594, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_NORTH1::traverse);
        ObjectAction.register(3722, 2881, 3595, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_NORTH2::traverse);
        ObjectAction.register(3723, 2881, 3594, 0, "Climb", ClimbingSpots.DEATH_PLATEAU_NORTH2::traverse);

        //Weiss Lv. 68
        ObjectAction.register(33184, 2852, 3965, 0, "Climb", ClimbingSpots.WEISS_DOCK_1::traverse);
        ObjectAction.register(33185, 2853, 3964, 0, "Climb", ClimbingSpots.WEISS_DOCK_2::traverse);
        ObjectAction.register(33185, 2854, 3964, 0, "Climb", ClimbingSpots.WEISS_DOCK_2::traverse);
        ObjectAction.register(33328, 2854, 3962, 0, 1, ClimbingSpots.WEISS_DOCK_3::traverse);
        ObjectAction.register(33327, 2853, 3962, 0, 1, ClimbingSpots.WEISS_DOCK_3::traverse);
        ObjectAction.register(33191, 2859, 3961, 0, "Climb", ClimbingSpots.WEISS_DOCK_4::traverse);


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

        /**
         * Crawl under
         */
        ObjectAction.register(33192, 2857, 3955, 0, "pass", CrawlUnder.WEISS_DOCK_TREE::traverse);

        /**
         * Monkeybars
         */
        ObjectAction.register(23567, 2597, 9494, 0, "Swing across", MonkeyBars.YANILLE_DUNGEON_BARS_1::traverse);
        ObjectAction.register(23567, 2598, 9494, 0, "Swing across", MonkeyBars.YANILLE_DUNGEON_BARS_1::traverse);
        ObjectAction.register(23567, 2597, 9489, 0, "Swing across", MonkeyBars.YANILLE_DUNGEON_BARS_2::traverse);
        ObjectAction.register(23567, 2598, 9489, 0, "Swing across", MonkeyBars.YANILLE_DUNGEON_BARS_2::traverse);

        /**
         * Pipes
         */
        ObjectAction.register(18416, 2331, 5096, 0, "climb-through", PipeShortcut.WITCHAVEN_DUNGEON_PIPE::traverse);
        Tile.getObject(23140, 2576, 9506, 0).walkTo = new Position(2578, 9506, 0);
        ObjectAction.register(23140, 2576, 9506, 0, "Squeeze-through", PipeShortcut.YANILLE_PIPE::traverse);

        Tile.getObject(23140, 2573, 9506, 0).walkTo =  new Position(2572, 9506, 0);
        ObjectAction.register(23140, 2573, 9506, 0, "Squeeze-through", PipeShortcut.YANILLE_PIPE::traverse);

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
