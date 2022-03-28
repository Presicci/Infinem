package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/27/2022
 */
public class StringJewellery extends Spell {

    private enum Amulet {
        UNSTRUNG_SYMBOL(1,1714, 1718),
        UNSTRUNG_EMBLEM(1,1720, 1724),
        SALVE_AMULET(35,4082, 4081),
        GOLD_AMULET(8,1673, 1692),
        OPAL_AMULET(27,21099, 21108),
        JADE_AMULET(34,21102, 21111),
        TOPAZ_AMULET(45,21105, 21114),
        SAPPHIRE_AMULET(24,1675, 1694),
        EMERALD_AMULET(31, 1677, 1696),
        RUBY_AMULET(50, 1679, 1698),
        DIAMOND_AMULET(70, 1681, 1700),
        DRAGONSTONE_AMULET(80,1683, 1702),
        ONYX_AMULET(90,  6579, 6581),
        ZENYTE_AMULET(98,  19501, 19541);

        private final int levelReq, unstrung, strung;

        Amulet(int levelReq, int unstrung, int strung) {
            this.levelReq = levelReq;
            this.unstrung = unstrung;
            this.strung = strung;
        }
    }

    public StringJewellery() {
        Item[] runes = {
                Rune.ASTRAL.toItem(2),
                Rune.WATER.toItem(5),
                Rune.EARTH.toItem(10)
        };
        clickAction = (p, i) -> {
            if (!p.getStats().check(StatType.Magic, 80, "cast this spell"))
                return;
            p.startEvent(e -> {
                int count = 0;
                for (Item item : p.getInventory().getItems()) {
                    for (Amulet amulet : Amulet.values()) {
                        if (item != null && item.getId() == amulet.unstrung) {
                            if (p.getStats().get(StatType.Crafting).currentLevel < amulet.levelReq) {
                                continue;
                            }
                            RuneRemoval r = null;
                            if ((r = RuneRemoval.get(p, runes)) == null) {
                                p.sendMessage("You don't have enough runes to cast this spell.");
                                break;
                            }
                            item.setId(amulet.strung);
                            p.animate(4412);
                            p.graphics(746, 96, 0);
                            p.publicSound(2879);
                            r.remove();
                            p.getStats().addXp(StatType.Crafting, 4, true);
                            p.getStats().addXp(StatType.Magic, 83, true);
                            e.delay(3);
                            count++;
                        }
                    }
                }
                if (count == 0)
                    p.sendMessage("You don't have any jewellry to string.");
            });
        };
    }

}