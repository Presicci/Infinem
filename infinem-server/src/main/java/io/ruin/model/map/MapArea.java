package io.ruin.model.map;

import io.ruin.model.entity.player.Player;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.actions.impl.Lightables;
import io.ruin.model.map.object.actions.impl.OldFirePit;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
@Getter
public enum MapArea {
    LUMBRIDGE_CASTLE(12850),
    VARROCK(12598, 12597, 12596, 12854, 12853, 12852, 13107, 13109, 13108),
    DRAGON_FORGE(1744, 5277, 1760, 5293, 1),
    WIZARD_TOWER(12337),
    REVENANT_CAVES(12703, 12702, 12701, 12959, 12958, 12957),
    WILDERNESS_SLAYER_CAVE(13470, 13469, 13726, 13725),
    PRIFDDINAS(12894, 12895, 13150, 13151),
    FISHING_GUILD(2596, 3394, 2614, 3426, 0, (player -> {
        player.getTaskManager().doLookupByUUID(575, 1); // Enter the Fishing Guild
    })),
    // Dark caves
    LUMBRIDGE_SWAMP_CAVE(OldFirePit.FirePit.LUMBRIDGE_SWAMP_CAVES_FIRE, 3, 12693, 12949),
    CHASM_OF_TEARS(null, 2, 12948)
    ;

    private OldFirePit.FirePit firePit;
    private final Bounds bounds;

    MapArea(OldFirePit.FirePit firepit, int darknessLevel, int... regionId) {
        this.bounds = Bounds.fromRegions(regionId);
        this.firePit = firepit;
        registerOnEnter(bounds, (player -> {
            player.putTemporaryAttribute(AttributeKey.DARKNESS_LEVEL, darknessLevel);
            if (!Lightables.hasLightSource(player) && (firepit == null || !firepit.isBuilt(player))) {
                player.putTemporaryAttribute(AttributeKey.DARKNESS_TICKS, 0);
                player.openInterface(InterfaceType.SECONDARY_OVERLAY, darknessLevel == 1 ? 97 : darknessLevel == 2 ? 98 : 96);
            } else {
                player.putTemporaryAttribute(AttributeKey.DARKNESS_TICKS, -1);
            }
        }));
        registerOnExit(bounds, ((player, logout) -> {
            player.removeTemporaryAttribute(AttributeKey.DARKNESS_TICKS);
            player.removeTemporaryAttribute(AttributeKey.DARKNESS_LEVEL);
            player.closeInterface(InterfaceType.SECONDARY_OVERLAY);
        }));
    }

    MapArea(int... regionId) {
        this.bounds = Bounds.fromRegions(regionId);
    }

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
    }

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z, MapListener.EnteredAction enteredAction) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
        registerOnEnter(bounds, enteredAction);
    }

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z, MapListener.ExitAction exitAction) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
        registerOnExit(bounds, exitAction);
    }

    private void registerOnEnter(Bounds bounds, MapListener.EnteredAction enteredAction) {
        MapListener.registerBounds(bounds).onEnter(enteredAction);
    }

    private void registerOnExit(Bounds bounds, MapListener.ExitAction exitAction) {
        MapListener.registerBounds(bounds).onExit(exitAction);
    }

    public boolean inArea(Player player) {
        return bounds.inBounds(player);
    }

    public boolean hasFirePitInArea(Player player) {
        if (!inArea(player))
            return false;
        if (firePit == null)
            return false;
        return firePit.isBuilt(player);
    }

    public static MapArea getMapArea(Player player) {
        for (MapArea mapArea : values()) {
            if (mapArea.inArea(player))
                return mapArea;
        }
        return null;
    }
}
