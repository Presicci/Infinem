package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.AttributeKey;
import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemNPCAction;

import static io.ruin.cache.ItemID.COINS_995;

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

    private static void dialogue(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "What can I help ya with?"),
                new OptionsDialogue(
                        new Option((player.hasAttribute(AttributeKey.BREAK_VIALS) ? "Disable" : "Enable") + " vial smashing", () -> {
                            if (player.hasAttribute(AttributeKey.BREAK_VIALS)) {
                                player.removeAttribute(AttributeKey.BREAK_VIALS);
                            } else {
                                player.putAttribute(AttributeKey.BREAK_VIALS, 1);
                            }
                        }),
                        new Option("View shop", () -> npc.openShop(player))
                )
        );
    }

    static {
        /*
         * Zamorakian spear/hasta conversion
         */
        for(int id : new int[]{11824, 11889})
            ItemNPCAction.register(id, 2914, OttoGodblessed::convertZamorakianWeapon);

        NPCAction.register(2914, "talk-to", OttoGodblessed::dialogue);
    }
}
