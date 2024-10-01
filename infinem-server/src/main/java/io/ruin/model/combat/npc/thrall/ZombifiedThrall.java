package io.ruin.model.combat.npc.thrall;

import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class ZombifiedThrall extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        Player player = World.getPlayer(npc.ownerId, true);
        if (player == null ||
                !player.getCombat().isAttacking(15) ||
                npc.getCombat().getTarget() == null ||
                npc.getCombat().hasAttackDelay() ||
                npc.getCombat().getTarget().isPlayer()) {
            return false;
        }

        target = npc.getCombat().getTarget();
        if (target == null) {
            return false;
        }

        Bounds attackBounds = target.npc.attackBounds;
        if (attackBounds != null && !player.getPosition().inBounds(attackBounds)) {
            return false;
        }

        if (!withinDistance(1))
            return false;

        npc.animate(info.attack_animation);

        Hit hit = new Hit(player, AttackStyle.CRUSH).randDamage(getMaxDamage()).ignoreDefence();
        target.hit(hit);
        return true;
    }

}
