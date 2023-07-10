package io.ruin.model.inter.handlers.itemskeptondeath;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/9/2023
 */

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.ItemBreaking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class IKOD {
    public final List<Item> kept;
    public final List<IKODItem> otherItems;

    IKOD(List<Item> kept, List<IKODItem> otherItems) {
        this.kept = kept;
        this.otherItems = otherItems;
    }

    public static IKOD create(Player player, IKODInterfaceFlags flags) {
        ArrayList<Item> items = IKODInterface.getItems(player);

        boolean ultimateIronMan = player.getGameMode().isUltimateIronman();
        int keepCount = IKODInterface.getKeepCount(flags.skull, ultimateIronMan, flags.protect);
        List<Item> kept = forceKeptItems(keepCount, items);
        items.removeAll(kept);

        ArrayList<IKODItem> remainingItems = new ArrayList<>();
        remainingItems.addAll(lostUntradeables(items, flags));
        remainingItems.addAll(otherKept(items, flags));
        remainingItems.addAll(lostTradeables(items, flags));

        return new IKOD(kept, remainingItems);
    }

    private static List<IKODItem> otherKept(List<Item> items, IKODInterfaceFlags flags) {
        ArrayList<IKODItem> result = new ArrayList<>();
        for (Item item : new ArrayList<>(items)) {
            if (!item.getDef().tradeable) {
                result.add(new IKODItem(IKODKind.OtherKept, item));
                items.remove(item);
            }
        }
        return result;
    }

    private static List<IKODItem> lostTradeables(List<Item> items, IKODInterfaceFlags flags) {
        ArrayList<IKODItem> result = new ArrayList<>();
        for (Item item : new ArrayList<>(items)) {
            if (!item.getDef().tradeable) {
                continue;
            }
            ItemBreaking breakTo = item.getDef().breakTo;
            if (flags.killedByAPlayer) {
                result.add(new IKODItem(IKODKind.LostToThePlayerWhoKillsYou, item));
            } else if (breakTo == null) {
                result.add(new IKODItem(IKODKind.Lost, item));
            } else {
                result.add(new IKODItem(IKODKind.LostDowngraded, new Item(breakTo.brokenId, item.getAmount())));
            }
            items.remove(item);
        }
        return result;
    }

    private static List<IKODItem> lostUntradeables(List<Item> items, IKODInterfaceFlags flags) {
        ArrayList<IKODItem> result = new ArrayList<>();
        for (Item item : new ArrayList<>(items)) {
            if (!item.getDef().tradeable) {
                boolean convertible = item.getDef().highAlchValue > 0;
                IKODKind kind;
                if (convertible && flags.wilderness && !flags.killedByAPlayer) {
                    kind = IKODKind.LostGraveyardCoins;
                } else if (!convertible && flags.wilderness) {
                    kind = IKODKind.Deleted;
                } else if (convertible && flags.killedByAPlayer) {
                    kind = IKODKind.LostToThePlayerWhoKillsYou;
                } else {
                    kind = IKODKind.Lost;
                }
                result.add(new IKODItem(kind, item));
                items.remove(item);
            }
        }
        return result;
    }

    private static List<Item> forceKeptItems(int keepCount, List<Item> sortedByProtectValue) {
        return sortedByProtectValue.stream()
                .filter(it -> !it.getDef().neverProtect)
                .limit(keepCount)
                .collect(Collectors.toList());
    }
}
