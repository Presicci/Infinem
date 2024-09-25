package io.ruin.model.activities.shadesofmortton;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/21/2024
 */
@Getter
@AllArgsConstructor
public enum PyreLog {
    REGULAR(5, 10, 50, 2, Items.LOGS, Items.PYRE_LOGS),
    OAK(20, 10, 70, 2, Items.OAK_LOGS, Items.OAK_PYRE_LOGS),
    WILLOW(35, 15, 100, 3, Items.WILLOW_LOGS, Items.WILLOW_PYRE_LOGS),
    TEAK(40, 15, 120, 3, Items.TEAK_LOGS, Items.TEAK_PYRE_LOGS),
    ARCTIC_PINE(47, 10, 158, 2, Items.ARCTIC_PINE_LOGS, Items.ARCTIC_PYRE_LOGS),
    MAPLE(50, 15, 175, 3, Items.MAPLE_LOGS, Items.MAPLE_PYRE_LOGS),
    MAHOGANY(55, 15, 210, 3, Items.MAHOGANY_LOGS, Items.MAHOGANY_PYRE_LOG),
    YEW(65, 20, 255, 4, Items.YEW_LOGS, Items.YEW_PYRE_LOGS),
    MAGIC(80, 20, 404.5, 4, Items.MAGIC_LOGS, Items.MAGIC_PYRE_LOGS),
    REDWOOD(95, 20, 500, 4, Items.REDWOOD_LOGS, Items.REDWOOD_PYRE_LOGS);

    private final int level;
    private final double experience, cremationExperience;
    private final int dosesRequired, logId, pyreLogId;

    private void oil(Player player, int amt) {
        if (!player.getStats().check(StatType.Firemaking, level, "oil that log")) {
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(e -> {
            int amount = amt;
            while (amount-- > 0) {
                Item log = player.getInventory().findItem(logId);
                if (log == null) {
                    player.sendMessage("You've run out of logs.");
                    return;
                }
                if (!hasOil(player)) {
                    player.sendMessage("You've run out of oil.");
                    return;
                }
                log.setId(pyreLogId);
                removeOil(player);
                player.getStats().addXp(StatType.Firemaking, experience, true);
                e.delay(2);
            }
        });
    }

    private static final int[] OILS = { Items.SACRED_OIL_1, Items.SACRED_OIL_2, Items.SACRED_OIL_3, Items.SACRED_OIL_4 };

    private void removeOil(Player player) {
        int doses = 0;
        for (int index = 0; index < OILS.length; index++) {
            int oilDose = index + 1;
            List<Item> oils = player.getInventory().collectItems(OILS[index]);
            if (oils == null) continue;
            for (Item oil : oils) {
                if (doses + oilDose > dosesRequired) {
                    int dosesToRemove = dosesRequired - doses;
                    if (index - dosesToRemove < 0) {
                        oil.setId(Items.VIAL);  // Dunno what happened to get here
                    } else {
                        oil.setId(OILS[index - dosesToRemove]);
                    }
                    return;
                } else {
                    doses += oilDose;
                    oil.setId(Items.VIAL);
                }
                if (doses >= dosesRequired) return;
            }
        }
    }

    private boolean hasOil(Player player) {
        int doses = 0;
        for (int index = 0; index < OILS.length; index++) {
            doses += (player.getInventory().getAmount(OILS[index]) * (index + 1));
            if (doses >= dosesRequired) return true;
        }
        return false;
    }

    static {
        for (PyreLog log : values()) {
            for (int oil : OILS) {
                ItemItemAction.register(oil, log.logId, (player, primary, secondary) -> {
                    List<SkillItem> skillItems = new ArrayList<>();
                    for (PyreLog l : values()) {
                        if (player.getInventory().hasId(l.logId)) skillItems.add(new SkillItem(l.pyreLogId).addAction((p, integer, event) -> l.oil(p, integer)));
                    }
                    SkillDialogue.make(player, skillItems.toArray(new SkillItem[0]));
                });
            }
        }
    }
}
