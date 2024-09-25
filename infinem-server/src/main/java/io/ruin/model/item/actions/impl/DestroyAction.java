package io.ruin.model.item.actions.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.actions.ItemAction;

import java.util.function.Consumer;

public class DestroyAction {

    static {
        ItemDefinition.forEach(def -> ItemAction.registerInventory(def.id, "destroy", (player, item) -> {
            String destroyString = def.getCustomValueOrDefault("DESTROY", "Warning: This action cannot be undone.");
            Consumer<Player> destroyConsumer = def.getCustomValueOrDefault("DESTROY_CONSUMER", null);
            player.dialogue(
                    new YesNoDialogue(
                            "Are you sure you want to destroy this item?",
                            destroyString,
                            item,
                            () -> {
                                item.remove();
                                if (destroyConsumer != null) destroyConsumer.accept(player);
                            }
                    )
            );
        }));
    }

}
