package io.ruin.model.skills.thieving.stealingvaluables;

import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/17/2024
 */
@AllArgsConstructor
public enum BurglingHomeOwner {
    LAVINIA(13312, new Position(1670, 3091), new Position(1668, 3095), new Position(1668, 3094),
            new Position[]{
                    new Position(1668, 3098), new Position(1667, 3099),
                    new Position(1667, 3104), new Position(1666, 3105),
                    new Position(1665, 3106), new Position(1654, 3107),
                    new Position(1653, 3108), new Position(1649, 3108),
                    new Position(1648, 3109), new Position(1648, 3116)
            },
            new Position[]{
                    new Position(1668, 3098), new Position(1667, 3099),
                    new Position(1667, 3106), new Position(1679, 3106)
            },
            new Position[]{
                    new Position(1668, 3098), new Position(1667, 3099),
                    new Position(1667, 3104), new Position(1666, 3105),
                    new Position(1665, 3106), new Position(1654, 3107),
                    new Position(1653, 3108), new Position(1649, 3108),
                    new Position(1648, 3109)
            }
    );

    private final int npcId;
    private final Position housePos, doorClosePos, doorPos;
    private final Position[] bankPath, bazaarPath, strollPath;
}
