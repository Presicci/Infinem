package io.ruin.model.map;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 8/19/2021
 */
public enum MapArea {
    LUMBRIDGE_CASTLE(12850),
    VARROCK(3170, 3376, 3299, 3506, 0);

    private Bounds bounds;

    MapArea(int regionId) {
        this.bounds = Bounds.fromRegion(regionId);
    };

    MapArea(int southWestX, int southWestY, int northEastX, int northEastY, int z) {
        this.bounds = new Bounds(new Position(southWestX, southWestY), new Position(northEastX, northEastY), z);
    }
}
