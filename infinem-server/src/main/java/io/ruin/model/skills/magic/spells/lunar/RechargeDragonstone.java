package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.content.tasksystem.areas.AreaConfig;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/30/2024
 */
public class RechargeDragonstone extends Spell {

    public RechargeDragonstone() {
        Item[] runes = {
                Rune.WATER.toItem(4),
                Rune.ASTRAL.toItem(1),
                Rune.SOUL.toItem(1)
        };
        registerClick(78, 81, true, runes, (player, integer) -> {
            if (!AreaConfig.FREMMY_HARD_LUNAR_SPELLS.checkUnlocked(player, "cast this spell.")) {
                return false;
            }
            player.startEvent(e -> {
                int count = 0;
                for (Item item : player.getInventory().getItems()) {
                    for (Recharge recharge : Recharge.values()) {
                        if (item != null && (item.getId() == recharge.uncharged || item.getId() == recharge.oneCharge || item.getId() == recharge.twoCharges || item.getId() == recharge.threeCharges)) {
                            RuneRemoval r = null;
                            if ((r = RuneRemoval.get(player, runes)) == null) {
                                player.sendMessage("You don't have enough runes to cast this spell.");
                                return;
                            }
                            item.setId(recharge.fourCharges);
                            r.remove();
                            player.getStats().addXp(StatType.Magic, 81, true);
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    player.animate(712);
                    player.graphics(114, 96, 0);
                } else {
                    player.sendMessage("You have no jewellery that this spell can charge");
                }
            });
            return false;
        });
    }

    private enum Recharge {

        AMULET_OF_GLORY(1704, 1706, 1708, 1710, 1712),
        COMBAT_BRACELET(11126, 11124, 11122, 11120, 11118),
        SKILLS_NECKLACE(11113, 11111, 11109, 11107, 11105);

        private final int uncharged, oneCharge, twoCharges, threeCharges, fourCharges;

        Recharge(int uncharged, int oneCharge, int twoCharges, int threeCharges, int fourCharges) {
            this.uncharged = uncharged;
            this.oneCharge = oneCharge;
            this.twoCharges = twoCharges;
            this.threeCharges = threeCharges;
            this.fourCharges = fourCharges;
        }
    }

}