package io.ruin.model.activities.combat.barrows;

import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.Items;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.ruin.cache.ItemID.COINS_995;

public class BarrowsRewards {

    /**
     * This table is pretty much designed for stackable loots only.
     * (Amounts are modified within roll based on kill count)
     */
    public static final LootTable MAIN_TABLE = new LootTable()
            .addTable(1,
                    new LootItem(995, 700, 800, 380),
                    new LootItem(Items.MIND_RUNE, 253, 336, 125),
                    new LootItem(Items.CHAOS_RUNE, 112, 139, 125),
                    new LootItem(Items.DEATH_RUNE, 70, 83, 125),
                    new LootItem(Items.BLOOD_RUNE, 37, 43, 125),
                    new LootItem(Items.BOLT_RACK, 35, 40, 125),
                    new LootItem(Items.LOOP_HALF_OF_KEY, 1, 1, 3),
                    new LootItem(Items.TOOTH_HALF_OF_KEY, 1, 1, 3),
                    new LootItem(Items.DRAGON_MED_HELM, 1, 1, 1)
            );

    /**
     * This table rolls alongside the main table for each kill.
     * (Only one clue can be rolled per chest loot!)
     */
    public static final LootTable TERTIARY_TABLE = new LootTable()
            .addTable(1,
                    new LootItem(-1, 1, 199),   // nothing
                    new LootItem(24365, 1, 1)   // clue scroll (elite)
            );

    /**
     * Looting - Read the comments above if you plan on changing anything! :)
     */
    public static ItemContainer loot(Player player) {
        double brothersKilled = 0;
        List<Integer> barrowsIds = new ArrayList<>();
        for(BarrowsBrother b : BarrowsBrother.values()) {
           if(b.isKilled(player)) {
                brothersKilled++;
                Collections.addAll(barrowsIds, b.pieces);
           }
        }
        Collections.shuffle(barrowsIds);

        double barrowsChance = 1D / (450D - (58D * brothersKilled));    // OSRS correct formula
        double customBarrowsChance = barrowsChance *  2;
        boolean rolledClue = false;

        ItemContainer container = new ItemContainer();
        container.init(player, 6, -1, 64161, 141, false);
        container.sendAll = true;

        for(int i = 0; i < brothersKilled; i++) {
            if(Random.get() > customBarrowsChance) {
                /*
                 * Misc loot
                 */
                Item item;
                if(!rolledClue && (item = TERTIARY_TABLE.rollItem()) != null) {
                    rolledClue = true;
                    container.add(item);
                }
                item = MAIN_TABLE.rollItem();
                int amount = item.getAmount();
                if(brothersKilled < 6) //if player didn't kill all 6 brothers, reduce the amount!
                    amount *= (brothersKilled / 6);
                item.setAmount(amount);
                container.add(item);
            } else {
                /**
                 * Barrows loot
                 */
                ItemDef def = ItemDef.get(barrowsIds.remove(0));
               // Broadcast.FRIENDS.sendNews(player, player.getName() + " just received " + def.descriptiveName + " from the Barrows chest!");
                container.add(def.id, 1);
            }
        }

        return container;
    }

}
