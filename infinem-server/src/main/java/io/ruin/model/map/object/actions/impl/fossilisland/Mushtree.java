package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
public class Mushtree {
    static {
        InterfaceHandler.register(Interface.MUSH_TREE, h -> {
            ObjectAction.register(30924, "use", (player, object) -> Mushtree.open(player));
            ObjectAction.register(30920, "use", (player, object) -> Mushtree.open(player));
            h.actions [4] = (SimpleAction) Mushtree::houseonthehill;
            h.actions [8] = (SimpleAction) Mushtree::verdantvalley;
            h.actions [12] = (SimpleAction) Mushtree::stickyswamp;
            h.actions [16] = (SimpleAction) Mushtree::mushroomeadow;
        });
    }

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 608);
        player.getPacketSender().sendString(608, 5,"House on the Hill");
        player.getPacketSender().sendString(608, 9,"Verdant Velley");
        player.getPacketSender().sendString(608, 13,"Sticky Swamp");
        player.getPacketSender().sendString(608, 17,"Mushroom Meadow");
    }

    public static void houseonthehill(Player player) {
        player.lock();
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
        player.getPacketSender().fadeOut();
        player.getMovement().teleport(3764, 3879, 1);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

    public static void verdantvalley(Player player) {
        player.lock();
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
        player.getPacketSender().fadeOut();
        player.getMovement().teleport(3757, 3757, 0);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

    public static void stickyswamp(Player player) {
        player.lock();
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
        player.getPacketSender().fadeOut();
        player.getMovement().teleport(3676, 3755, 0);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

    public static void mushroomeadow(Player player) {
        player.lock();
        player.closeInterface(InterfaceType.MAIN);
        player.getPacketSender().sendClientScript(101, "i", 11);
        player.getPacketSender().fadeOut();
        player.getMovement().teleport(3676, 3871, 0);
        player.getPacketSender().fadeIn();
        player.unlock();
    }

}