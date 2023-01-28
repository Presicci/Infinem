package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class Dialogue {

    public abstract void open(Player player);

    private Consumer<Player> onClose = null;
    private BiConsumer<Player, Integer> biOnContinue = null;
    private int value = 0;

    public Dialogue setOnClose(Consumer<Player> playerConsumer){
        this.onClose = playerConsumer;
        return this;
    }

    public Dialogue setOnContinue(BiConsumer<Player, Integer> playerTypeConsumer, int value){
        this.biOnContinue = playerTypeConsumer;
        this.value = value;
        return this;
    }

    public void closed(Player player) {
        if(onClose != null)
            onClose.accept(player);
    }

    public void continueDialogue(Player player) {
        if(biOnContinue != null && value > 0)
            biOnContinue.accept(player, value);
    }

}