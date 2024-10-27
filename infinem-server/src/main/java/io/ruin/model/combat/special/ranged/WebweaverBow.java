package io.ruin.model.combat.special.ranged;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/26/2024
 */
public class WebweaverBow implements Special {

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return name.contains("webweaver bow");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        ItemDefinition weaponDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        player.animate(9964);
        player.graphics(2354);
        target.graphics(2355, 0, 60);
        for (int i = 0; i < 4; i++) {
            Hit hit = new Hit(player, attackStyle, attackType)
                    .randDamage((int) (maxDamage * 0.4))
                    .boostAttack(1.0)
                    .clientDelay(60 + 10 * i)
                    .setAttackWeapon(weaponDef);
            if (i > 0) hit.keepCharges();
            target.hit(hit);
            if (Random.rollPercent(5))
                target.poison(4);
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}