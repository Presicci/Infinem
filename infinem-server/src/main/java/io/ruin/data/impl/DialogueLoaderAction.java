package io.ruin.data.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public enum DialogueLoaderAction {
    HEAL((player) -> {
        player.getStats().get(StatType.Hitpoints).restore();
    }),
    ITEM(null),  // Handled specifically in the loader
    SHOP(null),
    ;

    private final Consumer<Player> action;

    DialogueLoaderAction(Consumer<Player> action) {
        this.action = action;
    }


}
