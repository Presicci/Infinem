package io.ruin.model.map;

import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
@Getter
public enum MapArea {
    LUMBRIDGE_CASTLE(12850),
    VARROCK(3170, 3376, 3299, 3506, 0),
    DRAGON_FORGE(1744, 5277, 1760, 5293, 1);

    private Bounds bounds;

    MapArea(int regionId) {
        this.bounds = Bounds.fromRegion(regionId);
    };

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
    }
}
