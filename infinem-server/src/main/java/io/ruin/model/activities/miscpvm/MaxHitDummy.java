package io.ruin.model.activities.miscpvm;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.shared.listeners.HitListener;

public class MaxHitDummy extends PassiveCombat {

    public static final int[] dummyIds = { 2668, 7413, 8598, 10507, 10508, 10509, 10510, 10511, 10512, 15025 };

    @Override
    public void init() {
        npc.hitListener = new HitListener()
        .postDefend(h -> {
            if(h.isBlocked())
                h.type = HitType.DAMAGE;
            if(h.type == HitType.DAMAGE)
                h.damage = h.maxDamage;
            if (h.attackStyle.isCannon())
                h.damage = 0;
        });
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        if(attacker.isPlayer()) {
            attacker.player.addEvent(e -> {
                e.delay(2);
                if(attacker.player.nurseSpecialRefillCooldown.isDelayed() && !attacker.player.isAdmin()) {
                    return;
                }
                attacker.player.nurseSpecialRefillCooldown.delaySeconds(60);
                attacker.player.getCombat().restoreSpecial(100);
            });
        }
    }

    @Override
    public void startDeath(Hit killHit) {
        npc.setHp(npc.getMaxHp());
    }

}