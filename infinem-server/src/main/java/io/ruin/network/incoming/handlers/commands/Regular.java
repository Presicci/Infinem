package io.ruin.network.incoming.handlers.commands;

import io.ruin.model.map.object.actions.impl.locations.prifddinas.PrifCityEntrance;
import io.ruin.services.Votes;
import io.ruin.utility.Color;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.edgeville.EmblemTrader;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.player.Title;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.network.incoming.handlers.CommandHandler;
import io.ruin.services.Loggers;
import io.ruin.services.Punishment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/27/2021
 */
public class Regular {

    public static boolean handleRegular(Player player, String query, String command, String[] args) {

        switch (command) {

            case "commands": {
                player.sendScroll("<col=800000>Commands</col>",
                        "<col=800000>Misc Commands:</col>",
                        "::yell", "::skull", "::prif",
                        "",
                        "<col=800000>Website Commands:</col>",
                        "::store", "::vote", "::guides", "::support", "::forums", "::scores", "::discord"
                );
                return true;
            }

            case "clear":
            case "empty": {
                player.dialogue(
                        new YesNoDialogue("Are you sure you want to destroy everything in your inventory?",
                                "This cannot be undone!", 13195, 1, () -> {
                            player.getInventory().clear();
                        })
                );
                return true;
            }


            case "players": {
                int players = World.players.count();
                player.sendMessage("There is currently " + players + " player" + (players > 1 ? "s" : "") + " online!");
                if (player.isStaff()) {
                    List<String> names = new LinkedList<>();
                    World.players.forEach(p -> {
                        names.add(p.getName());
                    });
                    player.sendScroll("Players Online", names.toArray(new String[0]));
                }
                return true;
            }
            case "dz":
            case "donatorzone":
            case "dzone":
            case "donatorszone":
            case "donorzone":
                player.dialogue(new NPCDialogue(2108, "The donator zone is not available yet!"));
                break;

            case "skull": {
                if (!player.getCombat().isDead())
                    EmblemTrader.skull(player);
                return true;
            }

            case "yell": {
                boolean shadow = false;
                if (Punishment.isMuted(player)) {
                    if (!player.shadowMute) {
                        player.sendMessage("You're muted and can't talk.");
                        return true;
                    }
                    shadow = true;
                }
                String message;
                if (query.length() < 5 || (message = query.substring(5).trim()).isEmpty()) {
                    player.sendMessage("You can't yell an empty message.");
                    return true;
                }
                if (message.contains("<col=") || message.contains("<img=")) {
                    player.sendMessage("You can't use color or image tags inside your yell!");
                    return true;
                }
                long ms = System.currentTimeMillis(); //ew oh well
                long delay = player.yellDelay - ms;
                if (delay > 0) {
                    long seconds = delay / 1000L;
                    if (seconds <= 1)
                        player.sendMessage("You need to wait 1 more second before yelling again.");
                    else
                        player.sendMessage("You need to wait " + seconds + " more seconds before yelling again.");
                    return true;
                }
                boolean bypassFilter; //basically disallows players to filter staff yells.
                int delaySeconds; //be sure this is set in ascending order.
                if (player.isAdmin() || player.isSupport() || player.isModerator()) {
                    bypassFilter = true;
                    delaySeconds = 0;
                } else if (player.isGroup(PlayerGroup.ZENYTE)) {
                    bypassFilter = false;
                    delaySeconds = 0;
                } else if (player.isGroup(PlayerGroup.ONYX) || player.isGroup(PlayerGroup.YOUTUBER) || player.isGroup(PlayerGroup.BETA_TESTER)) {
                    bypassFilter = false;
                    delaySeconds = 5;
                } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
                    bypassFilter = false;
                    delaySeconds = 10;
                } else if (player.isGroup(PlayerGroup.DIAMOND)) {
                    bypassFilter = false;
                    delaySeconds = 15;
                } else if (player.isGroup(PlayerGroup.RUBY)) {
                    bypassFilter = false;
                    delaySeconds = 30;
                } else if (player.isGroup(PlayerGroup.EMERALD)) {
                    bypassFilter = false;
                    delaySeconds = 45;
                } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
                    bypassFilter = false;
                    delaySeconds = 60;
                } else {
                    Help.open(player, "yell");
                    return true;
                }

                PlayerGroup clientGroup = player.getClientGroup();
                String title = "";
                if (player.titleId != -1 && player.titleId < Title.PRESET_TITLES.length) { //normal titles
                    title = Title.PRESET_TITLES[player.titleId].getPrefix();
                    if (player.titleId == 22) { //custom title
                        title = player.customTitle;
                    }
                }

                message = Color.BLUE.wrap("[" + (clientGroup.clientImgId != -1 ? clientGroup.tag() : "") + title) + Color.BLUE.wrap(player.getName() + "]") + " " + message;

                player.yellDelay = ms + (delaySeconds * 1000L);
                if (shadow) {
                    player.sendMessage(message);
                    return true;
                }

                for (Player p : World.players) {
                    if (!bypassFilter && p.yellFilter && p.getUserId() != player.getUserId())
                        continue;
                    p.sendMessage(message);
                }
                player.sentMessages.add(message);
                if (player.sentMessages.size() > 20) player.sentMessages.poll();
                Loggers.logYell(player.getUserId(), player.getName(), player.getIp(), message);
                return true;
            }
            case "staff":
            case "staffonline": {
                List<String> text = new LinkedList<>();
                List<String> admins = new LinkedList<>();
                List<String> mods = new LinkedList<>();
                List<String> slaves = new LinkedList<>();
                World.players.forEach(p -> {
                    if (p.isAdmin()) admins.add(p.getName());
                    else if (p.isModerator()) mods.add(p.getName());
                    else if (p.isSupport()) slaves.add(p.getName());
                });

                text.add("<img=1><col=bbbb00><shad=0000000> Administrators</col></shad>");
                if (admins.size() == 0) text.add("None online!");
                else text.addAll(admins);
                text.add("");

                text.add("<img=0><col=b2b2b2><shad=0000000> Moderators<col></shad>");
                if (mods.size() == 0) text.add("None online!");
                else text.addAll(mods);
                text.add("");

                text.add("<img=15><col=5bccc4><shad=0000000> Server Supports</col></shad>");
                if (slaves.size() == 0) text.add("None online!");
                else text.addAll(slaves);

                player.sendScroll("Staff Online", text.toArray(new String[0]));
                return true;
            }
            /**
             * Website commands
             */
            case "store": {
                player.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "/store");
                return true;
            }
            case "updates": {
                player.openUrl(World.type.getWorldName() + " Updates", "https://www.infinem.net/forums/viewforum.php?f=5");
                return true;
            }
            case "rules": {
                player.openUrl(World.type.getWorldName() + " Rules", "https://www.infinem.net/forums/viewforum.php?f=11&sid=17653f2af30834fc3e1a3302b92fbc39");
                return true;
            }
            case "vote": {
                player.openUrl(World.type.getWorldName() + " Voting", World.type.getWebsiteUrl() + "/vote");
                return true;
            }
            case "guides": {
                player.openUrl(World.type.getWorldName() + " Guides", "https://www.infinem.net/forums/viewforum.php?f=9");
                return true;
            }
            case "forums": {
                player.openUrl(World.type.getWorldName() + " Forums", "https://www.infinem.net/forums/");
                return true;
            }
            case "hiscores":
            case "scores": {
                player.openUrl("https://www.infinem.net/highscores/");
                return true;
            }
            case "discord": {
                player.openUrl("Official " + World.type.getWorldName() + " Discord Server", "https://discord.gg/2j2rEnjVJg");
                return true;
            }
            case "claimvotes":
            case "claimvote": {
                Votes.claim(player, null);
                return true;
            }
            case "prifcheck":
            case "priffcheck":
            case "priff":
            case "prif": {
                if (PrifCityEntrance.prifSkillCheckNoNPC(player)) {
                    player.dialogue(new MessageDialogue("You have all Prifddinas requirements. Congratulations!"));
                }
                return true;
            }
        }
        return false;
    }
}
