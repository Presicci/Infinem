package io.ruin.model.content;

import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import lombok.val;

import java.util.StringTokenizer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2023
 */
public abstract class Book {
    public Book(final Player player) {
        this.player = player;
    }

    protected final Player player;
    protected int page = 1;
    protected int maxPages;
    protected int interfaceId;
    protected String[] context;
    public abstract String getTitle();
    public abstract String getString();

    public static final String[] splitIntoLine(final String input, final int maxCharInLine){
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
                output.append("\n");
                continue;
            }
            while(word.length() > maxCharInLine){
                output.append(word.substring(0, maxCharInLine-lineLen) + "\n");
                word = word.substring(maxCharInLine-lineLen);
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
        return output.toString().split("\n");
    }

    protected void sendBook(final boolean open) {
        player.openInterface(InterfaceType.MAIN, 26);
        player.getPacketSender().sendString(26,3, getTitle());
        clearInterface();
        sendPageNumbers();
        if (open) {
            context = splitIntoLine(getString(), 25);
            maxPages = (int) (1 + Math.ceil(context.length / 29));
        }
        player.getPacketSender().setHidden(26, 65, page == 1);
        player.getPacketSender().setHidden(26, 67, page == maxPages);
        final int offset = (page - 1) * 29;
        player.getPacketSender().sendString(26, 102, context[0 + offset]);
        for (int i = 74; i < 102; i++) {
            if ((i - 73 + offset) >= context.length) {
                break;
            }
            player.getPacketSender().sendString(26, i - 1, context[i - 73 + offset]);
        }
    }

    public void handleButtons(final int componentId) {
        if (componentId == 64) {
            if (page > 1) {
                page--;
            }
        } else if (componentId == 66) {
            if (page < maxPages) {
                page++;
            }
        }
        sendBook(false);
    }

    private void clearInterface() {
        for (int i = 73; i < 103; i++) {
            player.getPacketSender().sendString(26, i, "");
        }
    }

    protected void sendPageNumbers() {
        player.getPacketSender().sendString(26, 68, "" + ((page * 2) - 1));
        player.getPacketSender().sendString(26, 69, "" + (page * 2));
    }

    public static void openBook(final Book book) {
        book.player.animate(1350);
        book.player.putTemporaryAttribute(AttributeKey.BOOK, book);
        book.sendBook(true);
    }

    protected static void handleComponent(Player player, int component) {
        val object = player.getTemporaryAttribute(AttributeKey.BOOK);
        if (object instanceof Book) {
            ((Book) object).handleButtons(component);
            return;
        }
    }

    static {
        InterfaceHandler.register(26, (h -> {
            h.closedAction = ((p, integer) -> p.resetAnimation());
            h.actions[64] = (SimpleAction) p -> handleComponent(p, 64);
            h.actions[66] = (SimpleAction) p -> handleComponent(p, 66);
        }));
    }
}
