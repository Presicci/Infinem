package io.ruin.model.content.upgrade.effects;

import io.ruin.model.combat.Hit;
import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 7/13/2021
 */
public class DemonsLight extends ItemUpgrade {

    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        if (hit.attacker != null && hit.attacker.npc != null && hit.attacker.npc.getDef().demon) {
            hit.boostDamage(0.05);
        }
    }

}