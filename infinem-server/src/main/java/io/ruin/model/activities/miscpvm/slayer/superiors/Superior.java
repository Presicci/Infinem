package io.ruin.model.activities.miscpvm.slayer.superiors;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.DeathListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public abstract class Superior extends NPCCombat {

    @Override
    public void init() {
        npc.isSuperior = true;  // Flag for rolling additional loot
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            npc.remove();
        };
    }
}
