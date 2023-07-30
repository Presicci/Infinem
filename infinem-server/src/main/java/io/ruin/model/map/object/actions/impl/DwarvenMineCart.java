package io.ruin.model.map.object.actions.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/28/2023
 */
public class DwarvenMineCart {

    private static final int COST = 10;
    private static final int[] ORES = {
            Items.COAL, Items.COPPER_ORE, Items.TIN_ORE, Items.IRON_ORE, Items.GOLD_ORE,
            Items.SILVER_ORE, Items.MITHRIL_ORE, Items.ADAMANTITE_ORE, Items.CLAY
    };

    static {
        ObjectAction.register(6045, "search", ((player, obj) ->
                player.dialogue(new MessageDialogue("The dwarves will take your ore to the bank for you.<br>They charge " + COST + "gp per deposit."))));
        for (int ore : ORES) {
            ItemObjectAction.register(ore, 6045, (((player, item, obj) -> {
                int bankAmt = player.getBank().getAmount(ore);
                int amt = Math.min(Integer.MAX_VALUE - bankAmt, player.getInventory().getAmount(ore));
                String oreName = ItemDef.get(ore).name;
                if (amt <= 0) {
                    player.dialogue(new MessageDialogue("Your bank has too much " + oreName + "!"));
                    return;
                }
                int coins = player.getInventory().getAmount(995);
                if (coins < COST) {
                    player.dialogue(new MessageDialogue("You need to pay " + COST + "gp per deposit."));
                    return;
                }
                int finalAmt = Math.min(amt, coins/10);
                player.startEvent(e -> {
                    player.lock();
                    player.animate(832);
                    e.delay(1);
                    player.getInventory().remove(ore, finalAmt);
                    player.getInventory().remove(995, finalAmt*10);
                    player.getBank().add(ore, finalAmt);
                    player.dialogue(new ItemDialogue().one(ore, "You deposit " + finalAmt + " " + oreName + ".<br>Your bank now has " + (bankAmt + amt) + " " + oreName + " stored."));
                    player.getTaskManager().doLookupByUUID(915, 1); // Bank Some Ore at a Mine Cart in the Dwarven Mine
                    player.unlock();
                });
            })));
        }
    }
}
