package io.ruin.model.map.object.actions.impl.locations.prifddinas;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;

public class GauntletEntrance {

    public static final Bounds GAUNTLET_BOUNDS = new Bounds(new Position(1975, 5682, 1), 24);

    static {
        ObjectAction.register(36081, "enter", (player, obj) -> toGauntlet(player));
        ObjectAction.register(36082, "channel", (player, obj) -> leaveGauntlet(player));
        ObjectAction.register(37340, 1, (player, obj) -> enterGauntlet(player));
        ObjectAction.register(37344, 1, (player, obj) -> readRecipe(player));
        ObjectAction.register(36075, 1, (player, obj) -> readRecipe(player));
    }

    public static void readRecipe(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.SINGING_BOWL_RECIPE);
    }

    public static void toGauntlet(Player player) {
        player.lock();
        player.getPacketSender().fadeOut();
        player.dialogue(
                new MessageDialogue("<col=880000>Welcome to The Gauntlet.")
        );
        player.getMovement().teleport(3036,6124, 1);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

    public static void leaveGauntlet(Player player) {
        player.lock();
        player.getPacketSender().fadeOut();
        player.dialogue(
                new MessageDialogue("Welcome to the Almodd District")
        );
        player.getMovement().teleport(3228, 6116, 0);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

    public static void enterGauntlet(Player player) {
        player.dialogue(new MessageDialogue("The Gauntlet is currently not available.")
        );
        return;
//        }
    }

}
