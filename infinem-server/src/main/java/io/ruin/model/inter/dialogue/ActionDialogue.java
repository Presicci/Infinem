package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;

import java.util.function.Consumer;

public class ActionDialogue extends Dialogue {

    private Runnable action;
    private Consumer<Player> consumer;

    public ActionDialogue(Runnable action) {
        this.action = action;
    }

    public ActionDialogue(Consumer<Player> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void open(Player player) {
        player.closeInterface(InterfaceType.CHATBOX);
        if (consumer != null)
            consumer.accept(player);
        else
            action.run();
    }

}