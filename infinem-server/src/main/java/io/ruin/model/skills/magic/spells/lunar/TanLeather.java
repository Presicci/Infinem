package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.player.PlayerBoolean;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class TanLeather extends Spell {

    private enum Leather {

        COWHIDE(1739, 1743),
        GREEN_DRAGONHIDE(1753, 1745),
        BLUE_DRAGONHIDE(1751, 2505),
        RED_DRAGONHIDE(1749, 2507),
        BLACK_DRAGONHIDE(1747, 2509);

        private final int leather, hide;

        Leather(int hide, int leather) {
            this.hide = hide;
            this.leather = leather;
        }

        private static final Map<Integer, Leather> LOOKUP;

        static {
            Map<Integer, Leather> $values = new HashMap<>();
            for (Leather leather : values()) {
                $values.put(leather.hide, leather);
            }
            LOOKUP = $values;
        }

        static Leather forId(int id) {
            return LOOKUP.get(id);
        }
    }

    public TanLeather() {
        Item[] required = {
                Rune.ASTRAL.toItem(2),
                Rune.NATURE.toItem(1),
                Rune.FIRE.toItem(5)
        };
        registerClick(78, 81, true, required, (p, i) -> {
            if (i == 0) {
                List<Item> items = p.getInventory().collectItems(Leather.COWHIDE.hide, Leather.GREEN_DRAGONHIDE.hide,
                        Leather.BLUE_DRAGONHIDE.hide, Leather.RED_DRAGONHIDE.hide, Leather.BLACK_DRAGONHIDE.hide);
                if (items == null || items.size() == 0) {
                    p.sendMessage("You don't have any leather to tan.");
                    return false;
                }
                p.startEvent(event -> {
                    List<Item> leather = items;
                    p.lock();
                    p.animate(4413);
                    p.graphics(746, 96, 0); // TODO proper gfx
                    p.publicSound(2879);
                    if (leather.size() > 5) { // Limit of 5
                        leather = leather.subList(0, 5);
                    }
                    leather.forEach(item -> item.setId(
                            Leather.forId(item.getId()) == Leather.COWHIDE ? (PlayerBoolean.TAN_SOFT_LEATHER.has(p) ? Items.LEATHER : Items.HARD_LEATHER) : Leather.forId(item.getId()).leather)
                    );
                    event.delay(3);
                    p.unlock();
                });
                return true;
            } else {
                p.dialogue(
                        new OptionsDialogue("Tan cowhide into...",
                                new Option("Soft leather", () -> PlayerBoolean.TAN_SOFT_LEATHER.setTrue(p)),
                                new Option("Hard leather", () -> PlayerBoolean.TAN_SOFT_LEATHER.setFalse(p))
                        )
                );
                return false;
            }
        });
    }
}
