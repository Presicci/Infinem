package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.PrayerScroll;
import io.ruin.model.skills.prayer.Prayer;

import static io.ruin.cache.ItemID.COINS_995;

public class TabPrayer {

    static {
        InterfaceHandler.register(Interface.PRAYER, h -> {
            for(Prayer prayer : Prayer.values()) {
                if(prayer == Prayer.RIGOUR) {
                    h.actions[prayer.ordinal() + 9] = (DefaultAction) (p, option, slot, itemId) -> {
                        if(option == 1)
                            p.getPrayer().toggle(prayer);
                        else
                            refundPrayer(p, prayer);
                    };
                } else if(prayer == Prayer.AUGURY) {
                    h.actions[prayer.ordinal() + 9] = (DefaultAction) (p, option, slot, itemId) -> {
                        if(option == 1)
                            p.getPrayer().toggle(prayer);
                        else
                            refundPrayer(p, prayer);
                    };
                } else {
                    h.actions[prayer.ordinal() + 9] = (SimpleAction) p -> p.getPrayer().toggle(prayer);
                }
            }
            // Filters
            h.actions[42] = (DefaultAction) (player, option, slot, itemId) -> {
                switch (slot) {
                    case 0:
                        int newValue = Config.PRAYER_FILTER_LOWER_TIERS.toggle(player);
                        if (Config.PRAYER_FILTER_LOWER_TIERS_MULTI_PRAYER.get(player) != newValue) {
                            Config.PRAYER_FILTER_LOWER_TIERS_MULTI_PRAYER.toggle(player);
                        }
                        break;
                    case 1:
                        if (Config.PRAYER_FILTER_LOWER_TIERS.get(player) == 0) {
                            player.sendMessage("This option can only be toggled when the \"Show lower tiers of tiered prayers\" is off.");
                            return;
                        }
                        Config.PRAYER_FILTER_LOWER_TIERS_MULTI_PRAYER.toggle(player);
                        break;
                    case 2:
                        Config.PRAYER_FILTER_RAPID_HEALING.toggle(player);
                        break;
                    case 3:
                        Config.PRAYER_FILTER_LEVEL.toggle(player);
                        break;
                    case 4:
                        Config.PRAYER_FILTER_REQUIREMENT.toggle(player);
                        break;
                }
            };
        });
        InterfaceHandler.register(Interface.QUICK_PRAYER, h -> {
            h.actions[4] = (SlotAction) (p, slot) -> {
                Prayer prayer = Prayer.getQuickPrayer(slot);
                if(prayer != null)
                    p.getPrayer().toggleQuickPrayer(prayer);
            };
            h.actions[5] = (SimpleAction) p -> setupQuickPrayers(p, false);
            h.closedAction = (player, integer) -> player.getPacketSender().sendAccessMask(Interface.PRAYER, 42, 0, 4, AccessMasks.ClickOp1);
        });
    }

    public static void refundPrayer(Player player, Prayer prayer) {
        if(prayer == Prayer.AUGURY && Config.AUGURY_UNLOCK.get(player) == 0) {
            player.dialogue(new MessageDialogue("You have to learn how to use <col=000080>Augury</col> before attempting to refund the scroll!"));
            return;
        }

        if(prayer == Prayer.RIGOUR && Config.RIGOUR_UNLOCK.get(player) == 0) {
            player.dialogue(new MessageDialogue("You have to learn how to use <col=000080>Rigour</col> before attempting to refund the scroll!"));
            return;
        }

        if(player.getInventory().isFull()) {
            player.sendMessage("You need at least one free inventory slot to do this.");
            return;
        }

        if(player.isLocked()) {
            player.sendMessage("You're too busy to do this!");
            return;
        }

        if(player.getDuel().stage >= 4) {
            player.sendMessage("You can't refund your scroll inside the duel arena!");
            return;
        }

        if(player.joinedTournament) {
            player.sendMessage("You can't refund your scroll inside the tournament!");
            return;
        }

        int itemId = (prayer == Prayer.AUGURY ? PrayerScroll.AUGURY_SCROLL : PrayerScroll.RIGOUR_SCROLL);
        int cost = 5000000;
        int currencyId = COINS_995;
        String currencyName = "coins";

        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Pay " + cost + " " + currencyName + " and get your prayer scroll back?", new Item(itemId), () -> {
            Item bloodMoney = player.getInventory().findItem(currencyId);
            if(bloodMoney == null || bloodMoney.getAmount() < cost) {
                player.sendMessage("You need at least " + cost + " " + currencyName + " to refund your prayer scroll.");
                return;
            }
            player.lock();
            player.getInventory().add(itemId, 1);
            bloodMoney.remove(cost);
            if(prayer == Prayer.AUGURY)
                Config.AUGURY_UNLOCK.set(player, 0);
            else
                Config.RIGOUR_UNLOCK.set(player, 0);
            player.sendMessage("You refund your " + (prayer == Prayer.AUGURY ? "Dexterous" : "Arcane") + " prayer scroll.");
            player.unlock();
        }));
    }

    public static void setupQuickPrayers(Player player, boolean setup) {
        if (setup) {
            player.openInterface(InterfaceType.SIDE_PRAYER, 77);
            player.getPacketSender().sendAccessMask(77, 4, 0, 28, AccessMasks.ClickOp1);
        } else {
            player.openInterface(InterfaceType.SIDE_PRAYER, 541);
        }

        player.getPacketSender().sendClientScript(915, "i", 5);
    }
}