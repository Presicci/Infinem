package io.ruin.model.activities.miscpvm.slayer.superiors;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.map.Position;
import io.ruin.model.map.route.routes.ProjectileRoute;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/16/2022
 */
public class GreaterAbyssalDemon extends Superior {

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (Random.rollDie(7, 1)) {
            teleportAttack();
            return true;
        } else
            basicAttack();
        return true;
    }

    private void teleportAttack() {
        npc.startEvent(e -> {
            int hits = 0;
            while (hits < 4) {
                int x = Random.get(-1, 1);
                Position destination = target.getPosition().copy().translate(x, x == 0 ? Random.get(-1, 1) : 0, 0);
                if (!destination.equals(target.getPosition()) && ProjectileRoute.allow(target, destination) && !destination.equals(npc.getPosition())) {
                    npc.getMovement().teleport(destination);    // TODO change so he cant tele to small terrain like rocks
                    npc.graphics(409);
                    Hit hit = new Hit(npc, info.attack_style).ignoreDefence();
                    target.hit(hit);
                    ++hits;
                    e.delay(1);
                }
            }
        });
    }
}
