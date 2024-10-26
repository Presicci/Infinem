package io.ruin.model.activities.wilderness.bosses.venenatis;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/24/2024
 */
public class SpiderlingCombat extends NPCCombat {

    private final Spiderling spiderling;

    public SpiderlingCombat(Spiderling spiderling) {
        this.spiderling = spiderling;
    }

    @Override
    public void init() {
        npc.hitListener = new HitListener().preDefend(this::preDefend);
    }

    public void preDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.player != null) {
            hit.minDamage = 5;
            hit.ignoreDefence();
        }
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (spiderling.venenatis.isDead()) {
            npc.remove();
        }
        basicAttack().onLand(hit -> {
            if (target.player != null) {
                target.player.getPrayer().drain(1);
                target.player.sendFilteredMessage("You feel yourself drained by the spiderling");
            }
        });
        return true;
    }

    @Override
    public void startDeath(Hit killHit) {
        spiderling.venenatis.spiderlingsAlive--;
        super.startDeath(killHit);
    }
}
