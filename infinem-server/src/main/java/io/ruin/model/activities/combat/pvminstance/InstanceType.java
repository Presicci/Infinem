package io.ruin.model.activities.combat.pvminstance;

import io.ruin.Server;
import io.ruin.model.activities.combat.godwars.GodwarsBossEntrance;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import lombok.Getter;

import java.util.*;

@Getter
public enum InstanceType {
    GIANT_MOLE("Giant Mole", new Bounds(1728, 5120, 1791, 5247, -1), 300_000, 60, new Position(1752, 5236, 0), new Position(2985, 3316, 0)),
    DAGANNOTH_KINGS("Dagannoth Kings", 11589, 400_000, 60, new Position(2899, 4449, 0), new Position(1912, 4367, 0)),
    KRAKEN("Kraken", 9116, 400_000, 60, new Position(2280, 10022, 0), new Position(2280, 10016, 0)),
    KBD("King Black Dragon", 9033, 500_000, 60, new Position(2271, 4680, 0),  new Position(3067, 10253, 0)),
    THERMONUCLEAR_SMOKE_DEVIL("Thermonuclear Smoke Devil", new Bounds(2344, 9432, 2376, 9456, 0), 400_000, 60, new Position(2376, 9452, 0), new Position(2379, 9452, 0)),
    CERBERUS("Cerberus", 5140, 600_000, 60, new Position(1304, 1290, 0), new Position(1310, 1274, 0)),
    CORP("Corporeal Beast", 11844, 1_000_000, 60, new Position(2974, 4384, 2), new Position(2970, 4384, 2)),
    KALPHITE_QUEEN("Kalphite Queen", 13972, 500_000, 60, new Position(3507, 9494, 0), new Position(3509, 9496, 2)),
    SARACHNIS("Sarachnis", 7322, 50_000, 60, new Position(1842, 9911, 0), new Position(1842, 9912, 0)),

    BOUNCER_GHOST("Bouncer", new Bounds(1752, 4696, 1775, 4719, 0), 0, 60, new Position(1759, 4711, 0), new Position(2617, 9828, 0)),
    OBOR("Obor", new Bounds(3072, 9792, 3107, 9817, 0), 0, 60, new Position(3091, 9815, 0), new Position(3095, 9832, 0)),
    BRYOPHYTA("Bryophyta", new Bounds(3200, 9920, 3235, 9951, -1), 0, 60, new Position(3214, 9937, 0), new Position(3174, 9900, 0)),

    HESPORI("Hespori", new Bounds(1232, 10072, 1264, 10104, -1), 0, 60, new Position(1243, 10081), new Position(1230, 3729)),

    BANDOS_GWD("General Graardor", new Bounds(2856, 5344, 2877, 5374, 2), 500_000, 60, Config.GWD_BANDOS_KC, "Bandos", new Position(2864, 5354, 2), new Position(2862, 5354, 2)),
    ZAMORAK_GWD("K'ril Tsutsaroth", new Bounds(2912, 5312, 2944, 5336, 2), 500_000, 60, Config.GWD_ZAMORAK_KC, "Zamorak", new Position(2925, 5331, 2), new Position(2925, 5333, 2)),
    ARMADYL_GWD("Kree'arra", new Bounds(2816, 5288, 2840, 5304, 2), 500_000, 60, Config.GWD_ARMADYL_KC, "Armadyl", new Position(2839, 5296, 2), new Position(2839, 5294, 2)),
    SARADOMIN_GWD("Commander Zilyana", new Bounds(2880, 5248, 2911, 5278, -1), 500_000, 60, Config.GWD_SARADOMIN_KC, "Saradomin", new Position(2907, 5265, 0), new Position(2909, 5265, 0));

    /**
     * Name of type
     */
    private final String name;

    /**
     * The position the player will be teleported to upon leaving
     */
    private final Position exitPosition;

    /**
     * Entry position
     */
    private final Position entryPosition;

    /**
     * How many coins it costs to create this instance (only used if world is set to Eco)
     */
    private final int coinCost;


    /**
     * Instance duration (in ticks)
     */
    private final int duration;

    /**
     * Bounds. Always use whole chunks to make sure coordinate conversion works properly
     */
    private final Bounds bounds;

    @Getter private Config godwarsConfig = null;

    @Getter private String godwarsGodName = "";

    InstanceType(String name, Bounds bounds, int coinCost, int duration, Position entryPosition, Position exitPosition) {
        this(name, bounds, coinCost, duration, null, "", entryPosition, exitPosition);
    }

    InstanceType(String name, int regionId, int coinCost, int duration, Position entryPosition, Position exitPosition) {
        this(name, Bounds.fromRegion(regionId), coinCost, duration, entryPosition, exitPosition);
    }

    InstanceType(String name, Bounds bounds, int coinCost, int duration, Config godwarsConfig, String godwarsGodName, Position entryPosition, Position exitPosition) {
        this.name = name;
        this.bounds = bounds;
        this.coinCost = coinCost;
        this.duration = duration * 100;
        this.entryPosition = entryPosition;
        this.exitPosition = exitPosition;
        this.godwarsConfig = godwarsConfig;
        this.godwarsGodName = godwarsGodName;
        loadSpawns();
    }

    public static final Set<InstanceType> timelessInstances = EnumSet.of(OBOR, BRYOPHYTA, HESPORI, BOUNCER_GHOST);

    private void loadSpawns() {
        int baseX = bounds.swX;
        int baseY = bounds.swY;
        if (baseX % 8 != 0 || baseY % 8 != 0) {
            Server.logWarning("InstanceType " + name + " has imprecise coordinates!");
        }
        spawns = new LinkedList<>();
        SpawnListener.forEach(npc -> {
            if (npc == null) return;
            if (!npc.defaultSpawn)
                return;
            if (npc.spawnPosition.inBounds(bounds)) {
                spawns.add(new Spawn(npc.getId(), npc.spawnPosition.getX() - baseX, npc.spawnPosition.getY() -  baseY, npc.getSpawnPosition().getZ(), npc.spawnDirection, npc.walkRange));
            }
        });
    }

    public void enterPublic(Player player) {
        if (godwarsConfig != null && !GodwarsBossEntrance.enter(player, godwarsConfig, godwarsGodName)) {
            return;
        }
        player.getMovement().teleport(getEntryPosition());
    }

    public int getCost() {
        return coinCost;
    }

    /**
     * Spawns (In Local coords)
     */
    private List<Spawn> spawns;

    static class Spawn {
        int id;
        int x;
        int y;
        int z;
        Direction direction;
        int walkRange;


        Spawn(int id, int x, int y, int z, Direction direction, int walkRange) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
            this.direction = direction;
            this.walkRange = walkRange;
        }
    }
}

