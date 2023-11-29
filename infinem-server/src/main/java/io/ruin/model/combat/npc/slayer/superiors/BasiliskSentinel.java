package io.ruin.model.combat.npc.slayer.superiors;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.shared.BreakableLock;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.StatType;
import io.ruin.utility.TickDelay;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/29/2023
 */
public class BasiliskSentinel extends Superior {

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1735, 25, 25, 40, 60, 0, 16, 96);
    private static final Projectile SPECIAL_PROJECTILE = new Projectile(1737, 25, 25, 40, 150, 0, 16, 96);

    private static final StatType[] DRAIN = { StatType.Attack, StatType.Strength, StatType.Defence, StatType.Ranged, StatType.Magic};

    private final TickDelay specialCooldown = new TickDelay();

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (!specialCooldown.isDelayed() && Random.rollDie(3, 1))
            specialAttack();
        else if (withinDistance(1) && Random.rollDie(2, 1))
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

    private void specialAttack() {
        npc.animate(8500);
        if (target == null)
            return;
        specialCooldown.delay(50);  // 30 seconds
        final Position targetPos = target.getPosition().copy();
        SPECIAL_PROJECTILE.send(npc, targetPos);
        npc.addEvent(event -> {
            event.delay(5);
            if (target != null && target.isPlayer() && target.getPosition().equals(targetPos)) {
                target.player.sendMessage(Color.RED, "The basilisk sentinel encases you in stone.");
                target.breakableLock(10 + 1, true, BreakableLock.BreakableLockType.STUN,
                        "You manage to escape from the stone!",
                        "The stones explode!",
                        "You feel the stone break slightly as you try to move.",
                        (entity, success) -> {
                    entity.animate(8507);
                    entity.graphics(1743);
                    if (!success) entity.graphics(1738, 80, 0);
                });
                target.addEvent(e -> {
                    int ticks = 0;
                    while (ticks < 11) {
                        if (!target.isBreakableStunned()) break;
                        if (ticks++ >= 10) {
                            if (target.isBreakableStunned()) {
                                target.resetBreakableLock(false);
                                Hit hit = new Hit().randDamage(74).ignoreDefence().ignorePrayer();
                                target.hit(hit);
                                break;
                            }
                        } else {
                            target.animate(ticks == 1 ? 8503 : 8504);
                        }
                        e.delay(1);
                    }
                });
            }
        });
    }

    private void drain() {
        if (target.player != null
                && target.player.getEquipment().getId(Equipment.SLOT_SHIELD) != 4156
                && target.player.getEquipment().getId(Equipment.SLOT_SHIELD) != 24265) {
            for (StatType statType : DRAIN) {
                target.player.getStats().get(statType).drain(4);
            }
            target.player.sendMessage("<col=ff0000>The basilisk sentinel's piercing gaze drains your stats!");
            target.player.sendMessage("<col=ff0000>A mirror shield can protect you from this attack.");
        }
    }
}
