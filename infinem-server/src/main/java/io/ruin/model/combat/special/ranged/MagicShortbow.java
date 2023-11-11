package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Projectile;

//Snapshot: Fire two arrows within quick
//succession, but with reduced accuracy. (55%)
public class MagicShortbow implements Special {

    private static final Projectile FIRST = new Projectile(249, 40, 36, 20, 33, 3, 15, 11);
    private static final Projectile SECOND = new Projectile(249, 40, 36, 50, 53, 3, 15, 11);

    @Override
    public boolean accept(ItemDef def, String name) {
        return def.id == 861;
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle style, AttackType type, int maxDamage) {
        // In place to allow weapon poison to work
        ItemDef ammoDef = player.getEquipment().getDef(Equipment.SLOT_AMMO);

        Item ammo = player.getEquipment().get(Equipment.SLOT_AMMO);
        if(ammo == null || ammo.getAmount() < 2) {
            player.sendMessage("You need at least two arrows in your quiver to use this special attack.");
            return false;
        }
        player.animate(1074);
        player.graphics(256, 92, 30);
        Hit[] hits = new Hit[2];
        FIRST.send(player, target);
        int delay = SECOND.send(player, target);
        hits[0] = new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setRangedAmmo(ammoDef);
        hits[1] = new Hit(player, style, type).randDamage(maxDamage).clientDelay(delay).setRangedAmmo(ammoDef);
        player.getCombat().removeAmmo(ammo, hits);
        target.hit(hits);
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 55;
    }

}