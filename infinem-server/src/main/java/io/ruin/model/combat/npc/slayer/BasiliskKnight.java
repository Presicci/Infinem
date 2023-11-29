package io.ruin.model.combat.npc.slayer;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

public class BasiliskKnight extends NPCCombat {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1735, 25, 25, 40, 60, 0, 16, 96);

    private static final StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

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
        if (withinDistance(1) && Random.rollDie(2, 1))
            basicAttack();
        else
            mageAttack();
        drain();
        return true;
    }

    private void mageAttack() {
        npc.animate(8500);
        int delay = MAGIC_PROJECTILE.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay);
        hit.postDamage(t -> {
            if (hit.damage > 0) {
                t.graphics(1736, 80, 0);
            }
        });
        target.hit(hit);
    }

    private void drain() {
        if (target.player != null
                && target.player.getEquipment().getId(Equipment.SLOT_SHIELD) != 4156
                && target.player.getEquipment().getId(Equipment.SLOT_SHIELD) != 24265) {
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(4);
            }
            target.player.sendMessage("<col=ff0000>The basilisk knight's piercing gaze drains your stats!");
            target.player.sendMessage("<col=ff0000>A mirror shield can protect you from this attack.");
        }
    }
}