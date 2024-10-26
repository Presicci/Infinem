package io.ruin.model.combat.special.melee;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.skills.prayer.Prayer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/26/2024
 */
public class Voidwaker implements Special {

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return name.equalsIgnoreCase("voidwaker");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(1378);
        player.publicSound(6182, 5, 25);
        target.graphics(2363);

        // Set the damage to a guaranteed amount between 50% - 150% of the max hit
        final double minimumDamage = 50 / 100d;
        final double maximumDamage = 150 / 100d;
        int adjustedMinDamage = (int) (maxDamage * minimumDamage);
        int adjustedMaxDamage = (int) (maxDamage * maximumDamage);

        if (target.player != null && target.player.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
            // Praying against does reduced damage, but it's still guaranteed!
            adjustedMinDamage *= 0.65;
            adjustedMaxDamage *= 0.65;
        }

        target.hit(new Hit(player, AttackStyle.MAGIC, AttackType.ACCURATE).randDamage(adjustedMinDamage, adjustedMaxDamage).ignoreDefence().ignorePrayer());
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }
}
