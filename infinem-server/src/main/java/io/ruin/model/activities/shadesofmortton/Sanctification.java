package io.ruin.model.activities.shadesofmortton;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.skills.RandomEvent;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/20/2024
 */
@AllArgsConstructor
public enum Sanctification {
    SACRED_OIL_1(Items.OLIVE_OIL_1, Items.SACRED_OIL_1, 0, 1),
    SACRED_OIL_2(Items.OLIVE_OIL_2, Items.SACRED_OIL_2, 0, 2),
    SACRED_OIL_3(Items.OLIVE_OIL_3, Items.SACRED_OIL_3, 0, 3),
    SACRED_OIL_4(Items.OLIVE_OIL_4, Items.SACRED_OIL_4, 0, 4),
    SERUM_208_1(Items.SERUM_207_1, Items.SERUM_208_1, 15, 1),
    SERUM_208_2(Items.SERUM_207_2, Items.SERUM_208_2, 15, 2),
    SERUM_208_3(Items.SERUM_207_3, Items.SERUM_208_3, 15, 3),
    SERUM_208_4(Items.SERUM_207_4, Items.SERUM_208_4, 15, 4);

    private final int reqId, productId, herbloreLevel, sanctityCost;

    private void sanctify(Player player, int amt) {
        if (player.getAttributeIntOrZero(TempleRepairing.SANCTITY_KEY) < 10) {
            player.sendMessage("You need at least 10% sanctity to sanctify that.");
            return;
        }
        if (herbloreLevel > 0 && !player.getStats().check(StatType.Herblore, herbloreLevel, "sanctify that")) {
            return;
        }
        RandomEvent.attemptTrigger(player);
        player.startEvent(e -> {
            int amount = amt;
            while (amount-- > 0) {
                Item reagent = player.getInventory().findItem(reqId);
                if (reagent == null) {
                    player.sendMessage("You've run out of " + ItemDefinition.get(reqId).name + ".");
                    break;
                }
                if (player.getAttributeIntOrZero(TempleRepairing.SANCTITY_KEY) < 10) {
                    player.sendMessage("You need at least 10% sanctity to sanctify that.");
                    return;
                }
                player.animate(832);
                player.incrementNumericAttribute(TempleRepairing.SANCTITY_KEY, -sanctityCost);
                player.getPacketSender().sendVarp(345, player.getAttributeIntOrZero(TempleRepairing.SANCTITY_KEY));
                reagent.setId(productId);
                e.delay(4);
            }
        });
    }

    private static void use(Player player) {
        List<SkillItem> skillItems = new ArrayList<>();
        for (Sanctification sanc : values()) {
            if (player.getInventory().hasId(sanc.reqId)) {
                skillItems.add(new SkillItem(sanc.productId).addAction((p, i, event) -> sanc.sanctify(p, i)));
            }
        }
        SkillDialogue.make(player, skillItems.toArray(new SkillItem[0]));
    }

    static {
        ItemObjectAction.register(4090, (player, item, obj) -> use(player));
    }
}
