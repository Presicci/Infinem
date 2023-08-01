package io.ruin.model.combat.npc.werewolves;

import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/25/2023
 */
public abstract class Werewolf extends NPCCombat {

    private int originalId;

    protected abstract int getWerewolfId();

    @Override
    public void init() {
        originalId = npc.getId();
        npc.hitListener = new HitListener().preDefend(((hit) -> {
            if (hit.attacker == null || hit.attacker.player == null)
                return;
            if (hit.attackWeapon == null || hit.attackWeapon.id != Items.WOLFBANE) {
                if (npc.getId() != getWerewolfId()) {
                    int hp = npc.getHp() + 40;
                    //npc.animate();    // Pre werewolf
                    npc.transform(getWerewolfId());
                    npc.setHp(hp);
                    npc.animate(6543);  // Post werewolf
                    npc.doIfIdle(hit.attacker.player, () -> {
                        npc.transform(originalId);
                    });
                    npc.deathEndListener = (entity, killer, killHit) -> npc.transform(originalId);
                }
            }
        }));
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }
}
