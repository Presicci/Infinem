package io.ruin.model.combat;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/6/2024
 */
public class BaneWeapons {

    static {
        ItemDef.get(Items.DARKLIGHT).addPreTargetDefendListener(BaneWeapons::onSilverlightHit);
        ItemDef.get(Items.SILVERLIGHT).addPreTargetDefendListener(BaneWeapons::onSilverlightHit);
    }

    private static void onSilverlightHit(Player player, Item item, Hit hit, Entity entity) {
        if (entity == null || entity.isPlayer() || !entity.npc.getDef().hasCustomValue("DEMON") || hit.attackStyle == null || !hit.attackStyle.isMelee())
            return;
        hit.boostDamage(0.6);
    }

}
