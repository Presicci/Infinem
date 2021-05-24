package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;

import java.util.function.Consumer;

public abstract class Dialogue {

    public abstract void open(Player player);

    private Consumer<Player> onClose = null;

    public Dialogue setOnClose(Consumer<Player> playerConsumer){
        this.onClose = playerConsumer;
        return this;
    }

    public void closed(Player player) {
        if(onClose != null) onClose.accept(player);
    }

}