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
        // Piscatoris entrance
        // Watchtower wall climb
        // Coal Truck log balance
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
