package io.ruin.model.combat.special;

import io.ruin.api.utils.PackageLoader;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;

public interface Special extends ItemDefinition.ItemDefPredicate {

    default boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        /* override required */
        return false;
    }

    default boolean handleActivation(Player player) {
        /* override required */
        return false;
    }

    default int getDrainAmount() {
        return 0;
    }

    static void load() throws Exception {
        for(Class c : PackageLoader.load("io.ruin.model.combat.special", Special.class)) {
            Special special = (Special) c.newInstance();
            ItemDefinition.forEach(def -> {
                if(special.test(def))
                    def.special = special;
            });
        }
    }
}