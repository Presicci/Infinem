package io.ruin.model.combat.npc;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2024
 */
public class RangedTroll extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(304, 30, 26, 30, 51, 0, 10, 5);

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if (withinDistance(6)) {
            projectileAttack(PROJECTILE, info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }
}
