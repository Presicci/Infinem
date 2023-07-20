package io.ruin.model.content.transportation.charterships;

import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
@AllArgsConstructor
public enum CharterPoints {
        BRIMHAVEN(new Position(2760, 3238, 0), 17),
        CATHERBY(new Position(2792, 3414, 0), 8),
        MOS_LEHARMLESS(new Position(3671, 2931, 0), 26),
        MUSA_POINT(new Position(2954, 3158, 0), 14),
        PORT_KHAZARD(new Position(2674, 3144, 0), 20),
        PORT_PHASMATYS(new Position(3702, 3503, 0), 5),
        PORT_SARIM(new Position(3038, 3192, 0), 23),
        SHIP_YARD(new Position(3001, 3032, 0), 11),
        PORT_TYRAS(new Position(2142, 3122, 0), 2),
        CORSAIR_COVE(new Position(2589, 2851, 0), 32),
        PRIF(new Position(2157, 3330, 0), 33),
        CRANDOR(new Position(3853, 3235, 0), 29);

        public final Position pos;
        public final int component;
}
