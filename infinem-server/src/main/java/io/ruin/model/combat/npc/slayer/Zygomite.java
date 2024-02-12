package io.ruin.model.combat.npc.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/11/2024
 */
public class Zygomite extends NPCCombat {

    private static final int[] SPRAYS = { 7421, 7422, 7423, 7424, 7425, 7426, 7427, 7428, 7429, 7430 };

    static {
        for (int i : Arrays.asList(537, 1024)) {
            for (int sprayId : SPRAYS) {
                ItemNPCAction.register(sprayId, i, (player, item, npc) -> spray(player, npc, true));
            }
            ItemNPCAction.register(7431, i, (player, item, npc) -> player.sendMessage("You need to refill the fungicide before using it."));
        }
    }

    private static void spray(Player player, NPC npc, boolean manual) {
        if (npc.getCombat().getTarget() != player) {
            player.sendMessage("That zygomite is not fighting you.");
            return;
        }
        if (manual && npc.getHp() >= 8) {
            player.sendMessage("The zygomite is not weak enough to be sprayed!");
            return;
        }
        player.addEvent(event -> {
            if (!DumbRoute.withinDistance(player, npc, 1)) {
                player.getRouteFinder().routeEntity(npc);
                event.waitForMovement(player);
            }
            Item item = player.getInventory().findFirst(SPRAYS);
            item.setId(item.getId() + 1);
            ((Zygomite) npc.getCombat()).sprayed = true;
            npc.getCombat().startDeath(null);
        });
    }

    private boolean sprayed = false;

    @Override
    public void init() {
        npc.deathEndListener = (entity, killer, killHit) -> {
            sprayed = false;
            npc.transform(getNormalId());
        };
    }

    public int getNormalId() {
        return npc.getTemporaryAttribute("ORIG_ID");
    }

    @Override
    public void startDeath(Hit killHit) {
        if (!sprayed) {
            if (killHit.attacker != null && killHit.attacker.player != null
                    && Config.SHROOM_SPRAYER.get(killHit.attacker.player) == 1
                    && killHit.attacker.player.getInventory().containsAny(false, SPRAYS)) { // autospray
                spray(killHit.attacker.player, npc, false);
            } else
                npc.setHp(1);
            return;
        }
        super.startDeath(killHit);
    }

    @Override
    public void follow() {
        follow(5);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(5))
            return false;
        if (withinDistance(1) && Random.rollDie(2, 1))
            basicAttack();
        else
            magicAttack();
        return true;
    }

    private static final Projectile PROJECTILE = new Projectile(681, 20, 34, 0, 25, 0, 10, 5);

    private void magicAttack() {
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGICAL_RANGED).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
    }
}