package io.ruin.model.content.scroll;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/15/2023
 */
public class ScrollingScroll {
    private final String title;
    private final String lines;

    public ScrollingScroll(String title, String lines) {
        this.title = title;
        this.lines = lines;
    }

    public ScrollingScroll(String title, String... lines) {
        this.title = title;
        StringBuilder l = new StringBuilder();
        for (String line : lines) {
            l.append(line).append("<br>");
        }
        this.lines = l.toString();
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.SCROLLING_SCROLL);
        player.getPacketSender().sendClientScript(418, "ss", title, lines);
    }
}
