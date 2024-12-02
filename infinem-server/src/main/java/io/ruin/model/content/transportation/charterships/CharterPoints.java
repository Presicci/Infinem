package io.ruin.model.content.transportation.charterships;

import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/28/2021
 */
@AllArgsConstructor
public enum CharterPoints {
        BRIMHAVEN(new Position(2760, 3238, 0), 22),
        CATHERBY(new Position(2792, 3414, 0), 9),
        MOS_LEHARMLESS(new Position(3671, 2931, 0), 30),
        MUSA_POINT(new Position(2954, 3158, 0), 15),
        PORT_KHAZARD(new Position(2674, 3144, 0), 24),
        PORT_PHASMATYS(new Position(3702, 3503, 0), 6),
        PORT_SARIM(new Position(3038, 3192, 0), 27),
        SHIP_YARD(new Position(3001, 3032, 0), 13),
        PORT_TYRAS(new Position(2142, 3122, 0), 3),
        CORSAIR_COVE(new Position(2589, 2851, 0), 34),
        PRIF(new Position(2157, 3330, 0), 37),
        CRANDOR(new Position(3853, 3235, 0), 29),
        PORT_PISCARILIUS(new Position(1808, 3679, 0), 39),
        LANDS_END(new Position(1497, 3403, 0), 42),
        ALDARIN(new Position(1455, 2968, 0), 48),
        SUNSET_COAST(new Position(1514, 2971), 51),
        CIVITAS_ILLA_FORTIS(new Position(1743, 3136), 45);

        public final Position pos;
        public final int component;
}
