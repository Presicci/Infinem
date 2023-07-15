package io.ruin.model.inter.dialogue;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;

public class PlayerDialogue extends Dialogue {

    private String message;

    private Runnable action;

    private int animationId = 554;

    private int lineHeight;

    private boolean hideContinue;

    public PlayerDialogue(String message) {
        this.message = message;
    }

    public PlayerDialogue action(Runnable action) {
        this.action = action;
        return this;
    }

    public PlayerDialogue animate(int animationId) {
        this.animationId = animationId;
        return this;
    }

    public PlayerDialogue lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public PlayerDialogue hideContinue() {
        this.hideContinue = true;
        return this;
    }

    @Override
    public void open(Player player) {
        message = message.replace("[player name]", player.getName());
        message = message.replace("[Fremennik name]", player.getFremennikName());
        player.openInterface(InterfaceType.CHATBOX, Interface.PLAYER_DIALOGUE);
        player.getPacketSender().sendPlayerHead(Interface.PLAYER_DIALOGUE, 6);
        player.getPacketSender().animateInterface(Interface.PLAYER_DIALOGUE, 6, animationId);
        player.getPacketSender().sendString(Interface.PLAYER_DIALOGUE, 3, player.getName());
        player.getPacketSender().sendString(Interface.PLAYER_DIALOGUE, 5, message);
        player.getPacketSender().setTextStyle(Interface.PLAYER_DIALOGUE, 5, 1, 1, lineHeight);
        player.getPacketSender().sendAccessMask(Interface.PLAYER_DIALOGUE, 4, -1, -1, 1);
        if(hideContinue)
            player.getPacketSender().setHidden(Interface.PLAYER_DIALOGUE, 4, true);
        else
            player.getPacketSender().sendString(Interface.PLAYER_DIALOGUE, 4, "Click here to continue");
        if(action != null)
            action.run();
    }

    static {
        InterfaceHandler.register(Interface.PLAYER_DIALOGUE, h -> h.actions[4] = (SimpleAction) Player::continueDialogue);
    }

}
