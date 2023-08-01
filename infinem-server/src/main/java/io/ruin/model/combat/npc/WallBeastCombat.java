package io.ruin.model.combat.npc;

import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/1/2023
 */
public class WallBeastCombat extends NPCCombat {
    @Override
    public void init() {
        npc.deathEndListener = (entity, killer, killHit) -> npc.transform(475);
    }

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (!withinDistance(2))
            return false;
        basicAttack();
        return true;

    }
}
