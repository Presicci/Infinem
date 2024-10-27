package io.ruin.model.combat.special.magic;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/26/2024
 */

//Power of Death: Reduce all melee damage you receive by 50% for the next
//minute while the staff remains equipped. Stacks with Protect from Melee. (100%)
public class AccursedSceptre implements Special {

    private static final StatType[] DRAIN_STATS = {StatType.Defence, StatType.Magic};

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return def.id == 27665 || def.id == 27679;
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        ItemDefinition weapon = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if (weapon == null) {
            return true;
        }
        if (DuelRule.NO_MAGIC.isToggled(player)) {
            player.sendMessage("Magic attacks have been disabled for this duel!");
            return true;
        }
        player.animate(9961);
        player.graphics(2338, 60, 0);
        victim.graphics(1466, 60, 96);
        int duration = new Projectile(2339, 45, 23, 50, -4, 10, 10, 70, true).send(player, victim);
        //50% increase to max hit & accuracy
        Hit hit = new Hit(player, AttackStyle.MAGIC, attackType)
                .randDamage(1)
                .randDamage(player.getCombat().getThammaronSceptreMaxDamage(6))
                .clientDelay(duration)
                .setAttackWeapon(weapon);
        hit.boostDamage(0.5);
        hit.boostAttack(0.5);
        if (!hit.isBlocked()) {
            for (StatType stat : DRAIN_STATS) {
                if (victim.player != null) {
                    Stat playerStat = victim.player.getStats().get(stat);
                    double fixedLevel = playerStat.fixedLevel;
                    double currentLevel = playerStat.currentLevel;
                    int targetLevel = (int) (fixedLevel * 0.85); // Target level is 85% of fixed level

                    if (currentLevel > targetLevel) {
                        playerStat.alter(targetLevel);
                        System.out.println(targetLevel);
                    }
                } else if (victim.npc != null) {
                    Stat npcStat = victim.npc.getCombat().getStat(stat);
                    double fixedLevel = npcStat.fixedLevel;
                    double currentLevel = npcStat.currentLevel;
                    int targetLevel = (int) (fixedLevel * 0.85); // Target level is 85% of fixed level

                    if (currentLevel > targetLevel) {
                        npcStat.alter(targetLevel);
                        System.out.println(targetLevel);
                    }
                }
            }
        }
        victim.hit(hit);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}
