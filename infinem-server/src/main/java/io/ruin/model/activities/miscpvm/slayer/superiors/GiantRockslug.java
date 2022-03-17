package io.ruin.model.activities.miscpvm.slayer.superiors;

import io.ruin.model.activities.miscpvm.slayer.Rockslug;
import io.ruin.model.entity.shared.listeners.DeathListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/17/2022
 */
public class GiantRockslug extends Rockslug {

    @Override
    public void init() {
        npc.isSuperior = true;  // Flag for rolling additional loot
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            npc.remove();
        };
    }
}
