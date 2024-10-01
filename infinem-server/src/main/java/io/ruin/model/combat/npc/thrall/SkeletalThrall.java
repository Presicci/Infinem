package io.ruin.model.combat.npc.thrall;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.RangedAmmo;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Projectile;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class SkeletalThrall extends NPCCombat {

    private static final Projectile ARROW = new Projectile(1906, 25, 31, 57, 15, 32, 15, 0);

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
        int delay = ARROW.send(npc, target);

        // Create and apply the hit
        Hit hit = new Hit(player, AttackStyle.RANGED).randDamage(getMaxDamage()).ignoreDefence().clientDelay(delay).setAttackWeapon(ItemDefinition.get(Items.BRONZE_CROSSBOW));
        if (target.isNpc() && player.getRelicManager().hasRelic(Relic.DEADEYE) && RangedAmmo.procDeadeyeBoltEffect(target, hit)) {
            return true;
        }
        target.hit(hit);
        return true;
    }
}