package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.utils.XenPost;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.handlers.TabAccountManagement;
import io.ruin.model.item.Item;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class XenUsername {

    public static void requestNameChange(Player player, String usernameRequested) {
        player.dialogue(new MessageDialogue("Processing your request, please wait...").hideContinue());
        CompletableFuture.runAsync(() -> { // PostWorker uses blocking I/O and this method is called from the main thread, so we should run it on another thread
            if (usernameRequested.length() < 3) {
                player.dialogue(new MessageDialogue("1 and 2 character names are locked behind a shop purchase.<br>If you've purchased one of these names, contact a staff member."));
                return;
            }
            if (usernameRequested.length() > 12) {
                player.dialogue(new MessageDialogue("The username you requested is too long. Please try again."));
                return;
            }
            if (usernameRequested.equalsIgnoreCase(player.getName())) {
                player.dialogue(new MessageDialogue("The username you requested is already your username."));
                return;
            }
            // check if forum acc is linked, if not, just rename character
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put("name", player.getName());
            map.put("name_requested", usernameRequested);
            String result = XenPost.post("change_name", map);
            Server.worker.execute(() -> { //we have our result (blocking part is done), so lets pass this back to the main thread so we can tell the player what happened
                if (!player.isOnline()) { // the player may have logged out while we were running our request
                    return;
                }
                if (result == null) {
                    player.dialogue(new MessageDialogue("There was an issue processing your request. Please try again later."));
                    return;
                }
                if (!result.startsWith("Success")) {
                    switch (result) {
                        case "USERNAME_TAKEN": {
                            player.dialogue(new MessageDialogue("The username you requested is already in use. Please try again."));
                            return;
                        }
                        case "USERNAME_DISALLOWED" : {
                            player.dialogue(new MessageDialogue("The username you requested contains banned words. Please try again."));
                            return;
                        }
                        case "INVALID_EMOJIS":
                        case "INVALID_CHARS": {
                            player.dialogue(new MessageDialogue("The username you requested contains incorrect characters. Please try again."));
                            return;
                        }
                    }
                    player.dialogue(new MessageDialogue("There was an unexpected response from the host. Please contact a staff member."));
                    return;
                }
                player.lock();
                Item credits = player.getInventory().findItem(13190);
                if(credits == null || credits.getAmount() < 50) {
                    player.sendMessage("You don't have enough credits to complete this transaction.");
                    player.unlock();
                    return;
                }
                player.getInventory().remove(13190, 50);
                player.dialogue(new MessageDialogue("You have successfully changed your username."));
                player.setName(usernameRequested);
                player.getPacketSender().sendAccountManagement(TabAccountManagement.getDonatorRank(player), TabAccountManagement.getUsername(player), player.getUnreadPMs());
                player.unlock();
            });
        });
    }
}
