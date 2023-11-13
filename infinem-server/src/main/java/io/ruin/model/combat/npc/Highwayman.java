package io.ruin.model.combat.npc;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/13/2023
 */
public class Highwayman extends NPCCombat {
    private Entity prevTarget = null;

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if(withinDistance(1)) {
            if (prevTarget != target) {
                prevTarget = target;
                npc.forceText("Stand and deliver!");
            }
            basicAttack(info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }
}
