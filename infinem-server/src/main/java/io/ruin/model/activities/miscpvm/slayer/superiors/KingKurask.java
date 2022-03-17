package io.ruin.model.activities.miscpvm.slayer.superiors;

import io.ruin.model.activities.miscpvm.slayer.LeafVulnerableMonster;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class KingKurask extends LeafVulnerableMonster {

    @Override
    public void init() {
        npc.isSuperior = true;  // Flag for rolling additional loot
        npc.deathEndListener = (DeathListener.SimpleKiller) killer -> {
            npc.remove();
        };
        npc.hitListener = new HitListener().preDefend(super::preDefend);
    }
}
