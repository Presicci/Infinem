package io.ruin.model.item.containers.equipment;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public interface UnequipAction {

    void handle(Player player);

    static void register(int itemId, UnequipAction action) {
        ItemDefinition def = ItemDefinition.get(itemId);
        if (def == null) return;
        def.unequipAction = action;
    }
}
