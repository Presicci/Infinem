package io.ruin.model.inter.handlers;

import io.ruin.data.impl.polls;
import io.ruin.model.content.poll.PollInterface;
import io.ruin.utility.Color;
import io.ruin.model.World;
import io.ruin.model.entity.npc.actions.edgeville.CreditManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.services.XenUsername;

public class TabAccountManagement {

    private static final int OSPVP_CREDITS = 13190;
    private static final String FORUM_INBOX_URL = "https://www.infinem.net/forums/ucp.php?i=pm&folder=inbox";
    private static final String VOTE_URL = World.type.getWebsiteUrl() + "/vote";
    private static final String HISCORES = World.type.getWebsiteUrl() + "/highscores";

    static {
        /**
         * Send the interface when our player logs in
         */
        LoginListener.register(player -> player.getPacketSender().sendAccountManagement(getDonatorRank(player), getUsername(player), player.getUnreadPMs()));

        /**
         * Interface buttons
         */
        InterfaceHandler.register(Interface.ACCOUNT_MANAGEMENT, h -> {
            h.actions[26] = (SimpleAction) p -> p.dialogue(
                    new OptionsDialogue("Would you like to open our store?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Store", CreditManager.STORE_URL)),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[31] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to vote now?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Vote", VOTE_URL)),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[38] = (SimpleAction) p -> p.dialogue(
                    new OptionsDialogue("Would you like to open your forum inbox?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Inbox", FORUM_INBOX_URL)),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[45] = (SimpleAction) p -> {
              Item credits = p.getInventory().findItem(OSPVP_CREDITS);
              if(credits == null || credits.getAmount() < 50) {
                  p.dialogue(new ItemDialogue().one(OSPVP_CREDITS, "You need at least " + Color.COOL_BLUE.wrap("50 " + World.type.getWorldName() + " Credits") + " to change your username. You can purchase credits from our store." +
                          "<br>Would you like to view the store?"),
                          new OptionsDialogue("Would you like to open our store?",
                                  new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Store", CreditManager.STORE_URL)),
                                  new Option("No", p::closeDialogue)
                          ));
                  return;
              }
              p.nameInput("What would you like to change your display name to?", reqName -> {
                  XenUsername.requestNameChange(p, reqName);
              });
            };
            // Poll
            h.actions[54] = (SimpleAction) player -> player.getPollManager().viewPoll(polls.POLLS.get(polls.latestPollId));
            h.actions[60] = (SimpleAction) PollInterface::sendHistory;
            // Latest update
            h.actions[67] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to view recent announcements on the forums?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + "Infinem announcements", World.type.getWebsiteUrl() + "/forums/viewforum.php?f=4")),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[72] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to view recent patch notes on the forums?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + "Infinem patch notes", World.type.getWebsiteUrl() + "/forums/viewforum.php?f=5")),
                            new Option("No", p::closeDialogue)
                    )
            );
            // Useful links
            h.actions[80] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to view the website?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName(), World.type.getWebsiteUrl())),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[81] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to view the forums?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Forums", World.type.getWebsiteUrl() + "/forums")),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[82] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to join the discord?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Discord", "https://discord.gg/hG59gZtz")),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[83] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to view the hiscores?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Hiscores", HISCORES)),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[84] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to view the OSRS wiki?",
                            new Option("Yes", () -> p.openUrl("OSRS Wiki", "https://oldschool.runescape.wiki/")),
                            new Option("No", p::closeDialogue)
                    )
            );
            h.actions[85] = (SimpleAction) p -> p.dialogue(new OptionsDialogue("Would you like to open our store?",
                            new Option("Yes", () -> p.openUrl(World.type.getWorldName() + " Store", CreditManager.STORE_URL)),
                            new Option("No", p::closeDialogue)
                    )
            );
        });
    }

    public static String getUsername(Player player) {
        PlayerGroup clientGroup = player.getClientGroup();
        return (clientGroup.clientImgId != -1 ? clientGroup.tag() + " " : "") + player.getName();
    }

    public static String getDonatorRank(Player player) {
        if (player.isGroup(PlayerGroup.ZENYTE)) {
            return PlayerGroup.ZENYTE.tag() + " Godlike";
        } else if (player.isGroup(PlayerGroup.ONYX)) {
            return PlayerGroup.ONYX.tag() + " Master";
        } else if (player.isGroup(PlayerGroup.DRAGONSTONE)) {
            return PlayerGroup.DRAGONSTONE.tag() + " Ultimate";
        } else if (player.isGroup(PlayerGroup.DIAMOND)) {
            return PlayerGroup.DIAMOND.tag() + " Extreme";
        } else if (player.isGroup(PlayerGroup.RUBY)) {
            return PlayerGroup.RUBY.tag() + " Super";
        } else if (player.isGroup(PlayerGroup.SAPPHIRE)) {
            return PlayerGroup.SAPPHIRE.tag() + " Regular";
        } else {
            return "Unranked";
        }
    }

}
