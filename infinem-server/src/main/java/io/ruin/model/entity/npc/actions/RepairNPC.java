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
import io.ruin.model.stat.StatType;

import java.util.ArrayList;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.NpcID.BOB_2812;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/22/2023
 */
public class RepairNPC {

    private static void repairAll(Player player, NPC npc) {
        int totalPrice = 0;
        ArrayList<Item> brokenItems = new ArrayList<>();
        for(Item item : player.getInventory().getItems()) {
            if(item == null)
                continue;
            ItemBreaking brokenItem = item.getDef().brokenFrom;
            if(brokenItem == null)
                continue;
            totalPrice += brokenItem.coinRepairCost;
            brokenItems.add(item);
        }
        if(brokenItems.isEmpty()) {
            player.dialogue(new NPCDialogue(npc, "You don't appear to have any broken items, " + player.getName() + ". But, I'll be happy to repair them once you do!"));
            return;
        }
        int currencyId;
        String currencyName;
        int price;
        currencyId = COINS_995;
        currencyName = "coins";
        price = coinPrice(player, totalPrice);

        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Repair all your items for " + NumberUtils.formatNumber(price) + " " + currencyName + "?", 2347, 1, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < price) {
                currency = player.getBank().findItem(currencyId);
                if (currency == null || currency.getAmount() < price) {
                    player.dialogue(new NPCDialogue(npc, "You don't have enough " + currencyName + " for me to repair all your broken items. Use an item on me to repair them separately."));
                    return;
                }
            }
            currency.remove(price);
            for(Item item : brokenItems)
                item.setId(item.getDef().brokenFrom.fixedId);
            player.dialogue(new NPCDialogue(npc, "I've repaired all your items for you."));
        }));
    }

    private static void repairItem(Player player, Item item, ItemBreaking brokenItem) {
        int currencyId;
        String currencyName;
        int price;
        currencyId = COINS_995;
        currencyName = "coins";
        price = coinPrice(player, brokenItem.coinRepairCost);

        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Fix your " + item.getDef().name + " for " + NumberUtils.formatNumber(price) + " " + currencyName + "?", item, () -> {
            Item currency = player.getInventory().findItem(currencyId);
            if(currency == null || currency.getAmount() < price) {
                currency = player.getBank().findItem(currencyId);
                if (currency == null || currency.getAmount() < price) {
                    player.dialogue(new MessageDialogue("You don't have enough " + currencyName + " to repair that."));
                    return;
                }
            }
            currency.remove(price);
            item.setId(brokenItem.fixedId);
            player.dialogue(new MessageDialogue("You have repaired your " + item.getDef().name + "."));
        }));
    }

    private static int coinPrice(Player player, int basePrice) {
        double smithingLevel = player.getStats().get(StatType.Smithing).fixedLevel;
        double smithingMultiplier = 1D - (smithingLevel / 200D);
        return (int) (smithingMultiplier * basePrice);
    }

    private static final int[] REPAIR_NPCS = { BOB_2812 };

    static {
        for (int npcId : REPAIR_NPCS) {
            NPCAction.register(npcId, "repair", RepairNPC::repairAll);
        }
        for(ItemBreaking brokenItem : ItemBreaking.values()) {
            ItemAction.registerInventory(brokenItem.brokenId, "fix", (player, item) -> repairItem(player, item, brokenItem));
            ItemItemAction.register(COINS_995, brokenItem.brokenId, (player, primary, secondary) -> repairItem(player, secondary, brokenItem));
            for (int npcId : REPAIR_NPCS) {
                ItemNPCAction.register(brokenItem.brokenId, npcId, (player, item, npc) -> repairItem(player, item, brokenItem));
            }
        }
    }
}
