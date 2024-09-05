package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.wilderness.SkullingItem;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;
import io.ruin.model.map.Bounds;
import io.ruin.model.shop.Shop;
import io.ruin.model.shop.ShopItem;
import io.ruin.model.shop.ShopManager;

import static io.ruin.cache.ItemID.*;

public class EmblemTrader {

    static {
        //Wilderness Emblem Trader
        NPCAction.register(7943, "talk-to", (player, npc) -> {
            String currencyName = "coins";
            player.dialogue(new NPCDialogue(npc, "If you find an ancient emblem, totem, or statuette, use it on me and I'll exchange it for " + currencyName + "."));
        });
        int[][] ancientArtifacts = {
                {21807, 500_000},   //Emblem
                {21810, 1_000_000},  //Totem
                {21813, 2_000_000},  //Statuette
                {22299, 4_000_000},  //Medallion
                {22302, 8_000_000},  //Effigy
                {22305, 16_000_000}  //Relic
        };
        for (int[] artifact : ancientArtifacts) {
            int id = artifact[0];
            ItemDefinition.get(id).sigmundBuyPrice = artifact[1];
            ItemNPCAction.register(id, 7943, (player, item, npc) -> player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sell your " + ItemDefinition.get(id).name +
                    " for " + artifact[1] + " coins?", item, () -> {
                item.remove();
                player.getInventory().add(COINS_995, artifact[1]);
                player.dialogue(new NPCDialogue(npc, "Excellent find, " + player.getName() + "! If you find anymore artifacts you know where to find me!"));
            })));
        }
        //Edgeville Emblem Trader
        NPCAction.register(308, "talk-to", (player, npc) -> {
            String currencyName = "coins";
            player.dialogue(new NPCDialogue(npc, "If you find an ancient emblem, totem, or statuette, use it on me in revenant caves and I'll exchange it for " + currencyName + "."));
        });
        NPCAction.register(308, "skull", (player, npc) -> skull(player));
        SpawnListener.forEach(npc -> {
            if (npc.getId() == 308 && npc.walkBounds != null)
                npc.walkBounds = new Bounds(3099, 3518, 3092, 3516, 0);
        });


    }

    public static void skull(Player player) {
        player.dialogue(new OptionsDialogue(
                new Option("<img=46> Regular <img=46>", () -> player.getCombat().skullNormal(3000)),
                new Option("No Skull", () -> {
                    if (SkullingItem.canSkullDeplete(player))
                        player.getCombat().resetSkull();
                })
        ));
    }
}
