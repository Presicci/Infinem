package io.ruin.model.entity.player;

import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/24/2023
 */
@AllArgsConstructor
public enum RespawnPoint {
    LUMBRIDGE(new Bounds(3221, 3217, 3222, 3220, 0)),
    FALADOR(new Bounds(2969, 3339, 2972, 3341, 0)),
    CAMELOT(new Bounds(2756, 3476, 2758, 3479, 0)),
    EDGEVILLE(new Bounds(3093, 3468, 3095, 3471, 0)),
    ARDOUGNE(new Bounds(2659, 3303, 2663, 3308, 0)),
    PRIFDDINAS(new Bounds(3262, 6064, 3265, 6067, 0)),
    FEROX_ENCLAVE(new Bounds(3150, 3633, 3151, 3636, 0)),
    KOUREND_CASTLE(new Bounds(1639, 3671, 1642, 3675, 0));

    private final Bounds bounds;

    public Position getRandomPosition() {
        return new Position(bounds.randomX(), bounds.randomY(), bounds.z);
    }
}
