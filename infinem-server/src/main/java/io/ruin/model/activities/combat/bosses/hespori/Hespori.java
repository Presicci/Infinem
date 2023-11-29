package io.ruin.model.activities.combat.bosses.hespori;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.prayer.Prayer;

import java.util.ArrayList;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/29/2022
 */
public class Hespori extends NPCCombat {

    private static final Projectile MAGIC_PROJ = new Projectile(1640, 100, 31, 51, 56, 10, 4, 64);
    private static final Projectile RANGED_PROJ = new Projectile(1639, 60, 0, 0, 36, 10, 2, 64);
    private static final Projectile ENTANGLE_PROJ = new Projectile(1642, 100, 31, 51, 56, 10, 8, 64);

    private int flowerCount = 0;
    private static final int FLOWER = 8584;
    private static final int[][] FLOWER_OFFSETS = {
            { -3, -3 }, // SW corner
            { -3, 5 },  // NW corner
            { 5, -3 },  // SE corner
            { 5, 5 }    // NE corner
    };

    private final ArrayList<NPC> flowers = new ArrayList<>();

    @Override
    public void init() {
        flowerCount = 0;
        setAllowRespawn(false);
        npc.setIgnoreMulti(true);
        spawnFlowers();
        npc.hitListener = new HitListener().postDefend(hit -> {
            for (NPC flower : flowers) {
                if (flower.getId() == FLOWER) {
                    hit.damage = 0;
                    break;
                }
            }
            if (flowerCount == 0 && npc.getHp() <= 200) {
                System.out.println("flower" + flowerCount);
                openFlowers();
            } else if (flowerCount == 1 && npc.getHp() <= 100) {
                System.out.println("flower" + flowerCount);
                openFlowers();
            }
        });
        npc.deathStartListener = ((entity, killer, killHit) -> {
            for (NPC flower : flowers) {
                if (flower != null) {
                    flower.remove();
                }
            }
        });
    }

    @Override
    public void reset() {
        npc.startEvent(e -> {
            //e.delay(5);
            if (flowers.size() > 0) {
                for (NPC flower : flowers) {
                    if (flower != null) {
                        flower.remove();
                    }
                }
            }
            npc.remove();
        });
    }

    private void spawnFlowers() {
        Position pos;
        for (int[] xyOffset : FLOWER_OFFSETS)  {
            pos = npc.getPosition().copy();
            pos.translate(xyOffset[0], xyOffset[1]);
            NPC flower = new NPC(FLOWER).spawn(pos);
            flowers.add(flower);
        }
    }

    private void openFlowers() {
        for (NPC flower : flowers) {
            flower.transform(FLOWER);
            flower.animate(8229);
            flower.setHp(10);
        }
        ++flowerCount;
    }

    // Override so that items dont drop
    @Override
    public void dropItems(Killer killer) {}

    @Override
    public void follow() {}

    private int cooldown = 0;

    @Override
    public boolean attack() {
        if (Random.rollDie(7, 1) && cooldown >= 0) {
            entangle();
            cooldown = 2;
            return true;
        } else if (Random.rollDie(2)) {
            rangeAttack();
            --cooldown;
            return true;
        } else {
            mageAttack();
            --cooldown;
            return true;
        }
    }

    private void mageAttack() {
        npc.startEvent(e -> {
            npc.animate(8223);
            e.delay(1);
            if (target == null)
                return;
            int delay = MAGIC_PROJ.send(npc, target);
            Hit hit = new Hit().randDamage(14).clientDelay(delay);
            hit.postDamage(t -> {
                if (hit.damage > 0) {
                    t.graphics(1641, 124, 0);
                    t.privateSound(223);
                } else {
                    t.graphics(85, 124, 0);
                    hit.hide();
                }
            });
            target.hit(hit);
        });
    }

    private void rangeAttack() {
        npc.startEvent(e -> {
            npc.animate(8224);
            e.delay(1);
            int maxHit = (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) ? 2 : 8;
            if (target == null)
                return;
            int delay = RANGED_PROJ.send(npc, target);
            Hit hit1 = new Hit().randDamage(maxHit).clientDelay(delay).ignorePrayer();
            target.hit(hit1);
            e.delay(1);
            int delay2 = RANGED_PROJ.send(npc, target);
            Hit hit2 = new Hit().randDamage(maxHit).clientDelay(delay2).ignorePrayer();
            target.hit(hit2);
        });
    }

    private void entangle() {
        npc.startEvent(e -> {
            int rootTicks = 10;
            npc.animate(8223);
            if (target == null)
                return;
            ENTANGLE_PROJ.send(npc, target);
            e.delay(1);
            target.graphics(1643);
            target.breakableRoot(rootTicks + 1, true, "You manage to break free of the vines!", "The vines explode!", "You feel the vines loosen slightly as you try to move.");
            if (target != null && target.player != null) {
                target.player.sendMessage(Color.RED, "The Hespori entangles you in some vines!");
            }
            World.startEvent(we -> {
                we.delay(rootTicks);
                if (target.isBreakableRooted()) {
                    target.resetBreakableRoot(false);
                    Hit hit = new Hit().randDamage(40).ignoreDefence().ignorePrayer();
                    target.hit(hit);
                }
            });
        });
    }
    // emote 8223 magic 8224 ranged
}
