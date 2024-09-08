package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Option;

import java.util.List;

public class OptionScroll {

    public final String title;

    public final boolean allowHotkeys;

    public final Option[] options;

    public final String clientString;

    public OptionScroll(String title, boolean allowHotkeys, Option... options) {
        this.title = title;
        this.allowHotkeys = allowHotkeys;
        this.options = options;
        //Yeh Ik Edu, you love doing stuff here. ;) (;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < options.length; i++) {
            if(i != 0)
                sb.append("|");
            sb.append(options[i].name);
        }
        this.clientString = sb.toString();
    }

    public void open(Player player) {
        player.optionScroll = this;
        player.openInterface(InterfaceType.MAIN, 187);
        player.getPacketSender().sendClientScript(217, "ss1", title, clientString, allowHotkeys ? 1 : 0);
        player.getPacketSender().sendAccessMask(187, 3, 0, 127, 1);
    }

    private void select(Player player, int slot) {
        if(slot < 0 || slot >= options.length) {
            player.closeInterfaces();
            return;
        }
        player.closeInterface(InterfaceType.MAIN);
        options[slot].select(player);
    }

    public static void open(Player player, String title, boolean allowHotkeys, Option... options) {
        new OptionScroll(title, allowHotkeys, options).open(player);
    }

    public static void open(Player player, String title, Option... options) {
        open(player, title, false, options);
    }

    public static void open(Player player, String title, List<Option> options) {
        open(player, title, false, options.toArray(new Option[options.size()]));
    }

    public static void open(Player player, String title, boolean allowHotkeys, List<Option> options) {
        open(player, title, allowHotkeys, options.toArray(new Option[options.size()]));
    }

    static {
        InterfaceHandler.register(187, h -> {
            h.closedAction = (player, integer) -> {
                player.getPacketSender().sendClientScript(927, "1",  1);
                player.optionScroll = null;
            };
            h.actions[3] = (SlotAction) (player, slot) -> {
                player.optionScroll.select(player, slot);
            };
        });
    }

}
