package io.ruin.model.combat.npc.slayer.wyverns;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/18/2022
 */
public class SpittingWyvern extends NPCCombat {
    private static final Projectile PROJECTILE = new Projectile(1394, 2, 2, 50, 56, 10, 0, 127);
    private static final int WYVERN_SHIELD = 21633;
    private static final int WYVERN_SHIELD_UNCHARGED = 21634;

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(3, 2))
            meleeAttack();
        else if (Random.rollDie(5, 1))
            iceBreath();
        else if (Random.rollDie(5, 1))
            rangedAttack();
        else if (withinDistance(1))
            meleeAttack();
        else
            rangedAttack();
        return true;
    }

    private void meleeAttack() {
        if (withinDistance(1) && Random.rollDie(3, 1))
            basicAttack();
        else if (withinDistance(1) && Random.rollDie(3, 1))
            crush();
        else if (withinDistance(1))
            slash();
    }

    private void crush() {
        npc.animate(7658);
        target.hit(new Hit(npc, AttackStyle.CRUSH).randDamage(info.max_damage));
    }

    private void slash() {
        npc.animate(7654);
        target.hit(new Hit(npc, AttackStyle.SLASH).randDamage(info.max_damage));
    }

    private void rangedAttack() {
        npc.animate(7657);
        int delay = PROJECTILE.send(npc, target);
        int damage = Random.get(1, 5);
        Hit hit = new Hit(npc, AttackStyle.RANGED).fixedDamage(damage).clientDelay(delay).ignoreDefence();
        if (Random.rollDie(5)) {    // Multihit
            Hit hit1 = new Hit(npc, AttackStyle.RANGED).fixedDamage(damage).clientDelay(delay).ignoreDefence();
            Hit hit2 = new Hit(npc, AttackStyle.RANGED).fixedDamage(damage).clientDelay(delay).ignoreDefence();
            Hit hit3 = new Hit(npc, AttackStyle.RANGED).fixedDamage(damage).clientDelay(delay).ignoreDefence();
            target.hit(hit, hit1, hit2, hit3);
        } else {
            target.hit(hit);
        }
    }

    private void iceBreath() {
        npc.animate(7653);
        int maxDamage = 50;
        npc.graphics(1392); // TODO doesnt come out of mouth

        int protection = target.player != null ? target.player.getCombat().getIcyBreathResistance() : 0;
        if (protection >= 1) {
            target.player.sendMessage("Your shield provides protection from the icy breath!");
            maxDamage = 10;
        } else if (target.player != null) {
            target.player.sendMessage("You have been hit by the icy breath!");
        }

        // Having a wyvern shield (charged or uncharged) protects you from their freezing.
        if (protection < 2) {
            if (Random.rollDie(3, 1))
                target.freeze(3, npc);
            target.graphics(502);
            target.player.sendMessage("You have been frozen!");
        }

        target.hit(new Hit(npc, null).randDamage(maxDamage).ignoreDefence().ignorePrayer());
    }
}
