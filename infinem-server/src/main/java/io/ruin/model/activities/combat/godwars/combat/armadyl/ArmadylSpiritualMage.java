package io.ruin.model.activities.combat.godwars.combat.armadyl;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Projectile;

public class ArmadylSpiritualMage extends NPCCombat {
    private static final Projectile PROJECTILE = new Projectile(159, 83, 31, 51, 56, 10, 16, 64);

    @Override
    public void init() {
        npc.attackNpcListener = (player, n, message) -> {
            if (player.getCombat().getAttackStyle().isMelee() && player.getCombat().queuedSpell == null && Config.AUTOCAST.get(player) == 0) {
                if (message)
                    player.sendMessage("The aviansie is flying too high for you to hit with melee.");
                return false;
            }
            return true;
        };
    }

    @Override
    public void follow() {
        follow(7);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(7))
            return false;
        npc.animate(info.attack_animation);
        int delay = PROJECTILE.send(npc, target);
        target.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay));
        target.graphics(160, 124, delay);
        return true;
    }
}
