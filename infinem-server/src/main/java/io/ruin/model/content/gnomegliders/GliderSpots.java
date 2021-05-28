package io.ruin.model.content.gnomegliders;

import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
@AllArgsConstructor
public enum GliderSpots {
    GRAND_TREE(new Position(2465, 3501, 3), 4),
    ICE_MOUNTAIN(new Position(2850, 3498), 7),
    DIGSITE(new Position(3321, 3430), 10),
    DESERT(new Position(3284, 3213), 13),
    SHIPYARD(new Position(2971, 2969), 16),
    FELDIP(new Position(2544, 2970), 21),
    APE_ATOLL(new Position(2710, 2801), 25);

    public Position pos;
    public int component;
}
