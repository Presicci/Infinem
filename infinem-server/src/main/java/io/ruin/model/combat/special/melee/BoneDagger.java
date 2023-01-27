package io.ruin.model.combat.special.melee;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/26/2023
 */
public class BoneDagger implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("bone dagger");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        // In place to allow weapon poison to work
        ItemDef weaponDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);

        player.animate(4198);
        player.graphics(704);
        int damage = target.hit(new Hit(player, attackStyle, attackType)
                .randDamage(maxDamage)
                .boostAttack(1.0)
                .setAttackWeapon(weaponDef));
        if(damage > 0) {
            if(target.player != null) {
                target.player.getStats().get(StatType.Defence).drain(damage);
                target.player.sendMessage("You feel drained!");
            } else {
                target.npc.getCombat().getStat(StatType.Defence).drain(damage);
            }
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 75;
    }
}
