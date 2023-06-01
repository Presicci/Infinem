package io.ruin.model.inter.journal.dropviewer;

import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.utility.Misc;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerResultItem extends DropViewerResult {
    private final int id;
    private final int min;
    private final int max;

    public DropViewerResultItem(int id, int min, int max, int chance) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.chance = chance;
    }

    public String get() {
        String name = getItemName();
        String amount = getAmountString();
        String c = chance == 1 ? "Always" : ("1 / " + (chance == 0 ? "?" : chance));
        return name + "|" + amount + "|" + c;
    }

    private String getItemName() {
        switch (id) {
            case 22973:
                return "Brimstone Ring Part";
            default:
                return ItemDef.get(id).name;
        }
    }

    private String getAmountString() {
        switch (id) {
            case 22973:
                return "Dropped order:<br><col=F5DEB3>Eye, Fang, Heart";
            default:
                return min == max ? Misc.abbreviateItemQuantity(min)
                        : (Misc.abbreviateItemQuantity(min) + "-" + Misc.abbreviateItemQuantity(max));
        }
    }

    public Item getItem() {
        return new Item(id, (min + max) / 2);
    }
}
