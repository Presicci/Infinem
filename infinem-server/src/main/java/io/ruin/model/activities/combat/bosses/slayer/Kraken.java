package io.ruin.model.activities.combat.bosses.slayer;

import io.ruin.cache.def.NPCDefinition;
import io.ruin.model.combat.npc.slayer.CaveKraken;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.RespawnListener;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Projectile;

import java.util.ArrayList;
import java.util.List;

public class Kraken extends CaveKraken {

    static {
        NPCDefinition def = NPCDefinition.get(496);
        def.attackOption = def.getOption("disturb");
        def.swimClipping = true;
        def = NPCDefinition.get(494);
        def.swimClipping = true;
    }

    private static final Projectile PROJECTILE = new Projectile(156, 60, 31, 25, 56, 10, 15, 128);
    private List<NPC> tentacles = new ArrayList<>(4);

    @Override
    public void init() {
        super.init();
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(-3, 0, 0)));
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(-3, 4, 0)));
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(6, 0, 0)));
        tentacles.add(new NPC(5534).spawn(npc.spawnPosition.copy().translate(6, 4, 0)));
        npc.deathStartListener = (entity, killer, killHit) -> {
          tentacles.forEach(t -> {
              if (!t.getCombat().isDead())
                  t.getCombat().startDeath(killHit);
          });
        };
        RespawnListener superListener = npc.respawnListener;
        npc.respawnListener = n -> {
            superListener.onRespawn(npc);
            tentacles.forEach(t -> t.getCombat().respawn());
        };
        npc.hitListener.preDefend(this::preDefend);
    }

    private void preDefend(Hit hit) {
        hit.ignorePrayer();
        if (npc.getId() == getWhirlpoolId() && tentacles.stream().anyMatch(t -> t.getId() == 5534)) {
            hit.block();
        }
    }

    @Override
    public boolean attack() {
        tentacles.forEach(t -> {
            if (t.getCombat().getTarget() != target) {
                t.getCombat().setTarget(target);
                t.face(target);
            }
        });
        return super.attack();
    }

    @Override
    protected void preTargetDefend(Hit hit, Entity entity) {
        super.preTargetDefend(hit, entity);
        hit.ignorePrayer();
    }
    
    private void awakenTentacles(Player player) {
        tentacles.forEach(tent -> {
            tent.hit(new Hit(player).fixedDamage(1).ignoreDefence().ignorePrayer());
        });
    }

    @Override
    protected int getWhirlpoolId() {
        return 496;
    }

    @Override
    protected int getSurfaceId() {
        return 494;
    }

    @Override
    protected Projectile getProjectile() {
        return PROJECTILE;
    }

    @Override
    protected int getHitGfx() {
        return 157;
    }

    private static final Projectile FISHING_EXPLOSIVE_PROJECTILE = new Projectile(49, 40, 0, 5, 0, 10, 8, 11);
    
    static {
        NPCDefinition.get(496).custom_values.put("ITEM_ON_NPC_SKIP_MOVE_CHECK", 6664);
        ItemNPCAction.register(6664, 496, (player, item, kraken) -> {
            if (!player.getCombat().canAttack(kraken, true)) return;
            player.startEvent(e -> {
                player.lock();
                player.animate(864);    // TODO get a better anim
                player.graphics(50);
                e.delay(1);
                player.getInventory().remove(Items.FISHING_EXPLOSIVE, 1);
                FISHING_EXPLOSIVE_PROJECTILE.send(player, kraken.getCentrePosition());
                e.delay(1);
                Kraken combat = (Kraken) kraken.getCombat();
                combat.awakenTentacles(player);
                e.delay(1);
                kraken.hit(new Hit(player).fixedDamage(1).ignoreDefence().ignorePrayer());
                player.unlock();
            });
        });
    }
}
