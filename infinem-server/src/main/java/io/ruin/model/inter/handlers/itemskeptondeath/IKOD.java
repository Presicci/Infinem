package io.ruin.model.inter.handlers.itemskeptondeath;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/9/2023
 */

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemDropPrompt;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemImbue;
import io.ruin.model.item.actions.impl.chargable.Blowpipe;
import io.ruin.model.item.actions.impl.combine.ItemCombining;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.services.Loggers;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class IKOD {
    public final List<Item> kept;
    public final List<IKODItem> otherItems;

    IKOD(List<Item> kept, List<IKODItem> otherItems) {
        this.kept = kept;
        this.otherItems = otherItems;
    }

    public static IKOD create(Player player, IKODInterfaceFlags flags) {
        ArrayList<Item> items = getItems(player);

        boolean ultimateIronMan = player.getGameMode().isUltimateIronman();
        int keepCount = getKeepCount(flags.skull, ultimateIronMan, flags.protect);
        List<Item> kept = forceKeptItems(keepCount, items);

        ArrayList<IKODItem> remainingItems = new ArrayList<>();
        remainingItems.addAll(lostUntradeables(items, flags));
        remainingItems.addAll(otherKept(items, flags));
        remainingItems.addAll(lostTradeables(items, flags));

        return new IKOD(kept, remainingItems);
    }

    private static List<IKODItem> otherKept(List<Item> items, IKODInterfaceFlags flags) {
        ArrayList<IKODItem> result = new ArrayList<>();
        for (Item item : new ArrayList<>(items)) {
            Pet pet = item.getDef().pet;
            if (pet != null) {
                result.add(new IKODItem(IKODKind.OtherKept, item));
                items.remove(item);
            } else if (!item.getDef().tradeable) {
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
            IKODKind kind;
            if (item.getDef().name.toLowerCase().contains("chinchompa") && !flags.killedByAPlayer) {
                kind = IKODKind.Deleted;
            } else if (flags.killedByAPlayer) {
                kind = IKODKind.LostToThePlayerWhoKillsYou;
            } else {
                kind = IKODKind.Lost;
            }
            result.add(new IKODItem(kind, item));
            items.remove(item);
        }
        return result;
    }

    private static List<IKODItem> lostUntradeables(List<Item> items, IKODInterfaceFlags flags) {
        ArrayList<IKODItem> result = new ArrayList<>();
        for (Item item : new ArrayList<>(items)) {
            if (!item.getDef().tradeable) {
                ItemBreaking breakable = item.getDef().breakTo;
                ItemImbue imbue = item.getDef().upgradedFrom;
                ItemCombining combined = item.getDef().combinedFrom;
                boolean isChargeable = IKODChargeable.isChargeable(item.getId());
                IKODKind kind;
                if ((item.getId() == Items.RUNE_POUCH && flags.killedByAPlayer) || isLootingBag(item)) {
                    kind = IKODKind.Deleted;
                } else if (breakable != null) {
                    if (flags.killedByAPlayer) {
                        kind = IKODKind.OtherKeptDowngraded;
                    } else {
                        kind = IKODKind.Lost;
                    }
                } else if (imbue != null) {
                    if (flags.killedByAPlayer) {
                        ItemDefinition imbueDef = ItemDefinition.get(imbue.regularId);
                        if (imbueDef.tradeable || IKODChargeable.isChargeable(imbue.regularId)) {
                            kind = IKODKind.LostToThePlayerWhoKillsYou;
                        } else {
                            kind = IKODKind.OtherKeptDowngraded;
                        }
                    } else {
                        kind = IKODKind.Lost;
                    }
                } else if (combined != null) {
                    if (flags.killedByAPlayer) {
                        ItemDefinition revertedDef = ItemDefinition.get(combined.mainId);
                        if (revertedDef.breakTo != null) {
                            kind = IKODKind.OtherKeptDowngradedLostKit;
                        } else {
                            kind = IKODKind.LostToThePlayerWhoKillsYou;
                        }
                        boolean isCombinedChargeable = IKODChargeable.isChargeable(combined.mainId);
                        if (isCombinedChargeable) {
                            kind = IKODKind.LostToThePlayerWhoKillsYou;
                        }
                    } else {
                        kind = IKODKind.OtherKept;
                    }
                } else if (isChargeable) {
                    if (flags.killedByAPlayer) {
                        kind = IKODKind.LostToThePlayerWhoKillsYou;
                    } else {
                        kind = IKODKind.Lost;
                    }
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
        List<Item> keptItems = new ArrayList<>();
        List<Item> removeFromList = new ArrayList<>();
        Iterator<Item> iter = sortedByProtectValue.stream()
                .filter(item -> !item.getDef().neverProtect)
                .iterator();
        Item item = iter.next();
        while (keepCount > 0) {
            keptItems.add(new Item(item.getId(), 1, item.copyOfAttributes()));
            keepCount -= 1;
            item.incrementAmount(-1);
            if (item.getAmount() == 0) {
                removeFromList.add(item);
                item = iter.next();
            }
        }
        sortedByProtectValue.removeAll(removeFromList);
        return keptItems;
    }

    private static void downgradeItem(Item item, Item coins, ArrayList<Item> loseItems, ArrayList<Item> keepItems) {
        ItemBreaking breakable = item.getDef().breakTo;
        ItemImbue imbue = item.getDef().upgradedFrom;
        ItemCombining combined = item.getDef().combinedFrom;
        if (breakable != null && ItemDefinition.get(breakable.brokenId) != null) {
            item.setId(breakable.brokenId);
            coins.incrementAmount(breakable.coinRepairCost);
            keepItems.add(item);
        } else if (imbue != null && ItemDefinition.get(imbue.regularId) != null) {
            item.setId(imbue.regularId);
            loseItems.add(item);
        } else if (combined != null && ItemDefinition.get(combined.mainId) != null) {
            ItemDefinition mainDef = ItemDefinition.get(combined.mainId);
            item.setId(combined.mainId);
            breakable = mainDef.breakTo;
            if (breakable != null) {
                downgradeItem(item, coins, loseItems, keepItems);
            } else if (mainDef.tradeable) {
                loseItems.add(item);
            } else {
                boolean isCombinedChargeable = IKODChargeable.isChargeable(combined.mainId);
                if (isCombinedChargeable) {
                    loseItems.add(item);
                } else {
                    keepItems.add(item);
                }
            }
            ItemDefinition accessoryDef = ItemDefinition.get(combined.accessoryId);
            if (accessoryDef.tradeable) {
                loseItems.add(new Item(combined.accessoryId));
            } else {
                keepItems.add(new Item(combined.accessoryId));
            }
        } else {
            // Change to keep if we want untradeables to go to dying players inv after death
            loseItems.add(item);
        }
    }

    public static void forLostItem(Player player, Killer killer, Consumer<Item> dropConsumer) {
        IKOD ikod = create(player, new IKODInterfaceFlags(player.getPrayer().isActive(Prayer.PROTECT_ITEM), player.getCombat().isSkulled(), false, killer != null));
        player.getEquipment().clear();
        player.getInventory().clear();
        Item coins = new Item(995, 0);
        ArrayList<Item> lostItems = new ArrayList<>();
        ArrayList<Item> keptItems = new ArrayList<>();
        for (Item item : ikod.kept) {
            if (player.wildernessLevel > 0) {
                if (IKODChargeable.VIGGORAS_CHAINMACE.isCharged(item)) {
                    lostItems.add(new Item(21820, IKODChargeable.VIGGORAS_CHAINMACE.uncharge(item)));
                } else if (IKODChargeable.CRAWS_BOW.isCharged(item)) {
                    lostItems.add(new Item(21820, IKODChargeable.CRAWS_BOW.uncharge(item)));
                } else if (IKODChargeable.THAMMARONS_SCEPTRE.isCharged(item)) {
                    lostItems.add(new Item(21820, IKODChargeable.THAMMARONS_SCEPTRE.uncharge(item)));
                }
            }
            keptItems.add(item);
        }
        for (IKODItem ikodItem : ikod.otherItems) {
            Item item = ikodItem.item;
            switch (ikodItem.kind) {
                case OtherKept:
                    keptItems.add(item);
                    break;
                case Lost:
                    lostItems.add(item);
                    break;
                case LostToThePlayerWhoKillsYou:
                    if (!item.getDef().tradeable) {
                        downgradeItem(item, coins, lostItems, keptItems);
                    } else {
                        lostItems.add(item);
                    }
                    break;
                case Deleted:
                    if (item.getId() == Items.RUNE_POUCH) {
                        for (Item rune : player.getRunePouch().getItems()) {
                            if (rune != null)
                                lostItems.add(rune);
                        }
                        player.getRunePouch().clear();
                    } else if (isLootingBag(item)) {
                        ItemContainer contents = player.getLootingBag();
                        if (!contents.isEmpty()) {
                            List<Item> lost = Arrays.stream(contents.getItems()).filter(Objects::nonNull).collect(Collectors.toList());
                            lostItems.addAll(lost);
                            player.getLootingBag().clear();
                        }
                    }
                    // Item is deleted
                    break;
                case LostDowngraded:
                case OtherKeptDowngraded:
                case OtherKeptDowngradedLostKit:
                    downgradeItem(item, coins, lostItems, keptItems);
                    break;
            }
        }
        ArrayList<Item> lostCharges = new ArrayList<>();
        lostItems: for (Item item : lostItems) {
            // Uncharge chargeable items
            for (IKODChargeable chargeable : IKODChargeable.values()) {
                if (chargeable.isCharged(item) && chargeable.test(player, killer != null)) {
                    if (chargeable == IKODChargeable.TOXIC_BLOWPIPE) {
                        Blowpipe.Dart dart = Blowpipe.getDart(item);
                        int dartAmount = Blowpipe.getDartAmount(item);
                        if (dartAmount > 0 && dart != null) {
                            lostCharges.add(new Item(dart.id, dartAmount));
                        }
                    }
                    if (chargeable.getChargeItem() != -1) {
                        lostCharges.add(new Item(chargeable.getChargeItem(), chargeable.uncharge(item)));
                    } else {
                        chargeable.uncharge(item);
                    }
                    continue lostItems;
                }
            }
            // Process any death prompt actions
            ItemDropPrompt dropPrompt = item.getDef().getCustomValueOrDefault("DROP_PROMPT", null);
            if (dropPrompt != null) {
                dropPrompt.getAction().accept(item);
            }
        }
        lostItems.addAll(lostCharges);
        if (coins.getAmount() > 0)
            lostItems.add(coins);
        for (Item item : keptItems)
            player.getInventory().add(item);
        for (Item dropItem : lostItems)
            dropConsumer.accept(dropItem);
        if (killer == null)
            Loggers.logDangerousDeath(player.getUserId(), player.getName(), player.getIp(), -1, "", "", keptItems, lostItems);
        else
            Loggers.logDangerousDeath(player.getUserId(), player.getName(), player.getIp(), killer.player.getUserId(), killer.player.getName(), killer.player.getIp(), keptItems, lostItems);
    }

    private static boolean isLootingBag(Item item) {
        return item.getId() == 11941 || item.getId() == 22586;
    }

    public static int getKeepCount(boolean skulled, boolean ultimateIronman, boolean protectingItem) {
        if (ultimateIronman)
            return 0;
        int keepCount = skulled ? 0 : 3;
        if (protectingItem)
            keepCount++;
        return keepCount;
    }

    public static ArrayList<Item> getItems(Player player) {
        int count = player.getInventory().getCount() + player.getEquipment().getCount();
        ArrayList<Item> list = new ArrayList<>(count);
        if (count > 0) {
            for (Item item : player.getInventory().getItems()) {
                if (item != null)
                    list.add(item.copy());
            }
            for (Item item : player.getEquipment().getItems()) {
                if (item != null)
                    list.add(item.copy());
            }
            list.sort((i1, i2) -> Integer.compare(i2.getDef().protectValue, i1.getDef().protectValue));
        }
        return list;
    }
}
