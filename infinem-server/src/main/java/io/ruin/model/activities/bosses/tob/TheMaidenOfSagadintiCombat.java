package io.ruin.model.activities.bosses.tob;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.raids.tob.dungeon.boss.TheMaidenOfSugadinti;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/21/2021
 */
public class TheMaidenOfSagadintiCombat extends NPCCombat {

    private static Player target;

    private static final int NORMAL_ATTACK = 8092;
    private static final int MULTI_ATTACK = 8091;
    private static final int BLOOD_SPAWN = 8367;

    private static final Projectile BLOOD_PROJ = new Projectile(1578, 50, 25, 0, 100, 0, 50, 0);
    private static final Projectile VORTEX_PROJ = new Projectile(1577, 0, 0, 0, 0, 0, 0, 0);
    private static final int BLOOD_SPLASH = 1576;
    private static final int BLOOD_SPLAT = 1579;

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(32);
    }

    @Override
    public boolean attack() {
        target = getClosestPlayer();
        if (Random.rollDie(15,1)) {
            npc.animate(MULTI_ATTACK);
            //bloodSpawns(npc, target);
        } else {
            npc.animate(NORMAL_ATTACK);
            int delay = VORTEX_PROJ.send(npc, target);
            Hit hit = new Hit(npc, AttackStyle.MAGIC, null).randDamage(34).ignorePrayer().delay(delay);
            target.hit(hit);
        }
        return true;
    }

    /**
     * Handles spawning random blood spawns.
     * @param npc
     * @param target
     */
    private void bloodSpawns(NPC npc, Entity target) {
        /*Position position = target.getPosition();
        npc.addEvent(event -> {
            CopyOnWriteArraySet<BloodSplat> splats = new CopyOnWriteArraySet<>();
            NPC spawn = null;
            if (spawn == null) {
                spawn = new NPC(BLOOD_SPAWN);
                spawn.spawn(position.getX(), position.getY(), 0, Direction.EAST, 5);
                spawn.getCombat().setAllowRespawn(false);
                spawn.walkRange = 5;
                spawn.graphics(BLOOD_SPLASH, 0, 15);
            }
            if (spawn.dead() || npc.dead()) {
                spawn.remove();
                return;
            }
            NPC finalSpawn = spawn;
            boolean exists = splats.stream().anyMatch((splat) -> splat.getPosition().equals(finalSpawn.getPosition()));
            if (target.isPlayer() && !exists) {
                target.asPlayer().getPA().createPlayersStillGfx(BLOOD_SPLAT, spawn.getX(), spawn.getY(), 0, 0);
                splats.add(new BloodSplat(spawn.getLocation()));
            }

            for (BloodSplat splat : splats) {
                if (splat.age <= 8 && splat.getLocation().equals((target.getLocation()))) {
                    target.appendDamage(Misc.random(10, 25), Hitmark.HIT);
                } else {
                    splats.remove(splat);
                }
                splat.age++;
            }
        });
        handleDodgableAttack(npc, target, CombatType.SPECIAL, BLOOD_PROJ, new Graphic(1576), new Hit(Hitmark.HIT, Misc.random(5, 25), 3, true), event);*/
    }

    /**
     * Gets the closest player to the npc.
     * @return
     */
    private Player getClosestPlayer() {
        asMaiden().getTargets().forEach(player -> {
            if (!player.getPosition().isWithinDistance(npc.getPosition(), 32) || player.dead()) {
                return;
            }
            if (target == null || (target.getPosition().distance(npc.getPosition()) > player.getPosition().distance(npc.getPosition()))) {
                target = player;
            }
        });
        return target;
    }

    private TheMaidenOfSugadinti asMaiden() {
        return (TheMaidenOfSugadinti) npc;
    }

    @RequiredArgsConstructor @Getter
    protected class BloodSplat {

        private final Position position;
        private int age;

    }
}
