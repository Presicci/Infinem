package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Containers;
import io.ruin.model.skills.farming.Farming;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.concurrent.atomic.AtomicInteger;

public class Humidify extends Spell {

    public Humidify() {
        Item[] runes = {
                Rune.ASTRAL.toItem(1),
                Rune.WATER.toItem(3),
                Rune.FIRE.toItem(1)
        };
        registerClick(68, 65, true, runes, (p, i) -> {
            AtomicInteger count = new AtomicInteger();
            for (Item item : p.getInventory().getItems()) {
                if (item != null) {
                    /* Fill up our water containers */
                    for (Containers containers : Containers.values()) {
                        if (item.getId() == containers.empty) {
                            item.setId(containers.full);
                            count.getAndIncrement();
                        }
                    }
                    Farming.CROPS.stream().filter(crop -> crop instanceof TreeCrop).map(crop -> (TreeCrop) crop).filter(crop -> crop.getSeedling() == item.getId()).forEach(crop -> {
                        item.setId(crop.getWateredSeedling());
                        count.getAndIncrement();
                    });
                }
            }
            if (count.get() > 0) {
                p.startEvent(e -> {
                    p.lock();
                    p.animate(6294);
                    p.graphics(1061, 72, 0);
                    e.delay(4);
                    p.unlock();
                });
                return true;
            }

            p.sendMessage("You have nothing in your inventory that this spell can humidify.");
            return false;
        });
    }

}