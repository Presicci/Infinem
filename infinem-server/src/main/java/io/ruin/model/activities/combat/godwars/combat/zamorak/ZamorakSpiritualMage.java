package io.ruin.model.activities.combat.godwars.combat.zamorak;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/18/2021
 */
public class ZamorakSpiritualMage extends NPCCombat {
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
        npc.animate(info.attack_animation);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage));
        target.graphics(78, 0, 0);
        return true;
    }
}
