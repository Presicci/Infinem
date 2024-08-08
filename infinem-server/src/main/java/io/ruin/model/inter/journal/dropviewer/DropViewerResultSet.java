package io.ruin.model.inter.journal.dropviewer;

import io.ruin.model.item.Item;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 8/8/2024
 */
public class DropViewerResultSet extends DropViewerResult {

    private final Item faceItem;
    private final Item[] items;

    public DropViewerResultSet(int chance, Item faceItem, Item... items) {
        this.chance = chance;
        this.faceItem = faceItem;
        this.items = items;
    }

    private String getItemString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            if (item == faceItem) continue;
            sb.append(item.getAmount() > 1 ? item.getAmount() + "x " : "");
            sb.append(item.getDef().name);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        int lastCommaIndex = sb.lastIndexOf(",");
        if (lastCommaIndex > 0) sb.replace(lastCommaIndex, lastCommaIndex + 1, " and");
        return sb.toString();
    }

    public String get() {
        String name = faceItem.getDef().name;
        String amount = faceItem.getAmount() + "";
        String info = "Drops with: <col=F5DEB3>" + getItemString();
        String chance = this.chance == 1 ? "Always" : ("1 / " + (this.chance == 0 ? "?" : this.chance));
        return name + "|" + amount + "|" + chance + "|" + info;
    }

    public Item getItem() {
        return new Item(faceItem.getId(), faceItem.getAmount());
    }
}
