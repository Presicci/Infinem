package io.ruin.model.combat.special.melee;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/26/2024
 */
public class UrsineChainmace implements Special {

    @Override
    public boolean accept(ItemDefinition def, String name) {
        return name.contains("ursine chainmace");
    }

    @Override
    public boolean handle(Player player, Entity target, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        player.animate(9963);
        player.graphics(2342, 90, 0);
        int damage = target.hit(new Hit(player, attackStyle, attackType)
                .randDamage(maxDamage)
                .boostAttack(1.0));
        if (target.isPlayer()) {
            target.player.getStats().get(StatType.Agility).drain(20);
            target.player.runDelay.delay(6);
        }
        if (damage > 0) {
            World.startEvent(e -> {
                int hits = 0;
                while (++hits < 6) {
                    e.delay(2);
                    target.hit(new Hit(player).fixedDamage(4));
                }
            }).setCancelCondition(() -> target == null || player == null || target.getCombat().isDead());
        }
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 50;
    }

}
