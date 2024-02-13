package io.ruin.model.combat.npc.slayer;

import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.route.routes.DumbRoute;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/12/2024
 */
public class DesertLizard extends NPCCombat {

    static {
        for (int i : Arrays.asList(458, 459, 460, 461, 462, 463, 12003)) {
            ItemNPCAction.register(Items.ICE_COOLER, i, (player, item, npc) -> cool(player, npc, true));
        }
    }

    private static void cool(Player player, NPC npc, boolean manual) {
        Entity target = npc.getCombat().getTarget();
        if (target != null && target != player) {
            player.sendMessage("That lizard is not fighting you.");
            return;
        }
        if (manual && npc.getHp() >= 5) {
            player.sendMessage("The lizard is not weak enough to be cooled!");
            return;
        }
        player.addEvent(event -> {
            if (!DumbRoute.withinDistance(player, npc, 1)) {
                player.getRouteFinder().routeEntity(npc);
                event.waitForMovement(player);
            }
            player.getInventory().remove(Items.ICE_COOLER, 1);
            ((DesertLizard) npc.getCombat()).cooled = true;
            npc.getCombat().startDeath(null);
        });
    }

    private boolean cooled = false;

    @Override
    public void init() {
        npc.deathEndListener = (entity, killer, killHit) -> {
            cooled = false;
        };
    }

    @Override
    public void startDeath(Hit killHit) {
        if (!cooled) {
            if (killHit.attacker != null && killHit.attacker.player != null
                    && Config.REPTILE_FREEZER.get(killHit.attacker.player) == 1
                    && killHit.attacker.player.getInventory().hasId(Items.ICE_COOLER)) { // Reptile freezer unlock
                cool(killHit.attacker.player, npc, false);
            } else
                npc.setHp(1);
            return;
        }
        super.startDeath(killHit);
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