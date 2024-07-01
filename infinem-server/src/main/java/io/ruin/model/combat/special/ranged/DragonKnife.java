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

public class DragonKnife implements Special {

    private static final Projectile PROJECTILE = new Projectile(699, 40, 36, 15, 37, 5, 15, 11);//699 = spec proj  8291 = anim
    private static final Projectile POISON_PROJECTILE = new Projectile(1629, 40, 36, 15, 37, 5, 15, 11);//699 = spec proj  8291 = anim

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return def.id == 22804 || def.id == 22806 || def.id == 22808 || def.id == 22810;
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        // In place to allow weapon poison to work
        ItemDefinition weaponDef = player.getEquipment().getDef(Equipment.SLOT_WEAPON);

        boolean poisoned = weaponDef.id == 22806 || weaponDef.id == 22808 || weaponDef.id == 22810;
        int delay = poisoned ? POISON_PROJECTILE.send(player, victim) : PROJECTILE.send(player, victim);
        player.animate(poisoned ? 8292 : 8291);
        player.publicSound(2528);
        Hit hit = new Hit(player, attackStyle, attackType).randDamage(maxDamage).clientDelay(delay);
        victim.hit(hit, new Hit(player, attackStyle, attackType).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(weaponDef));
        player.getCombat().removeAmmo(player.getEquipment().get(Equipment.SLOT_WEAPON), hit);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 25;
    }
}
