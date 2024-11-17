package io.ruin.model.content.transportation.quetzel;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/16/2024
 */
@Getter
@AllArgsConstructor
public enum QuetzelDestination {
    CIVITAS_ILLA_FORTIS(0, -1, new Position(1697, 3140)),
    TEOMAT(1, -1, new Position(1437, 3171)),
    SUNSET_COAST(2, -1, new Position(1548, 2995)),
    HUNTER_GUILD(3, -1, new Position(1585, 3053)),
    CAM_TORUM_ENTRANCE(4, 9955, new Position(1446, 3108)),
    COLOSSAL_WYRM_REMAINS(5, 9956, new Position(1670, 2933)),
    OUTER_FORTIS(6, 9957, new Position(1700, 3037)),
    FORTIS_COLOSSEUM(7, 9958, new Position(1779, 3111)),
    ALDARIN(8, -1, new Position(1389, 2901)),
    QUETZACALLI_GORGE(9, -1, new Position(1510, 3222)),
    SALVAGER_OVERLOOK(10, 11379, new Position(1613, 3300));

    private final int childId, varpbit;
    private final Position destination;

    static {
        for (QuetzelDestination dest : values()) {
            if (dest.varpbit != -1) Config.varpbit(dest.varpbit, false).defaultValue(1);
        }
    }
}
