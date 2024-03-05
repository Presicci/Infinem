package io.ruin.model.item.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.actions.ItemAction;

public class DestroyAction {

    static {
        ItemDef.forEach(def -> ItemAction.registerInventory(def.id, "destroy", (player, item) -> {
            String destroyString = def.getCustomValueOrDefault("DESTROY", "Warning: This action cannot be undone.");
            player.dialogue(
                    new YesNoDialogue(
                            "Are you sure you want to destroy this item?",
                            destroyString,
                            item, item::remove
                    )
            );
        }));
    }

}
