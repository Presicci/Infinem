package io.ruin.model.combat.npc;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.actions.impl.MonkeyGreeGree;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/19/2023
 */
public class MonkeyArcher extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(9, 15, 36, 41, 51, 5, 15, 11);

    @Override
    public void init() {}

    @Override
    public void follow() {

    }

    @Override
    public boolean attack() {
        if (target.isPlayer()) {
            int targetAppearance = target.player.getAppearance().getNpcId();
            if (targetAppearance > -1) {
                for (MonkeyGreeGree greeGree : MonkeyGreeGree.values()) {
                    if (targetAppearance == greeGree.npcId) {
                        return false;
                    }
                }
            }
        }

        if(withinDistance(8)) {
            projectileAttack(PROJECTILE, info.attack_animation, info.attack_style, info.max_damage);
            return true;
        }
        return false;
    }

}