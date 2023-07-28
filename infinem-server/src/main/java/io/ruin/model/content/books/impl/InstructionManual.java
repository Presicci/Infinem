package io.ruin.model.content.books.impl;

import io.ruin.model.content.books.Book;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/27/2023
 */
public class InstructionManual extends Book {

    public InstructionManual(final Player player) {
        super(player);
    }

    @Override
    public String getString() {
        return
                "Constructing the cannon" +
                        " <br> " +
                        "To construct the cannon, firstly set down the base of the cannon firmly onto the ground. Next add the Dwarf stand to the cannon base. Then add the Barrels. Lastly add the Furnace, which powers the cannon. You should now have a fully set up Multi Cannon, to splat nasty creatures! <br> " +
                        " <br> " +
                        "Making ammo." +
                        " <br> " +
                        "The ammo for the cannon is made from steel bars. Firstly you must heat up a steel bar in a furnace." +
                        " <br> " +
                        "Now pour the molten steel into a cannon ammo mould. You should now have a ready to fire Multi cannon ball." +
                        " <br> " +
                        " <br> " +
                        "Firing the Cannon." +
                        " <br> " +
                        "The cannon will only fire when monsters are available to target. If you are carrying enough ammo the cannon will fire up to 30 rounds before it runs out and stops. The cannon will automatically target non friendly creatures <br> " +
                        " <br> " +
                        " <br> " +
                        "Dwarf Cannon Warranty" +
                        " <br> " +
                        "If your cannon is stolen or has been lost, after or during being set up, the Dwarf engineer will replace the parts, however cannon parts that were given away or dropped will not be replaced for free. It is only possible to operate one cannon at a time. <br> " +
                        " <br> " +
                        "By order of the members of the noble Dwarven Black Guard.";
    }

    @Override
    public String getTitle() {
        return "Dwarven Multi Cannon";
    }

    static {
        ItemAction.registerInventory(5, "read", ((p, item) -> Book.openBook(new InstructionManual(p))));
    }
}