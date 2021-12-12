package io.ruin.model.item.actions.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
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
                        + (loc.levelReq > 0 ? "level " + loc.levelReq + " Construction" : "no requirement"), () -> makeTablet(player, loc)))
                .toArray(Option[]::new));
    }

    private static void makeTablet(Player player, ScrollOfRedirection scroll) {
        if (player.getInventory().getAmount(HOUSE_TELE_TAB) > 1 && !player.getInventory().hasFreeSlots(1) && !player.getInventory().contains(scroll.itemId)) {
            player.sendMessage("You do not have enough inventory space make this tablet.");
            return;
        }
        if (player.getStats().get(StatType.Construction).currentLevel < scroll.levelReq) {
            player.sendMessage("Your construction level is not high enough to make that tablet.");
            return;
        }
        player.getInventory().remove(HOUSE_TELE_TAB, 1);
        player.getInventory().add(scroll.itemId, 1);
    }

    static {
        ItemItemAction.register(HOUSE_TELE_TAB, SCROLL, (player, item1, item2) -> {
            openMenu(player);
        });
        ItemAction.registerInventory(SCROLL, "refund", (player, item) -> {
            player.getInventory().remove(SCROLL, 1);
            player.nmzRewardPoints += 775;
            player.sendFilteredMessage("You refund a scroll for 775 reward points.");
        });
    }
}
