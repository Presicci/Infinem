package io.ruin.model.item.containers.equipment;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public interface EquipAction {

    void handle(Player player);

    static void register(int itemId, EquipAction action) {
        ItemDefinition def = ItemDefinition.get(itemId);
        if (def == null) return;
        def.equipAction = action;
    }
}