package io.ruin.data.impl.npcs;

import com.google.api.client.util.Lists;
import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.NPCDef;
import io.ruin.data.DataFile;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.*;
import io.ruin.process.event.Event;
import io.ruin.process.event.EventConsumer;
import io.ruin.process.event.EventType;
import io.ruin.process.event.EventWorker;
import io.ruin.process.task.TaskWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 5/24/2021
 */
public class npc_shops extends DataFile {
    @Override
    public String path() {
        return "shops/json/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        TempShop s = JsonUtils.fromJson(json, TempShop.class);
        List<ShopItem> items = new ArrayList<>();
        for (TempItem i : s.items)
            items.add(new ShopItem(i.itemId, i.stock, i.sell_price, i.buy_price));
        Shop shop = new Shop(s.name, s.currency, s.generalStore, RestockRules.generateDefault(), items, s.ironman);
        allShops.add(shop);
        for (int n : s.npcs) {
            if(s.uniquePerNpc){
                shop = new Shop(s.name, s.currency, s.generalStore, RestockRules.generateDefault(), items, s.ironman);
            }
            Shop fShop = shop;
            NPCDef npcDef = NPCDef.get(n);

            if(npcDef.shops == null){
                npcDef.shops = Lists.newArrayList();
            }
            npcDef.shops.add(shop);

            shop.init();
            shop.populate();

            EventConsumer eventConsumer = event -> ShopManager.shopTick(event, fShop);
            Event event = EventWorker.startEvent(eventConsumer);
            event.eventType = EventType.PERSISTENT;

            int talkToOption = npcDef.getOption("Talk-to");
            if (talkToOption < 0)
                talkToOption = npcDef.getOption("Talk");
            if (talkToOption > 0)
                NPCAction.register(n, talkToOption, (player, npc) -> talkToDialogue(player, npc, fShop));

            String npcOption = s.npcOption != null ? s.npcOption : "Trade";
            int tradeOption = npcDef.getOption(npcOption);

            if (tradeOption > 0) {
                NPCAction.register(n, tradeOption, (player, npc) -> {
                    fShop.open(player);
                });
            }
        }

        return s;
    }

    public static List<Shop> allShops = Lists.newArrayList();

    private static final void talkToDialogue(Player player, NPC npc, Shop shop) {
        player.dialogue(
                new NPCDialogue(npc, "Do you need something wanderer?"),
                new OptionsDialogue(
                        new Option("Yes can you show me what you are selling?", () -> {
                            shop.open(player);
                        }),
                        new Option("No thanks.")));
    }

    private static final class TempShop {
        @Expose
        String name;
        @Expose
        Currency currency;
        @Expose
        boolean ironman = true;
        @Expose
        boolean generalStore;
        @Expose
        TempItem[] items;
        @Expose
        private int[] npcs;
        @Expose
        private String npcOption;
        @Expose
        private int sellMultiplier;
        @Expose
        private int buyMultiplier;
        @Expose
        private boolean hideBuy;
        @Expose
        private boolean uniquePerNpc;
    }

    private static final class TempItem {
        @Expose
        int itemId;
        @Expose
        int stock;
        @Expose
        int buy_price;
        @Expose
        int sell_price;
    }
}
