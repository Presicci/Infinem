package io.ruin.model.content.tasksystem.areas;

import io.ruin.model.content.tasksystem.tasks.TaskArea;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.Currency;
import io.ruin.model.shop.RestockRules;
import io.ruin.model.shop.Shop;
import io.ruin.model.shop.ShopItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/23/2024
 */
public class AreaShop {

    private static void openAreaShop(Player player, TaskArea area) {
        List<ShopItem> items = new ArrayList<>();
        for (AreaShopItem item : AreaShopItem.values()) {
            if (item.getArea() != area) continue;
            if (area.hasTierUnlocked(player, item.getTier())) {
                items.add(new ShopItem(item.getItemId(), item.getStock(), item.getCost(), (int) (item.getCost() * 0.5)));
            } else {
                items.add(new ShopItem(item.getItemId(), 0, item.getCost(), (int) (item.getCost() * 0.5)));
            }
        }
        Shop shop = new Shop(area + " Unlock Shop", Currency.COINS, false, RestockRules.generateDefault(), items, true);
        shop.init();
        shop.populate();
        shop.open(player);
        player.getTaskManager().doLookupByUUID(997);    // View a Region's Unlock Shop
    }

    private static void dialogue(Player player, NPC npc, TaskArea area) {
        player.dialogue(
                new NPCDialogue(npc, "Hello there, I'm a task master for the " + area + " region. What can I help you with?"),
                new OptionsDialogue(
                        new Option("View Region Unlocks", () -> AreaReward.openRewards(player, area)),
                        new Option("Open Region Shop", () -> openAreaShop(player, area))
                )
        );
    }

    private static void registerAreaVendor(int npcId, TaskArea area) {
        NPCAction.register(npcId, "talk-to", (player, npc) -> dialogue(player, npc, area));
        NPCAction.register(npcId, "region shop", (player, npc) -> openAreaShop(player, area));
    }

    static {
        registerAreaVendor(5526, TaskArea.FREMENNIK);       // Thorodin
        registerAreaVendor(5517, TaskArea.KANDARIN);        // The 'Wedge'
        registerAreaVendor(5519, TaskArea.KANDARIN);        // Two-pints
        registerAreaVendor(5520, TaskArea.DESERT);          // Jarr
        registerAreaVendor(5524, TaskArea.ASGARNIA);        // Sir Rebral
        registerAreaVendor(7650, TaskArea.KARAMJA);         // Pirate Jackie the Fruit
        registerAreaVendor(5514, TaskArea.WILDERNESS);      // Lesser Fanatic
        registerAreaVendor(8538, TaskArea.ZEAH);            // Elise
        registerAreaVendor(5523, TaskArea.MISTHALIN);       // Hatius Cosaintus
        registerAreaVendor(5521, TaskArea.MORYTANIA);       // Le-sabre
        registerAreaVendor(5525, TaskArea.MISTHALIN);       // Toby
        registerAreaVendor(5518, TaskArea.TIRANNWN);        // Elder gnome child
    }
}
