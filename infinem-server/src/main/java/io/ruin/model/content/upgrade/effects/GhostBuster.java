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
        if (hit.attacker != null && hit.attacker.npc != null) {
            Optional<String> npc = PlayerCombat.UNDEAD_NPCS.stream().filter(Objects::nonNull).filter(s -> s.equalsIgnoreCase(hit.attacker.npc.getDef().name) || Utils.containsIgnoreCase(hit.attacker.npc.getDef().name, s)).findAny();
            if (npc.isPresent()) {
                hit.boostDamage(0.05);
            }
        }
    }

}