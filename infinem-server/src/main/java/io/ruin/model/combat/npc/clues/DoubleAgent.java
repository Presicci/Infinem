package io.ruin.model.combat.npc.clues;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.npc.NPCCombat;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/31/2024
 */
public class DoubleAgent extends NPCCombat {
    @Override
    public void init() {
        npc.forceText(Random.get(Arrays.asList("I expect you to die!")));
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }
}
