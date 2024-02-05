package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.degradeable.BarrowsDegradeable;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.NpcID.BOB_2812;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/22/2023
 */
public class RepairNPC {

    public static Map<Integer, Integer> REPAIR_COSTS = new HashMap<>();

    private static void repairAll(Player player, NPC npc) {
        int totalPrice = 0;
        ArrayList<Item> brokenItems = new ArrayList<>();
        Set<Integer> repairableItems = REPAIR_COSTS.keySet();
        for(Item item : player.getInventory().getItems()) {
            if(item == null)
                continue;
            if (!repairableItems.contains(item.getId())) {
                continue;
            }
            int cost = REPAIR_COSTS.get(item.getId());
            if (item.getCharges() > 0)
                cost = (int) (cost * (1D - ((double) item.getCharges() / BarrowsDegradeable.MAX_CHARGES)));
            totalPrice += cost;
            brokenItems.add(item);
        }
        if(brokenItems.isEmpty()) {
            player.dialogue(new NPCDialogue(npc, "You don't appear to have any repairable items, " + player.getName() + ". I'll be happy to repair them once you do!"));
            return;
        }
        int currencyId = COINS_995;
        int finalTotalPrice = totalPrice;
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Repair all your items for " + NumberUtils.formatNumber(totalPrice) + " coins?", 2347, 1, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < finalTotalPrice) {
                currency = player.getBank().findItem(currencyId);
                if (currency == null || currency.getAmount() < finalTotalPrice) {
                    player.dialogue(new NPCDialogue(npc, "You don't have enough coins for me to repair all your items. Use an item on me to repair them separately."));
                    return;
                }
            }
            currency.remove(finalTotalPrice);
            for(Item item : brokenItems)
                item.setId(item.getDef().brokenFrom.fixedId);
            player.dialogue(new NPCDialogue(npc, "I've repaired all your items for you."));
        }));
    }

    public static void repairItem(Player player, Item item, int price, int fixedId) {
        int currencyId = COINS_995;
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Fix your " + item.getDef().name + " for " + NumberUtils.formatNumber(price) + " coins?", item, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < price) {
                currency = player.getBank().findItem(currencyId);
                if (currency == null || currency.getAmount() < price) {
                    player.dialogue(new MessageDialogue("You don't have enough coins to repair that."));
                    return;
                }
            }
            currency.remove(price);
            item.setId(fixedId);
            player.dialogue(new MessageDialogue("You have repaired your " + item.getDef().name + "."));
        }));
    }

    public static final int[] REPAIR_NPCS = {
            10619,  // Bob
            4105,   // Dunstan
            1358,   // Tindel merchant
            1766,   // Squire
            9157,   // Aneirin
            2882,   // Horvik
    };

    static {
        for (int npcId : REPAIR_NPCS) {
            NPCAction.register(npcId, "repair", RepairNPC::repairAll);
        }
    }
}
