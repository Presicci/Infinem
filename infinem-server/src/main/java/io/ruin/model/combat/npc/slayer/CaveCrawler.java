package io.ruin.model.combat.npc.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;

public class CaveCrawler extends NPCCombat {


    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    private boolean heal = false;

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        if (heal) npc.incrementHp(1);
        heal = !heal;
        if (Random.rollDie(4, 1))
            target.poison(3);
        return true;
    }
}
