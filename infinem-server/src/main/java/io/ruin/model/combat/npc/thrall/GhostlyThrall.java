package io.ruin.model.combat.npc.thrall;

import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class GhostlyThrall extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1907, 27, 31, 15, 15, 32, 15, 0);

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
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

        if (!withinDistance(8))
            return false;

        npc.animate(info.attack_animation);

        // Send magic projectile to the target
        int delay = MAGIC_PROJECTILE.send(npc, target);

        // Create and apply the hit
        Hit hit = new Hit(player, AttackStyle.MAGIC).randDamage(getMaxDamage()).ignoreDefence().clientDelay(delay);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(1908, 124, 0);
                t.privateSound(223);
            } else {
                t.graphics(85, 124, 0);
                hit.hide();
            }
        });
        int dmg = target.hit(hit);
        if (dmg > 0 && player.getRelicManager().hasRelicEnalbed(Relic.ARCHMAGE) && target.isNpc() && target.inMulti()) {
            int entityIndex = npc.getClientIndex();
            int targetIndex = target.getClientIndex();
            int targetCount = 0;
            for(NPC npc : target.localNpcs()) {
                int npcIndex = npc.getClientIndex();
                if(npcIndex == entityIndex || npcIndex == targetIndex)
                    continue;
                if (!npc.getPosition().isWithinDistance(target.getPosition(), 2)) {
                    continue;
                }
                if(npc.getDef().ignoreMultiCheck)
                    continue;
                if(!this.npc.getCombat().canAttack(npc))
                    continue;
                Hit aoeHit = new Hit(player, AttackStyle.MAGIC).randDamage(getMaxDamage() / 2).ignoreDefence().clientDelay(delay);
                npc.hit(aoeHit);
                if(++targetCount >= 5)
                    break;
            }
        }
        return true;
    }
}