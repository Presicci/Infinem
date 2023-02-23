package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.actions.edgeville.Bob;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.NpcID.BOB_2812;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/22/2023
 */
public class OttoGodblessed {

    public static void convertZamorakianWeapon(Player player, Item item, NPC npc) {
        if (item.getId() == 11824) {
            String currencyName = "coins";
            int price = 300000;
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Convert your " + item.getDef().name + " for " + NumberUtils.formatNumber(price) + " " + currencyName + "?", item, () -> {
                Item currency = player.getInventory().findItem(COINS_995);
                if(currency == null || currency.getAmount() < price) {
                    player.dialogue(new NPCDialogue(npc, "You don't have enough " + currencyName + " for me to convert that."));
                    return;
                }
                currency.remove(price);
                item.setId(item.getId() == 11824 ? 11889 : 11824);
                player.dialogue(new NPCDialogue(npc, "I've converted your item for you."));
            }));
        } else {
            player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "You will have to pay again if you want to turn it back into a hasta?", item, () -> {
                item.setId(item.getId() == 11824 ? 11889 : 11824);
                player.dialogue(new NPCDialogue(npc, "I've converted your item for you."));
            }));
        }
    }

    static {
        /*
         * Zamorakian spear/hasta conversion
         */
        for(int id : new int[]{11824, 11889})
            ItemNPCAction.register(id, 2914, OttoGodblessed::convertZamorakianWeapon);
    }
}
