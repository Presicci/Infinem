package io.ruin.model.combat.npc.basic;

import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/5/2023
 */
public class BasicThreePoisonCombat extends NPCCombat {

    @Override
    public void init() {
        setPoison(3);
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            basicAttack();
            return true;
        }
        return false;
    }

}