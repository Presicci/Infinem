package io.ruin.model.activities.combat.bosses.hespori;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/29/2022
 */
public class HesporiFlower extends NPCCombat {

    @Override
    public void init() {
        npc.animate(8229);
        setAllowRespawn(false);
        npc.setIgnoreMulti(true);
        npc.hitListener = new HitListener().postDefend(hit -> {
            hit.denyExperience();
            hit.damage = 10;
            if (hit.attacker.player != null)
                hit.attacker.player.getCombat().reset();
        });
    }

    @Override
    public void startDeath(Hit killHit) {
        npc.startEvent(e -> {
            npc.animate(8228);
            e.delay(1);
            npc.transform(8585);
        });
    }

    @Override
    public void follow() {}

    @Override
    public boolean attack() {
        return false;
    }
}
