package io.ruin.model.content;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import lombok.val;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2023
 */
public abstract class ChapteredBook extends Book {

    public ChapteredBook(final Player player) {
        super(player);
    }

    @Override
    @Deprecated
    public String getString() {
        return null;
    }

    public abstract String[] getChapters();

    public abstract String[] getContent();

    protected final Int2IntOpenHashMap mappedPages = new Int2IntOpenHashMap();

    @Override
    protected void sendBook(final boolean open) {
        player.openInterface(InterfaceType.MAIN, 680);
        player.getPacketSender().sendString(680, 6, getTitle());
        sendPageNumbers();
        if (open) {
            int chapterIndex = 0;
            val list = new ArrayList<String>(100);
            val splitContext = splitIntoLine(getContent(), 27);
            list.add("Chapters");
            for (int i = 1; i < 15; i++) {
                list.add("");
            }

            for (val arrays : splitContext) {
                mappedPages.put(chapterIndex++, list.size() / 30);
                int lineCount = 0;
                for (val string : arrays) {
                    list.add(string);
                    lineCount++;
                }
                lineCount -= (lineCount / 15) * 15;


                while (lineCount++ < 15) {
                    list.add("");
                }
            }
            context = list.toArray(new String[0]);
            maxPages = (int) (Math.ceil((double) context.length / 30));
        }
        player.getPacketSender().setHidden(680, 108, page == 1);  // Hide back button
        player.getPacketSender().setHidden(680, 110, page == maxPages);   // Hide next button
        player.getPacketSender().setHidden(680, 9, page == 1); // Hide 'First Page' button
        final int offset = (page - 1) * 30;

        player.getPacketSender().setHidden(680, 77, true);
        player.getPacketSender().setHidden(680, 78, true);
        int chapterCount = 0;
        val chapters = getChapters();
        for (int i = 79; i <= 106; i ++) {
            if (i == 92) continue;
            if (offset == 0 && chapterCount < chapters.length) {
                player.getPacketSender().setHidden(680, i, false);
                player.getPacketSender().sendString(680, i, chapters[chapterCount++]);
                continue;
            }
            player.getPacketSender().setHidden(680, i, true);
        }
        for (int i = 45; i <= 75; i++) {
            if (i == 60) continue;
           if ((i - 45 + offset) >= context.length) {
                player.getPacketSender().sendString(680, i, "");
                continue;
            }
            player.getPacketSender().sendString(680, i, context[i - 45 + offset - (i > 60 ? 1 : 0)]);
        }

    }

    public static final ArrayList<String[]> splitIntoLine(final String[] strings, final int maxCharInLine) {
        val list = new ArrayList<String[]>();
        for (val input : strings) {
            final StringTokenizer tok = new StringTokenizer(input, " ");
            final StringBuilder output = new StringBuilder(input.length());
            int lineLen = 0;
            while (tok.hasMoreTokens()) {
                String word = tok.nextToken();
                if (word.startsWith("<col")) {
                    if (lineLen > 0) {
                        output.append("\n");
                        lineLen = 0;
                    }
                    output.append(word);
                    continue;
                }
                if (word.equals("<br>")) {
                    lineLen = 0;
                    output.append("\n");
                    continue;
                }
                while (word.length() > maxCharInLine) {
                    output.append(word.substring(0, maxCharInLine - lineLen) + "\n");
                    word = word.substring(maxCharInLine - lineLen);
                    lineLen = 0;
                }
                final int wordLength = word.startsWith("<col") ? 0 : word.length();
                if (lineLen + wordLength > maxCharInLine) {
                    output.append("\n");
                    lineLen = 0;
                }
                if (wordLength > 0) {
                    output.append(word).append(" ");
                    lineLen += wordLength + 1;
                } else {
                    output.append(word);
                }
            }
            list.add(output.toString().split("\n"));
        }
        return list;
    }

    @Override
    public void handleButtons(final int componentId) {
        if (componentId == 108) {
            if (page > 1) {
                page--;
            }
        } else if (componentId == 110) {
            if (page < maxPages) {
                page++;
            }
        } else if (componentId == 9) {
            page = 1;
        } else if (componentId == 8) {
            player.closeInterface(InterfaceType.MAIN);
            return;
        } else if (componentId >= 79 && componentId <= 107) {
            val chapterIndex = (componentId - 79);
            val pageIndex = mappedPages.get(chapterIndex);
            if (pageIndex != -1) {
                page = pageIndex + 1;
            }
        }
        sendBook(false);
    }

    @Override
    protected void sendPageNumbers() {
        player.getPacketSender().sendString(680, 10, "" + ((page * 2) - 1));
        player.getPacketSender().sendString(680, 11, "" + (page * 2));
    }

    static {
        InterfaceHandler.register(680, (h -> {
            h.closedAction = ((p, integer) -> p.resetAnimation());
            h.actions[108] = (SimpleAction) p -> handleComponent(p, 108);
            h.actions[110] = (SimpleAction) p -> handleComponent(p, 110);
            h.actions[9] = (SimpleAction) p -> handleComponent(p, 9);
            h.actions[8] = (SimpleAction) p -> handleComponent(p, 8);
            for (int index = 77; index < 108; index++) {
                int finalIndex = index;
                if (finalIndex == 92) continue;
                h.actions[index] = (SimpleAction) p -> handleComponent(p, finalIndex);
            }

        }));
    }
}
