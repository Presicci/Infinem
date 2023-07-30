package io.ruin.model.content.scroll;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/30/2023
 */
public class Scroll {
    private final List<String> lines;

    public Scroll(List<ScrollLine> lines) {
        this.lines = new ArrayList<>(Collections.nCopies(12, null));
        for (ScrollLine line : lines) {
            this.lines.add(line.getLineNumber(), line.getLine());
        }
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.SCROLL);
        int lineNumber = 1;
        for (String line : lines) {
            if (line != null && !line.isEmpty())
                player.getPacketSender().sendString(Interface.SCROLL, lineNumber, line);
            lineNumber++;
        }
    }
}
