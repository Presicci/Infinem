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
        player.openInterface(InterfaceType.MAIN, 27);
        player.getPacketSender().sendString(27, 3, getTitle());
        sendPageNumbers();
        if (open) {
            int chapterIndex = 0;
            val list = new ArrayList<String>(100);
            val splitContext = splitIntoLine(getContent(), 29);
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
            context = list.toArray(new String[list.size()]);
            maxPages = (int) (Math.ceil(context.length / 30));
        }
        player.getPacketSender().setHidden(27, 95, page == 1);  // Hide back button
        player.getPacketSender().setHidden(27, 97, page == maxPages);   // Hide next button
        player.getPacketSender().setHidden(27, 160, page == 1); // Hide 'First Page' button
        final int offset = (page - 1) * 30;

        player.getPacketSender().setHidden(27, 100, true);
        player.getPacketSender().setHidden(27, 102, true);
        int chapterCount = 0;
        val chapters = getChapters();
        for (int i = 104; i <= 158; i += 2) {
            if (offset == 0 && chapterCount < chapters.length) {
                player.getPacketSender().setHidden(27, i, false);
                player.getPacketSender().sendString(27, i + 1, chapters[chapterCount++]);
                continue;
            }
            player.getPacketSender().setHidden(27, i, true);
        }
        for (int i = 33; i <= 62; i++) {
            if ((i - 33 + offset) >= context.length) {
                player.getPacketSender().sendString(27, i, "");
                continue;
            }
            player.getPacketSender().sendString(27, i, context[i - 33 + offset]);
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
        if (componentId == 94) {
            if (page > 1) {
                page--;
            }
        } else if (componentId == 96) {
            if (page < maxPages) {
                page++;
            }
        } else if (componentId == 160) {
            page = 1;
        } else if (componentId == 162) {
            player.closeInterface(InterfaceType.MAIN);
            return;
        } else if (componentId >= 105 && componentId <= 159) {
            val chapterIndex = (componentId - 105) / 2;
            val pageIndex = mappedPages.get(chapterIndex);
            if (pageIndex != -1) {
                page = pageIndex + 1;
            }
        }
        sendBook(false);
    }

    @Override
    protected void sendPageNumbers() {
        player.getPacketSender().sendString(27, 98, "" + ((page * 2) - 1));
        player.getPacketSender().sendString(27, 99, "" + (page * 2));
    }

    static {
        InterfaceHandler.register(27, (h -> {
            h.closedAction = ((p, integer) -> p.resetAnimation());
            h.actions[94] = (SimpleAction) p -> handleComponent(p, 94);
            h.actions[96] = (SimpleAction) p -> handleComponent(p, 96);
            h.actions[160] = (SimpleAction) p -> handleComponent(p, 160);
            h.actions[162] = (SimpleAction) p -> handleComponent(p, 162);
            for (int index = 105; index < 160; index++) {
                int finalIndex = index;
                h.actions[index] = (SimpleAction) p -> handleComponent(p, finalIndex);
            }

        }));
    }
}
