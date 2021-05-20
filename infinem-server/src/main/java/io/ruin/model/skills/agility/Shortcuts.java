package io.ruin.model.skills.agility;

import io.ruin.model.entity.shared.LockType;
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

        // Lumbridge Stile into sheep farm
        ObjectAction.register(12892, "climb-over", (p, obj) -> Stile.shortcut(p, obj, 1));

        // Falador Agility Shortcut
        ObjectAction.register(24222,2935,3355, 0, "Climb-over", JumpShortcut.FALADOR_JUMP5::traverse);

        // Camelot loose railing
        ObjectAction.register(51, 2662, 3500, 0, "squeeze-through", (p, obj) -> LooseRailing.shortcut(p, obj, 1));

        // (Grapple) Over the River Lum to Al Kharid (FUCK THAT)

        // Rope swing to Moss Giant Island
        ObjectAction.register(23568, 2705, 3209, 0, "swing-on", (p, obj) -> RopeSwing.shortcut(p, obj, 10, Position.of(2709, 3209, 0), Position.of(2704, 3209, 0)));
        ObjectAction.register(23569, 2703, 3205, 0, "swing-on", (p, obj) -> RopeSwing.shortcut(p, obj, 10, Position.of(2705, 3205, 0), Position.of(2709, 3205, 0)));

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
        ObjectAction.register(16519, "climb-under", (p, obj) -> Hole.shortcut(p, obj, 16));
        ObjectAction.register(16520, "climb-into", (p, obj) -> Hole.shortcut(p, obj, 16));
        // Watchtower wall climb
        // Coal Truck log balance

        // Grand Exchange Agility Shortcut
        ObjectAction.register(16529, "climb-into", (p, obj) -> UnderwallTunnel.shortcut(p, obj, 21));
        ObjectAction.register(16530, "climb-into", (p, obj) -> UnderwallTunnel.shortcut(p, obj, 21));

        // Pipe contortion in Brimhaven Dungeon
        // Eagles' Peak Agility Shortcut
        // Underwall tunnel	Falador Agility Shortcut
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
        Tile.getObject(13504, 3419, 3325, 0, 22, -1).skipReachCheck = p -> true;
        ObjectAction.register(13504, "cross", ((player, obj) -> SteppingStone.crossEW(player, obj, 1)));
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
    }
}
