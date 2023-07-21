package io.ruin.model.activities.combat.godwars.combat.bandos;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/18/2021
 */
public class BandosSpiritualRanger extends NPCCombat {

    private static final Projectile ARROW = new Projectile(9, 45, 30, 40, 50, 5, 15, 16);

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(7);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(7))
            return false;
        npc.graphics(18, 90, 0);
        projectileAttack(ARROW, info.attack_animation, info.attack_style, info.max_damage);
        return true;
    }
}
