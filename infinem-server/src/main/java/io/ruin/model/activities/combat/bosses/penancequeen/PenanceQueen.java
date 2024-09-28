package io.ruin.model.activities.combat.bosses.penancequeen;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.npc.actions.traveling.Traveling;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.ArrayList;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/26/2024
 */
public class PenanceQueen extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(979, 100, 31, 0, 56, 10, 16, 64);

    @Override
    public void init() {
        rangers = new ArrayList<>();
        minionDelay = 10;
    }

    @Override
    public void follow() {
        follow(10);
    }

    private ArrayList<NPC> rangers = new ArrayList<>();
    private int minionDelay = 6;

    @Override
    public boolean attack() {
        if (!withinDistance(10))
            return false;
        if (--minionDelay <= 0 && Random.rollDie(3) && spawnMinions()) {
            return true;
        }
        if (withinDistance(1) && Random.rollDie(2)) {
            basicAttack();
            return true;
        }
        magicAttack();
        return true;
    }

    private void magicAttack() {
        npc.animate(5413);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(20).clientDelay(delay);
        hit.postDamage(t -> {
            t.graphics(869);
        });
        target.hit(hit);

    }

    private boolean spawnMinions() {
        if (!rangers.isEmpty()) return false;
        minionDelay = 7;
        Position pos;
        for (int index = 0; index < 2; index++) {
            pos = Random.get(npc.getPosition().area(5, position ->
                    position.getTile().clipping == 0
                            && position.isWithinDistance(target.getPosition(), 8)
                            && !position.isWithinDistance(target.getPosition(), 2)
                            && npc.getSpawnPosition().getY() - position.getY() <= 8
                            && npc.getSpawnPosition().getX() - position.getX() <= 8));
            NPC ranger = new NPC(5765).spawn(pos);
            ranger.getCombat().setTarget(target);
            ranger.face(target);
            ranger.graphics(580, 0, 1);
            rangers.add(ranger);
            if (target.isPlayer()) ranger.doIfIdle(target.player, () -> {
                rangers.remove(ranger);
                ranger.remove();
            });
            ranger.deathEndListener = (entity, killer, killHit) ->  {
                rangers.remove(ranger);
                ranger.remove();
            };
        }
        return true;
    }
}
