package io.ruin.model.combat.special.ranged;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

//Armadyl Eye: Deal an attack with double accuracy. (40%)
public class ArmadylCrossbow implements Special {

    private static final Projectile PROJECTILE = new Projectile(301, 38, 36, 41, 51, 5, 5, 11);

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return name.contains("armadyl crossbow");
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        ItemDefinition weaponDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        // In place to allow weapon poison to work
        ItemDefinition ammoDef = player.getEquipment().getDef(Equipment.SLOT_AMMO);

        player.animate(4230);
        player.privateSound(3892, 1, 15);
        int delay = PROJECTILE.send(player, victim);
        victim.hit(new Hit(player, attackStyle, attackType).randDamage(maxDamage).boostAttack(1.0).clientDelay(delay).setRangedAmmo(ammoDef).setAttackWeapon(weaponDef));
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 40;
    }

}