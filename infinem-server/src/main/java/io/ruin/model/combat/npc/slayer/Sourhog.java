package io.ruin.model.combat.npc.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.skills.slayer.Slayer;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/2/2023
 */
public class Sourhog extends NPCCombat {

    private static final Projectile PROJECTILE = new Projectile(1817, 37, 38, 5, 30, 15, 16, 0);
    private static final StatType[] DRAIN = { StatType.Attack, StatType.Defence };

    @Override
    public void init() {}

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(4, 1)) {
            ranged();
            return true;
        }
        if (!withinDistance(1))
            return false;
        basicAttack();
        return true;
    }

    private void ranged() {
        npc.animate(8770);
        int delay = PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.RANGED).randDamage(info.max_damage).clientDelay(delay);
        if (target.player != null && target.player.getEquipment().getId(Equipment.SLOT_HAT) != 24942 && !Slayer.hasSlayerHelmEquipped(target.player)) {
            hit.randDamage(20, 30).ignoreDefence().ignorePrayer();
            hit.postDamage(t -> {
                for (StatType statType : DRAIN) {
                    target.player.getStats().get(statType).drain(90.0);
                }
                t.graphics(1818, 80, 0);
                target.player.forceText("Argh! My eyes!");
            });
        } else {
            hit.postDamage(t -> {
                t.graphics(1818, 0, 0);
            });
        }
        target.hit(hit);
    }
}
