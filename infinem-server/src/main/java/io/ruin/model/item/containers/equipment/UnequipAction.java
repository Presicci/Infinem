package io.ruin.model.item.containers.equipment;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public interface UnequipAction {

    void handle(Player player);

    static void register(int itemId, UnequipAction action) {
        ItemDef def = ItemDef.get(itemId);
        def.unequipAction = action;
    }
}
