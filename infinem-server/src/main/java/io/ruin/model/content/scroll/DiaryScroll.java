package io.ruin.model.content.scroll;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;

import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/7/2023
 */
public class DiaryScroll {
    private final String title;
    private final List<String> lines;
    private final int size;

    public DiaryScroll(String title, List<String> lines) {
        this.title = title;
        this.size = lines.size();
        this.lines = lines;
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.DIARY_SCROLL);
        player.getPacketSender().sendClientScript(2498, "i", 1);
        for (int index = 0; index < size; index++) {
            if (index > 174)
                break;
            player.getPacketSender().sendString(Interface.DIARY_SCROLL, index + 4, lines.get(index));
        }
        player.getPacketSender().sendString(Interface.DIARY_SCROLL, 2, title);
        if (size >= 10) {
            player.getPacketSender().sendClientScript(2523, "ii", 1, size);
        }
    }
}
