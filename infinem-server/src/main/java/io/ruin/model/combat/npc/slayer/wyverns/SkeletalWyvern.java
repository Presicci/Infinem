package io.ruin.model.combat.npc.slayer.wyverns;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class SkeletalWyvern extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(500, 15, 31, 50, 56, 10, 16, 127);
    private static StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

    private static final int WYVERN_SHIELD = 21633;
    private static final int WYVERN_SHIELD_UNCHARGED = 21634;

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (withinDistance(1) && Random.rollDie(3, 2))
            basicAttack();
        else if (Random.rollDie(5, 1))
            iceBreath();
        else if (Random.rollDie(2, 1))
            rangedAttack();
        else
            jumpAttack();
        return true;
    }

    private void jumpAttack() {
        npc.graphics(499);
        basicAttack(2989, AttackStyle.RANGED, info.max_damage);
    }

    private void rangedAttack() {
        npc.animate(2985);
        int delay = PROJECTILE.send(npc, target);
        target.graphics(502, 0, delay);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        target.hit(hit);
    }

    private void iceBreath() {
        npc.animate(2988);
        npc.graphics(502);
        int maxDamage = 60;

        int protection = target.player != null ? target.player.getCombat().getIcyBreathResistance() : 0;
        if (protection >= 1) {
            maxDamage = 10;
        } else if (target.player != null) {
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(9);
            }
            target.player.sendMessage("The wyvern's ice breath drains your stats!");
        }

        // Having a wyvern shield (charged or uncharged) protects you from their freezing.
        if (protection < 2) {
            if (Random.rollDie(3, 1))
                target.freeze(3, npc);
        }

        target.hit(new Hit(npc, null).randDamage(maxDamage).ignoreDefence().ignorePrayer());
    }
}
