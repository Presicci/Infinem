package io.ruin.model.content.transportation.relics;

import io.ruin.api.utils.StringUtils;
import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/30/2024
 */
@Getter
public enum DungeonHubTeleport {
    /**
     * Combat related dungeons
     */
    ASGARNIAN_ICE_DUNGEON(TaskArea.ASGARNIA, new Position(3005, 9548, 0), new Bounds(2983, 9539, 3084, 9599, 0)),
    TAVERLY_DUNGEON(TaskArea.ASGARNIA, new Position(2884, 9801, 0),
            new Bounds(2880, 9664, 2943, 9855, -1),
            new Bounds(2944, 9769, 2970, 9799, -1),
            Bounds.fromRegion(11417),
            Bounds.fromRegion(11161),
            new Bounds(2822, 9782, 2879, 9791, -1),
            new Bounds(2845, 9769, 2879, 9782, -1),
            new Bounds(2854, 9760, 2879, 9769, -1),
            new Bounds(2944, 9807, 2962, 9832, 1)
    ),
    FREMENNIK_SLAYER_DUNGEON(TaskArea.FREMENNIK, new Position(2803, 10000, 0),
            Bounds.fromRegions(10908, 11164), Bounds.fromRegion(10907)),
    WATERBIRTH_ISLAND_DUNGEON(TaskArea.FREMENNIK, new Position(2447, 10147, 0),
            Bounds.fromRegions(9886, 10142),
            Bounds.fromRegions(7492, 7748),
            Bounds.fromRegions(7236, 7492, 7748),
            Bounds.fromRegions(7236, 7492, 7748),
            Bounds.fromRegions(7236, 7492, 7748)),
    BRINE_RAT_CAVERN(TaskArea.FREMENNIK, new Position(2706, 10134, 0),
            Bounds.fromRegion(10910)),
    CATACOMBS_OF_KOUREND(TaskArea.ZEAH, new Position(1666, 10049, 0),
            Bounds.fromRegions(6556, 6557, 6812, 6813)),
    FORTHOS_DUNGEON(TaskArea.ZEAH, new Position(1829, 9972, 0),
            Bounds.fromRegion(7323)),
    KARUULM_SLAYER_DUNGEON(TaskArea.ZEAH, new Position(1311, 10204, 0),
            Bounds.fromRegions(5022, 5023, 5278, 5279, 5280, 5535)),
    CHASM_OF_FIRE(TaskArea.ZEAH, new Position(1435, 10077, 3),
            Bounds.fromRegion(5533)),
    ANCIENT_CAVERN(TaskArea.KANDARIN, new Position(1768, 5366, 1),
            Bounds.fromRegions(6994, 6995)),
    KRAKEN_COVE(TaskArea.KANDARIN, false, new Position(2276, 9992, 0),
            Bounds.fromRegion(9116)),
    SMOKE_DEVIL_DUNGEON(TaskArea.KANDARIN, false, new Position(2404, 9417, 0),
            Bounds.fromRegion(9619)),
    CORSAIR_COVE_DUNGEON(TaskArea.KANDARIN, new Position(2010, 9003, 1),
            Bounds.fromRegions(7564, 7820, 7821, 8076, 8332)),
    WATERFALL_DUNGEON(TaskArea.KANDARIN, new Position(2574, 9866, 0),
            Bounds.fromRegion(10394)),
    STRONGHOLD_SLAYER_CAVE(TaskArea.KANDARIN, new Position(2426, 9824, 0),
            Bounds.fromRegions(9880, 9881),
            Bounds.fromRegion(9624),
            new Bounds(2403, 9792, 2431, 9810, 0),
            new Bounds(2421, 9811, 2431, 9834, 0)
    ),
    BRIMHAVEN_DUNGEON(TaskArea.KARAMJA, new Position(2703, 9563, 0),
            Bounds.fromRegions(10643, 10644, 10645, 10899, 10900, 10901)),
    KALPHITE_LAIR(TaskArea.DESERT, new Position(3489, 9510, 2),
            Bounds.fromRegion(13972, 2)),
    SMOKE_DUNGEON(TaskArea.DESERT, new Position(3211, 9379, 0),
            Bounds.fromRegions(12690, 12946, 13202)),
    SOPHANEM_DUNGEON(TaskArea.DESERT, new Position(2166, 4403, 2),
            Bounds.fromRegion(8516)),
    STRONGHOLD_OF_SECURITY(TaskArea.MISTHALIN, new Position(1859, 5240, 0),
            Bounds.fromRegion(7505),
            Bounds.fromRegion(8017),
            Bounds.fromRegion(8530),
            Bounds.fromRegion(9297)
    ),
    EDGEVILLE_DUNGEON(TaskArea.MISTHALIN, new Position(3097, 9869, 0),
            new Bounds(3088, 9821, 3149, 9917, 0),
            new Bounds(3149, 9868, 3152, 9908, 0)
    ),
    VARROCK_SEWER(TaskArea.MISTHALIN, new Position(3237, 9867, 0),
            new Bounds(3153, 9871, 3199, 9917, 0),
            Bounds.fromRegion(12954),
            new Bounds(3264, 9856, 3281, 9875, 0),
            new Bounds(3264, 9887, 3290, 9919, 0)
    ),
    LUMBRIDGE_SWAMP_CAVE(TaskArea.MISTHALIN, new Position(3168, 9572, 0),
            Bounds.fromRegions(12693, 12949)),
    DORGESH_KAAN_DUNGEON(TaskArea.MISTHALIN, new Position(2714, 5239, 0),
            Bounds.fromRegion(10833)),
    ISLE_OF_SOULS_DUNGEON(TaskArea.MISTHALIN, new Position(2165, 9309, 0),
            Bounds.fromRegion(8593)),
    EXPERIMENT_CAVE(TaskArea.MORYTANIA, new Position(3574, 9929, 0),
            Bounds.fromRegions(13979, 14235)),
    ABANDONED_MINE(TaskArea.MORYTANIA, new Position(3436, 9637, 0),
            Bounds.fromRegion(13718));

    private final TaskArea area;
    private Position destination;
    private Bounds[] bounds;
    private boolean allowMarker;

    DungeonHubTeleport(TaskArea area) {
        this.area = area;
    }

    DungeonHubTeleport(TaskArea area, Position destination, Bounds... bounds) {
        this(area, true, destination, bounds);
    }

    DungeonHubTeleport(TaskArea area, boolean allowMarker, Position destination, Bounds... bounds) {
        this.area = area;
        this.allowMarker = allowMarker;
        this.destination = destination;
        this.bounds = bounds;
    }

    public Position getMarkedTile(Player player) {
        if (!player.hasAttribute(getKey())) return null;
        return DungeonHub.stringToPosition(player.getAttribute(getKey()));
    }

    public void markTile(Player player, String positionString) {
        player.putAttribute(getKey(), positionString);
        player.sendMessage("You can now teleport to this tile when teleporting to " + StringUtils.initialCaps(name().toLowerCase().replace("_", " ")) + ".");
    }

    public String getKey() {
        return "HUB" + name();
    }

    public static DungeonHubTeleport getDungeon(Player player) {
        for (DungeonHubTeleport dungeon : values()) {
            if (dungeon.bounds == null) continue;
            for (Bounds bounds : dungeon.bounds) {
                if (bounds.inBounds(player)) {
                    return dungeon;
                }
            }
        }
        return null;
    }
}
