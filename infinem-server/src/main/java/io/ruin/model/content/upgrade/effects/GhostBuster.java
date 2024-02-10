package io.ruin.model.content.upgrade.effects;

import io.ruin.model.combat.Hit;
import io.ruin.model.content.upgrade.ItemUpgrade;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCombat;
import io.ruin.model.item.Item;
import io.ruin.utility.Utils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 7/13/2021
 */
public class GhostBuster extends ItemUpgrade {

    @Override
    public void preTargetDefend(Player player, Entity target, Item item, Hit hit) {
        if (!target.npc.getDef().undead) {
            hit.boostDamage(0.05);
        }
    }

}