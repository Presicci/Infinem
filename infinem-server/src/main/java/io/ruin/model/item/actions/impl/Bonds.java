package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.services.Store;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 7/20/2020
 */
public class Bonds {

    private static void redeem(Player player, int amount, Item item) {
        player.dialogue(new ItemDialogue().one(item.getId(), "You are about to redeem this bond<br>" +
                "adding "+amount+" to your purchased credits.<br>" +
                "This will consume the bond forever."),
        new OptionsDialogue(
                new Option("Yes!", (player1 -> {
                    player.getInventory().remove(item);
                    player.storeAmountSpent += amount;
                    player.sendMessage("You have redeemed a $" + (amount / 10) + " Bond. Your new total credits: "+player.storeAmountSpent);
                    PlayerGroup group = Store.getGroup(player);
                    if(group != null && !player.isGroup(group)) {
                        player.join(group);
                        group.sync(player);
                        player.sendMessage("Congratulations, you've unlocked a new donator rank: <img=" + group.clientImgId + ">");
                    }
                })),
                new Option("I'll keep it for now.", player::closeDialogue)
        ));
    }

    static {
        ItemAction.registerInventory(32033, 1, ((player, item) -> redeem(player, 50, item)));
        ItemAction.registerInventory(32035, 1, ((player, item) -> redeem(player, 100, item)));
        ItemAction.registerInventory(32037, 1, ((player, item) -> redeem(player, 250, item)));
    }
}
