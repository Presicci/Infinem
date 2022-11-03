package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ScrollOfRedirection {

    RIMMINGTON(11741, 0),
    TROLLHEIM(11747, 0),
    TAVERLY(11742, 10),
    POLLNIVNEACH(11743, 20),
    KOUREND(19651, 25),
    RELLEKKA(11744, 30),
    BRIMHAVEN(11745, 40),
    YANILLE(11746, 50),
    PRIFDDINAS(23771, 70);

    private final int itemId, levelReq;

    private static final int HOUSE_TELE_TAB = 8013;
    private static final int SCROLL = 11740;

    private static void openMenu(Player player) {
        OptionScroll.open(player, "House Tablets", Arrays.stream(values())
                .map(loc -> new Option(loc.toString().substring(0, 1).toUpperCase() + loc.toString().substring(1).toLowerCase() + " - "
                        + (loc.levelReq > 0 ? "level " + loc.levelReq + " Construction" : "no requirement"), () -> convertTablet(player, loc)))
                .toArray(Option[]::new));
    }

    private static void convertTablet(Player player, ScrollOfRedirection scroll) {
        if (player.getInventory().getAmount(HOUSE_TELE_TAB) > 1 && !player.getInventory().hasFreeSlots(1) && !player.getInventory().contains(scroll.itemId)) {
            player.sendMessage("You do not have enough inventory space make that tablet.");
            return;
        }
        if (player.getStats().get(StatType.Construction).currentLevel < scroll.levelReq) {
            player.sendMessage("Your construction level is not high enough to make that tablet.");
            return;
        }
        if (player.getInventory().getAmount(HOUSE_TELE_TAB) == 1 || player.getInventory().getAmount(SCROLL) == 1) {
            convert(player, scroll, 1);
            return;
        }
        player.dialogue(new OptionsDialogue("How many would you like to redirect?",
                new Option("1", () -> convert(player, scroll, 1)),
                new Option("5", () -> convert(player, scroll, 5)),
                new Option("10", () -> convert(player, scroll, 10)),
                new Option("X", () -> player.integerInput("Enter amount:", amt -> convert(player, scroll, amt))),
                new Option("All", () -> convert(player, scroll, player.getInventory().getAmount(SCROLL)))
        ));
    }

    private static void convert(Player p, ScrollOfRedirection scroll, int amount) {
        int houseTabs = p.getInventory().getAmount(HOUSE_TELE_TAB);
        int scrollRedirects = p.getInventory().getAmount(SCROLL);
        int maxAmount = Math.min(scrollRedirects, houseTabs);
        if (amount > maxAmount) {
            amount = maxAmount;
        }
        p.getInventory().remove(HOUSE_TELE_TAB, amount);
        p.getInventory().remove(SCROLL, amount);
        p.getInventory().add(scroll.itemId, amount);
    }

    private static void refund(Player p, Item scrolls) {
        p.dialogue(
                new OptionsDialogue("Exchange " + scrolls.getAmount() + " for " + NumberUtils.formatNumber(775L * scrolls.getAmount()) + " points?",
                        new Option("Yes", () -> {
                            int amount = scrolls.getAmount();
                            p.getInventory().remove(scrolls.getId(), scrolls.getAmount());
                            PlayerCounter.NMZ_REWARD_POINTS.increment(p, 775 * amount);
                            p.dialogue(new ItemDialogue().one(SCROLL, "New rewards points total: " + NumberUtils.formatNumber(PlayerCounter.NMZ_REWARD_POINTS.get(p))));
                        }),
                        new Option("No", Player::closeDialogue)
                ));
    }

    private void revertOption(Player player, Item tab) {
        switch (tab.getAmount()) {
            case 1:
                player.dialogue(
                        new OptionsDialogue("Revert this tablet?",
                                new Option("Yes ~ you will not get your scroll back.", () -> revert(player, tab, 1)),
                                new Option("No ~ leave it alone.", Player::closeDialogue)
                        )
                );
                break;
            case 2:
                player.dialogue(
                        new OptionsDialogue("Revert these tablets?",
                                new Option("Revert BOTH ~ you will not get your scrolls back.", () -> revert(player, tab, 2)),
                                new Option("Revert ONE ~ you will not get your scroll back.", () -> revert(player, tab, 1)),
                                new Option("No ~ leave it alone.", Player::closeDialogue)
                        )
                );
                break;
            default:
                player.dialogue(
                        new OptionsDialogue("Revert these tablets?",
                                new Option("Revert ALL ~ you will not get your scrolls back.", () -> revert(player, tab, tab.getAmount())),
                                new Option("Revert SOME ~ you will not get your scrolls back.", () -> player.integerInput("How many do you wish to revert? " + tab.getAmount(), amt ->  revert(player, tab, amt))),
                                new Option("No ~ leave it alone.", Player::closeDialogue)
                        )
                );
                break;
        }

    }

    private void revert(Player player, Item tab, int amount) {
        if (!player.getInventory().contains(HOUSE_TELE_TAB) && !player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You don't have enough inventory space to do this.");
            return;
        }
        int maxAmt = tab.getAmount();

        if (amount > maxAmt)
            amount = maxAmt;

        player.getInventory().remove(tab.getId(), amount);
        player.getInventory().add(HOUSE_TELE_TAB, amount);
        player.dialogue(new ItemDialogue().one(HOUSE_TELE_TAB, "You revert the tablets to their original form."));
    }

    static {
        ItemItemAction.register(HOUSE_TELE_TAB, SCROLL, (player, item1, item2) -> {
            openMenu(player);
        });
        ItemAction.registerInventory(SCROLL, "refund", ScrollOfRedirection::refund);
        for(ScrollOfRedirection tab : values())
            ItemAction.registerInventory(tab.itemId, "revert", tab::revertOption);
    }
}
