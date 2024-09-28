package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/27/2024
 */
public class PlankMake extends Spell {

    public PlankMake() {
        Item[] runes = {
                Rune.NATURE.toItem(1),
                Rune.ASTRAL.toItem(2),
                Rune.EARTH.toItem(15)
        };
        registerItem(86, 90, true, runes, (p, item) -> {
            p.startEvent(e -> {
                int count = 0;
                for (Item log : p.getInventory().getItems()) {
                    for (Planks plank : Planks.values()) {
                        if (log != null && log.getId() == plank.log) {
                            if (!p.getInventory().contains(995, plank.cost)) {
                                p.sendMessage("You don't have enough gold to cast this spell.");
                                return;
                            }
                            RuneRemoval r = null;
                            if (runes != null && (r = RuneRemoval.get(p, runes)) == null) {
                                p.sendMessage("You don't have enough runes to cast this spell.");
                                return;
                            }
                            log.setId(plank.plank);
                            p.animate(6298);
                            p.graphics(1063, 96, 0);
                            r.remove();
                            p.getInventory().remove(995, plank.cost);
                            p.getStats().addXp(StatType.Magic, 90, true);
                            if (!p.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                                e.delay(3);
                            }
                            count++;
                        }
                    }
                }
                if (count == 0)
                    p.sendMessage("You don't have any planks to make.");
            });
            return false;
        });
    }

    private enum Planks {

        PLANK(1511, 960, 70),
        OAK_PLANK(1521, 8778, 175),
        TEAK_PLANK(6333, 8780, 350),
        MAHOGANY_PLANK(6332, 8782, 1050);

        private int log, plank, cost;

        Planks(int log, int plank, int cost) {
            this.log = log;
            this.plank = plank;
            this.cost = cost;
        }
    }

}