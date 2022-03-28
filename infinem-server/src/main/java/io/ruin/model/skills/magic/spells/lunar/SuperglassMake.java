package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SuperglassMake extends Spell {

    private static final int[] SECONDARIES = {
            Items.SODA_ASH,
            Items.SEAWEED,
            21504,
            Items.SWAMP_WEED
    };

    public SuperglassMake() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.FIRE.toItem(6),
                Rune.AIR.toItem(10)
        };
        registerClick(77, 78, true, runes, (p, i) -> {
            List<Item> items = p.getInventory().collectItems(1783);
            if (items == null || items.size() == 0) {
                p.sendMessage("You don't have any sand to turn into glass.");
                return false;
            }
            if (!p.getInventory().containsAny(false, SECONDARIES)) {
                p.sendMessage("You don't have any seaweed or soda ash to turn into glass.");
                return false;
            }
            p.startEvent(event -> {
                p.lock();
                p.animate(4413);
                p.graphics(729, 96, 0);
                AtomicInteger count = new AtomicInteger();
                items.forEach(item -> {
                    for (int id : SECONDARIES) {
                        if (p.getInventory().contains(id)) {
                            p.getInventory().remove(id, 1);
                            item.setId(1775);
                            count.incrementAndGet();
                            break;
                        }
                    }
                });
                p.getStats().addXp(StatType.Crafting, 10 * count.get(), true);
                event.delay(1);
                p.unlock();
            });
            return true;
        });
    }
}
