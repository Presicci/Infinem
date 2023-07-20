package io.ruin.model.content.books.impl;

import io.ruin.model.content.Book;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/13/2023
 */
public class GlassblowingBook extends Book {

    public GlassblowingBook(final Player player) {
        super(player);
    }

    @Override
    public String getString() {
        return
                "<col=000088> Ultimate Guide to <col=000088> Glassblowing" +
                        " <br> " +
                        "Author: Fritz the Glassblower" +
                        " <br> " +
                        "I can tell you now, the art of creating glass has long been a valued trade in Gielinor. Not many could get by without the use of some item made from this marvelous substance." +
                        " <br> " +
                        "It's within this book that we cover the basics of the art, in a hope to get you hooked on this popular pass-time and trade." +
                        " <br> " +
                        "To start with, you will need a few essential items." +
                        " <br> " +
                        "<col=000088> An empty bucket" +
                        " <br> " +
                        "These can be purchased from the majority of general stores and are required to carry the sand you will need to make glass." +
                        " <br> " +
                        "<col=000088> A glassblowing pipe" +
                        " <br> " +
                        "Found on Entrana or often just north-west of the Ranging Guild. As it's[sic] name suggests, this is used to blow air into molten glass." +
                        " <br> " +
                        "<col=000088> Seaweed" +
                        " <br> " +
                        "Seaweed can be found on the shore in many places, but it is also a frequent find when Fishing with nets. This you will need to create soda ash - an important ingredient to the process." +
                        " <br> " +
                        "<col=000088> Soda ash" +
                        " <br> " +
                        "Made by burning seaweed, you will need this to make glass. Other than making it yourself, you can purchase it from other budding glassblowers." +
                        " <br> " +
                        "With these items you are ready to begin! You'll need to find a sandpit, and there are a few dotted around Gielinor. Try the island of Entrana or Yanille, for example. Use your bucket on the sandpit to fill it up." +
                        " <br> " +
                        "Next, you need to find your way to a furnace. Turn your seaweed into soda ash by using it on a range before using the bucket of sand or soda ash on the furnace to create molten glass." +
                        " <br> " +
                        "The last stage is simply to use the molten glass with your glassblowing pipe and, depending on your level of skill, you can create a variety of items." +
                        " <br> " +
                        "I hope you enjoyed this guide, and please keep an eye out for more of my titles!";
    }

    @Override
    public String getTitle() {
        return "Ultimate Guide to Glassblowing";
    }

    static {
        ItemAction.registerInventory(Items.GLASSBLOWING_BOOK, "read", ((p, item) -> Book.openBook(new GlassblowingBook(p))));
    }
}