package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/1/2023
 */
@AllArgsConstructor
public enum RockCrab {
    SAND_CRAB(5936, 5935),
    SAND_CRAB2(7207, 7206),
    ROCK_CRAB(101, 100),
    ROCK_CRAB2(103, 102);

    private final int rockId, crabId;

    static {
        for (RockCrab crab : values()) {
            SpawnListener.register(crab.rockId, npc -> {
                npc.putTemporaryAttribute(AttributeKey.CRAB_TRANSFORM, crab.crabId);
                npc.respawnListener = n -> {
                    n.startEvent(e -> {
                        n.lock();
                        e.delay(1);
                        n.unlock();
                    });
                };
                npc.deathEndListener = (entity, killer, killHit) -> npc.transform(crab.rockId);
            });
        }
    }
}
