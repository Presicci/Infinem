package io.ruin.model.inter.journal.dropviewer;

import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.item.pet.Pet;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/1/2023
 */
public class DropViewerResultPet extends DropViewerResult {
    private final Pet pet;

    public DropViewerResultPet(Pet pet, int chance) {
        this.pet = pet;
        this.chance = chance;
    }

    public String get() {
        String name = ItemDef.get(pet.itemId).name;
        String amount = "" + (pet.dropThreshold > 0 ? "Threshold: <br><col=F5DEB3>" + pet.dropThreshold : "<col=F5DEB3>No threshold");
        String c = chance == 1 ? "Always" : ("1 / " + (chance == 0 ? "?" : chance));
        return name + "|" + amount + "|" + c;
    }

    public Item getItem() {
        return new Item(pet.itemId, 1);
    }
}
