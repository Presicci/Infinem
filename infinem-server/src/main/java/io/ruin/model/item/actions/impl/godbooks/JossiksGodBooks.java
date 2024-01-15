package io.ruin.model.item.actions.impl.godbooks;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/14/2024
 */
public class JossiksGodBooks {

    private static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 302);
        for (GodBook book : GodBook.values) {
            refreshBook(player, book);
        }
    }

    private static void refreshBook(Player player, GodBook book) {
        int value = 0;
        int pages = player.getAttributeIntOrZero(book.name() + "_PAGES");
        int godbooks = player.getAttributeIntOrZero("GODBOOKS");
        int mask = 1 << book.ordinal();
        boolean purchasedBook = (godbooks & mask) != 0;
        if (purchasedBook) {
            value = 2;
            if (player.findItem(book.getCompletedBookId()) == null && player.findItem(book.getDamagedBookId()) == null) {
                value = 1;
            }
        }
        if (pages == 15) {
            value = 3;
        }
        player.getPacketSender().sendVarp(261 + book.ordinal(), value);
    }

    private static void clickBook(Player player, int option, GodBook book) {
        if (option == 10) {
            new Item(book.getCompletedBookId()).examine(player);
            return;
        }
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You need some free inventory space to do that.");
            return;
        }
        boolean hasBook = player.findItem(book.getDamagedBookId()) != null || player.findItem(book.getCompletedBookId()) != null;
        if (hasBook) {
            player.sendMessage("You already own a book from this god.");
            return;
        }
        int pages = player.getAttributeIntOrZero(book.name() + "_PAGES");
        int godbooks = player.getAttributeIntOrZero("GODBOOKS");
        if (pages == 15) {
            player.getInventory().add(book.getCompletedBookId());
            refreshBook(player, book);
            return;
        }
        int mask = 1 << book.ordinal();
        boolean purchasedBook = (godbooks & mask) != 0;
        if (purchasedBook) {
            player.getInventory().add(book.getDamagedBookId());
            refreshBook(player, book);
            return;
        }
        player.dialogue(false,
                new ItemDialogue().one(book.getCompletedBookId(), "Unlock the damaged book for 5,000 coins?"),
                new OptionsDialogue("Unlock the book?",
                        new Option("Unlock it.", () -> {
                            if (!player.getInventory().hasFreeSlots(1)) {
                                player.sendMessage("You need some free inventory space to do that.");
                                return;
                            }
                            if (!player.getInventory().contains(995, 5000)) {
                                player.sendMessage("You need at least 5,000 coins to unlock a god book.");
                                return;
                            }
                            player.getInventory().remove(995, 5000);
                            player.getInventory().add(book.getDamagedBookId());
                            player.incrementNumericAttribute("GODBOOKS", mask);
                            refreshBook(player, book);
                        }),
                        new Option("Cancel.")
                )
        );
    }

    static {
        NPCAction.register(4423, "rewards", (player, npc) -> open(player));
        InterfaceHandler.register(302, h -> {
            for (int index = 3; index < 9; index++) {
                int bookIndex = index - 3;
                h.actions[index] = (OptionAction) (player, option) -> clickBook(player, option, GodBook.values()[bookIndex]);
            }
        });
    }
}
