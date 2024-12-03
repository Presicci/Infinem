package io.ruin.model.entity.player;

import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.impl.locations.prifddinas.PrifCityEntrance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/24/2023
 */
@AllArgsConstructor
public enum RespawnPoint {
    CIVITAS_ILLA_FORTIS(0, new Bounds(1673, 3137, 1676, 3140, 0)),
    LUMBRIDGE(0, new Bounds(3221, 3217, 3222, 3220, 0)),
    FALADOR(10_000, new Bounds(2969, 3339, 2972, 3341, 0)),
    CAMELOT(25_000, new Bounds(2756, 3476, 2758, 3479, 0)),
    ARDOUGNE(50_000, new Bounds(2659, 3303, 2663, 3308, 0)),
    KOUREND_CASTLE(75_000, new Bounds(1639, 3671, 1642, 3675, 0)),
    FEROX_ENCLAVE(100_000, new Bounds(3150, 3633, 3151, 3636, 0)),
    PRIFDDINAS(250_000, new Bounds(3262, 6064, 3265, 6067, 0)),
    EDGEVILLE(500_000, new Bounds(3093, 3468, 3095, 3471, 0));

    @Getter private final int cost;
    @Getter private final Bounds bounds;

    public Position getRandomPosition() {
        return new Position(bounds.randomX(), bounds.randomY(), bounds.z);
    }

    public boolean canChange(Player player) {
        if (this != PRIFDDINAS) return true;
        return PrifCityEntrance.prifSkillCheckNoMessage(player);
    }
}
