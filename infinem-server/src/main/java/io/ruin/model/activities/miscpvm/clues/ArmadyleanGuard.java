package io.ruin.model.activities.miscpvm.clues;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/24/2022
 */
public class ArmadyleanGuard extends NPCCombat {

    private static final Projectile AXE = new Projectile(1193, 80, 30, 40, 50, 5, 15, 16);

    @Override
    public void init() {
        npc.forceText("No warning! Begone!");
        npc.attackNpcListener = (player, n, message) -> {
            if (player.getCombat().getAttackStyle().isMelee()) {
                if (message)
                    player.sendMessage("The guard is flying too high for you to hit with melee.");
                return false;
            }
            return true;
        };
    }


    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        projectileAttack(AXE, info.attack_animation, info.attack_style, info.max_damage);
        return true;
    }
}