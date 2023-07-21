package io.ruin.model.activities.combat.bosses.bryophyta;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;

import java.util.ArrayList;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/22/2021
 * Last edit on 12/17/2021
 */
public class Bryophyta extends NPCCombat {

    private static final int MAGIC_EMOTE = 7173;
    private static final int GROWTHLING = 8194;

    private static final Projectile PROJECTILE = new Projectile(139, 43, 31, 0, 56, 10, 16, 64);

    private ArrayList<NPC> growthlings;

    // Not in osrs i dont think but just to prevent b2b growthling spawns
    private int growthlingCooldown = 1;

    @Override
    public boolean allowRespawn() {
        return false;
    }

    @Override
    public void init() {
        growthlings = new ArrayList<NPC>();
        npc.setIgnoreMulti(true);
        npc.hitListener = new HitListener().postDefend(hit -> {
            if (growthlings.size() > 0 && hit.type != HitType.POISON && hit.type != HitType.VENOM) {
                hit.damage = 0;
                //if (hit.attacker.player != null) {
                //    hit.attacker.player.sendMessage("<col=" + Color.RED + ">Bryophyta is immune while her growthlings are alive!");
                //}
            }
        });
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if ((growthlings.size() == 0 && Random.rollDie(5, 1) && growthlingCooldown <= 0) || growthlingCooldown <= -5 ) {
            summonGrowthlings();
        } else {
            --growthlingCooldown;
        }
        if (Random.rollDie(10, 5) || !withinDistance(1)) {
            return mageAttack();
        }
        basicAttack();
        return true;
    }

    private boolean mageAttack() {
        npc.animate(MAGIC_EMOTE);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                .randDamage(info.max_damage)
                .clientDelay(delay);
        target.hit(hit);
        target.graphics(140, 60, delay);
        return true;
    }

    private void summonGrowthlings() {
        growthlingCooldown = 4;

        Position pos;
        for (int index = 0; index < 3; index++) {
            pos = Random.get(npc.getPosition().area(5, position ->
                            position.getTile().clipping == 0
                            && position.isWithinDistance(target.getPosition(), 8)
                            && !position.isWithinDistance(target.getPosition(), 2)
                            && npc.getSpawnPosition().getY() - position.getY() <= 8
                            && npc.getSpawnPosition().getX() - position.getX() <= 8));
            NPC growthling = new NPC(GROWTHLING).spawn(pos);
            growthling.getCombat().setTarget(target);
            growthling.face(target);
            growthling.graphics(580, 0, 1);
            growthlings.add(growthling);
            growthling.deathEndListener = (entity, killer, killHit) ->  {
                growthlings.remove(growthling);
                growthling.remove();
            };
        }
    }
}
