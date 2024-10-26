package io.ruin.model.combat.special.ranged;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

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
        player.animate(9964);
        player.graphics(2354);
        target.graphics(2355, 0, 30);
        for (int i = 0; i < 4; i++) {
            target.hit(new Hit(player, attackStyle, attackType)
                    .randDamage((int) (maxDamage * 0.4))
                    .boostAttack(1.0).clientDelay(1));
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