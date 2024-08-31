package io.ruin.services;

import io.ruin.Server;
import io.ruin.api.utils.XenPost;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Votes {

    public static void claim(Player player, NPC npc) {
        if (player.getInventory().isFull() && !player.getInventory().contains(32029)) {
            if (npc != null) {
                player.dialogue(new NPCDialogue(npc, "You need at least 1 inventory slot to claim your voting rewards."));
            } else {
                player.dialogue(new MessageDialogue("You need at least 1 inventory slot to claim your voting rewards."));
            }
            return;
        }
        if (npc != null) {
            player.dialogue(new NPCDialogue(npc, "Attempting to claim vote tickets, please wait...").hideContinue());
        } else {
            player.dialogue(new MessageDialogue("Attempting to claim vote tickets, please wait...").hideContinue());
        }
        CompletableFuture.runAsync(() -> {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("username", player.getName());
            String result = XenPost.post("claim_votes", map);
            Server.worker.execute(() -> {
                if (Character.isDigit(result.charAt(0)) || result.charAt(0) == '-') {
                    if (result.charAt(0) == '-') {
                        if (npc != null) {
                            player.dialogue(new NPCDialogue(npc, "You don't have any votes to claim. Type ::vote to vote."));
                        } else {
                            player.dialogue(new MessageDialogue("You don't have any votes to claim. Type ::vote to vote."));
                        }
                    } else {
                        try {
                            int amt = Integer.parseInt(result);
                            if (amt < 1) {
                                if (npc != null) {
                                    player.dialogue(new NPCDialogue(npc, "You don't have any votes to claim. Type ::vote to vote."));
                                } else {
                                    player.dialogue(new MessageDialogue("You don't have any votes to claim. Type ::vote to vote."));
                                }
                            } else {
                                player.getInventory().add(32029, amt);
                                if (npc != null) {
                                    player.dialogue(new NPCDialogue(npc, "You've claimed " + amt + " vote" + (amt > 1 ? "s" : "") + "! Thank you for voting for the server!"));
                                } else {
                                    player.dialogue(new MessageDialogue("You've claimed " + amt + " vote" + (amt > 1 ? "s" : "") + "! Thank you for voting for the server!"));
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.err.println(player.getName() + " had an error claiming their vote tickets, output: " + result + " | " + e.getMessage());
                        }
                    }
                } else {
                    if (npc != null) {
                        player.dialogue(new NPCDialogue(npc, "Something went wrong, please try again later."));
                    } else {
                        player.dialogue(new MessageDialogue("Something went wrong, please try again later."));
                    }
                }
            });
        });
    }

}
