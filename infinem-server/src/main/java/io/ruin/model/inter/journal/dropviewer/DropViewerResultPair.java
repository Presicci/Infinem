package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerResultPair extends DropViewerResult {
    private final Item item1;
    private final Item item2;

    public DropViewerResultPair(Item item1, Item item2, int chance) {
        this.item1 = item1;
        this.item2 = item2;
        this.chance = chance;
    }

    public String get() {
        String name = item1.getDef().name;
        String amount = item1.getAmount() + "";
        String info = "Drops with: <col=F5DEB3>" + (item2.getAmount() > 1 ? item2.getAmount() + "x " : "") + item2.getDef().name;
        String chance = this.chance == 1 ? "Always" : ("1 / " + (this.chance == 0 ? "?" : this.chance));
        return name + "|" + amount + "|" + chance + "|" + info;
    }

    public Item getItem() {
        return new Item(item1.getId(), item1.getAmount());
    }
}
