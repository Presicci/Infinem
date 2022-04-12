package io.ruin.model.map;

import io.ruin.model.entity.player.Player;
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
    ;

    private final Bounds bounds;

    MapArea(int... regionId) {
        this.bounds = Bounds.fromRegions(regionId);
    };

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
    }

    public boolean inArea(Player player) {
        return bounds.inBounds(player);
    }

    public static MapArea getMapArea(Player player) {
        for (MapArea mapArea : values()) {
            if (mapArea.inArea(player))
                return mapArea;
        }
        return null;
    }
}
