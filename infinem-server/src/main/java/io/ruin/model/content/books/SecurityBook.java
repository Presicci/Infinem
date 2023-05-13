package io.ruin.model.content.books;

import io.ruin.model.content.Book;
import io.ruin.model.content.ChapteredBook;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/18/2023
 */
public class SecurityBook extends ChapteredBook {
    SecurityBook(final Player player) {
        super(player);
    }

    @Override
    public String[] getChapters() {
        return new String[] {
                "<col=000080>Password Tips",
                "<col=000080>Other Security Tips",
                "<col=000080>Stronghold of Security", };
    }

    @Override
    public String[] getContent() {
        return new String[]{
                "<col=000080> Password Tips </col> <br> A good password should be easily remembered by yourself but not easily guessed by anyone else. <br> " +
                        " <br> " +
                        "Choose a password that has both letters and numbers in it for the best security but don't make it so hard that you'll forget it! <br> " +
                        " <br> " +
                        "Never write your password down or leave it in a text file on your computer, someone could find it easily! <br> " +
                        " <br> " +
                        "Never tell anyone your password, not even a Moderator of any kind.",

                "<col=000080> Other Security Tips </col> <br> Set a Bank PIN to help protect your items in case someone manages to steal your account. Speak to a banker and ask to check your PIN settings. <br> " +
                        " <br> " +
                        "Setup the two-factor authentication to ensure no-one can gain unauthorised access to your account, even if they have your password.  <br> " +
                        " <br> " +
                        "Never give your password to ANYONE. This includes your friends, family and moderators in game. <br> " +
                        " <br> " +
                        "Never leave you account logged on if you are away from the computer, it only takes 5 seconds to steal your account!",

                "<col=000080> Stronghold of Security </col>  <br>  Location: The Stronghold of Security, as we call it, is located under the village filled with Barbarians. It was " +
                        "found after they moved their mining operatios and a miner fell through. The Stronghold contains many challenges. Both for those who enjoy combat and " +
                        "those who enjoy challenges of the mind. This book will be very useful to you in your travels there. <br> " +
                        " <br> " +
                        "You can find the Stronghold of Security by looking for a hole in the Barbarian Village. Be sure to take your combat equipment though!",

        };
    }

    @Override
    public String getTitle() {
        return "Security Book";
    }

    static {
        ItemAction.registerInventory(9003, "read", ((p, item) -> Book.openBook(new SecurityBook(p))));
    }
}
