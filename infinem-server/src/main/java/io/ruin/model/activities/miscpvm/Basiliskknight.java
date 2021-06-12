package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;

public class Basiliskknight extends NPCCombat {

    private static final Projectile SPECIAL_PROJECTILE = new Projectile(1735, 25, 0, 40, 85, 0, 16, 96);


    private static final List<Integer> SHIELDS = Arrays.asList(24266, 4156);
    private static StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

    private static final int V_SHIELD = 24266;
    private static final int MIRROR_SHIELD = 4156;
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
            specialAttack();
        else if (Random.rollDie(2, 1))
            meleeAttack();
        return true;
    }


    private void meleeAttack() {
        npc.animate(8499);
        Hit hit = basicAttack();
        }
    private void specialAttack() {
        npc.animate(8500);
        final Position targetPos = target.getPosition().copy();
        int delay = SPECIAL_PROJECTILE.send(npc, targetPos);
        World.sendGraphics(1735, 0, delay, targetPos);
        npc.addEvent(event -> {
            event.delay(2);
            if (target != null && target.getPosition().equals(targetPos)) {
                target.hit(new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(24, 32));
            }
        });
    }
    }

//    private void mageAttack() {
//        npc.animate(8500);
//        npc.graphics(1735);
//        int maxDamage = 45;
//
//        int shieldId = target.player.getEquipment().getId(Equipment.SLOT_SHIELD);
//
//        if (target.player != null && SHIELDS.contains(shieldId)) {
//            maxDamage = 10;
//        } else if (target.player != null) {
//            for (StatType statType : DRAIN) {
//                target.player.getStats().get(statType).drain(9);
//            }
//            target.player.sendMessage("The Basilisk knight's mage attack drains your stats!");
//        }
//
//        if (shieldId != V_SHIELD && shieldId != MIRROR_SHIELD) {
//            if (Random.rollDie(3, 1))
//                target.freeze(3, npc);
//        }
//
//        target.hit(new Hit(npc, null).randDamage(maxDamage).ignoreDefence().ignorePrayer());
//    }
//}