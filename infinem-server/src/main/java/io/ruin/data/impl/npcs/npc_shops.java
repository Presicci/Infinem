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
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.*;
import io.ruin.model.shop.Currency;
import io.ruin.process.event.Event;
import io.ruin.process.event.EventConsumer;
import io.ruin.process.event.EventType;
import io.ruin.process.event.EventWorker;

import java.util.*;
import java.util.stream.Collectors;

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
        List<Integer> npcs = Arrays.stream(s.npcs).boxed().collect(Collectors.toList());
        for (int n : s.npcs) {
            NPCDef def = NPCDef.get(n);
            if ((def.varpbitId != -1 || def.varpId != -1) && def.showIds.length > 0) {
                npcs.addAll(Arrays.stream(def.showIds).filter(e -> e != -1).boxed().collect(Collectors.toList()));
            }
        }
        for (int n : npcs) {
            if(s.uniquePerNpc){
                shop = new Shop(s.name, s.currency, s.generalStore, RestockRules.generateDefault(), items, s.ironman);
            }
            Shop fShop = shop;
            NPCDef npcDef = NPCDef.get(n);

            if(npcDef.shops == null){
                npcDef.shops = Lists.newArrayList();
            }
            npcDef.shops.add(shop);
            shop.startup();
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
            } else {
                tradeOption = npcDef.getOption("Shop");
                if (tradeOption > 0) {
                    NPCAction.register(n, tradeOption, (player, npc) -> {
                        fShop.open(player);
                    });
                } else {
                    NPCAction.registerIncludeVariants(n, "Trade", ((player, npc) -> {
                        fShop.open(player);
                    }));
                }
            }
        }
        if (s.uuid >= 0) {
            if (s.npcs.length == 0)
                shop.startup();
            Shop.shops.put(s.uuid, shop);
        }
        return s;
    }

    private static void talkToDialogue(Player player, NPC npc, Shop shop) {
        npc.faceTemp(player);
        player.dialogue(
                new NPCDialogue(npc, "Hello, would you like to browse my shop?"),
                new OptionsDialogue(
                        new Option("Yes please. What are you selling?", () -> {
                            shop.open(player);
                        }),
                        new Option("No thanks.", () -> player.dialogue(new PlayerDialogue("No thanks.")))));
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
        private int[] npcs = {};
        @Expose
        private int uuid = -1;
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
