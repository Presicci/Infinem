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
        player.openInterface(InterfaceType.MAIN, 680);
        player.getPacketSender().sendString(680,6, getTitle());
        clearInterface();
        sendPageNumbers();
        if (open) {
            context = splitIntoLine(getString(), 27);
            maxPages = (int) (Math.ceil((double) context.length / 30));
        }
        player.getPacketSender().setHidden(680, 108, page == 1);  // Hide back button
        player.getPacketSender().setHidden(680, 110, page == maxPages);   // Hide next button
        player.getPacketSender().setHidden(680, 9, page == 1); // Hide 'First Page' button
        final int offset = (page - 1) * 30;

        //player.getPacketSender().sendString(26, 102, context[0 + offset]);
        player.getPacketSender().setHidden(680, 76, true);
        player.getPacketSender().setHidden(680, 92, true);
        for (int i = 45; i <= 75; i++) {
            if (i == 60) continue;
            if ((i - 45 + offset) >= context.length) {
                player.getPacketSender().sendString(680, i, "");
                break;
            }
            player.getPacketSender().sendString(680, i, context[i - 45 + offset - (i > 60 ? 1 : 0)]);
        }
    }

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
        }
        sendBook(false);
    }

    private void clearInterface() {
        for (int i = 45; i <= 75; i++) {
            if (i == 60) continue;
            player.getPacketSender().sendString(680, i, "");
        }
    }

    protected void sendPageNumbers() {
        player.getPacketSender().sendString(680, 10, "" + ((page * 2) - 1));
        player.getPacketSender().sendString(680, 11, "" + (page * 2));
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
}
