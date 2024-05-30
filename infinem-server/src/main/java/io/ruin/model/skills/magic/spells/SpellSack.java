package io.ruin.model.skills.magic.spells;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/30/2024
 */
@AllArgsConstructor
public enum SpellSack {
    ANCIENT_ICE(24607),
    ENTANGLE(24613),
    SURGE(26705),
    TELEPORT_SPELL(24615),
    VENGEANCE(24621);

    private final int sackId;

    public boolean canCast(Player player) {
        if (player.wildernessLevel <= 0) return false;
        return player.getInventory().contains(sackId);
    }

    public Item getSack() {
        return new Item(sackId);
    }
}
