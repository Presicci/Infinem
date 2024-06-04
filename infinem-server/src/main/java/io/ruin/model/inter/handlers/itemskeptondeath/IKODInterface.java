package io.ruin.model.inter.handlers.itemskeptondeath;

import io.ruin.cache.def.EnumDefinition;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.combat.Killer;
import io.ruin.api.utils.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemDropPrompt;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemImbuing;
import io.ruin.model.item.pet.Pet;
import io.ruin.model.item.actions.impl.combine.ItemCombining;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.services.Loggers;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class IKODInterface {

    public static void open(Player player) {
        if (player.isVisibleInterface(Interface.ITEMS_KEPT_ON_DEATH))
            player.closeInterface(InterfaceType.MAIN);
        player.openInterface(InterfaceType.MAIN, Interface.ITEMS_KEPT_ON_DEATH);

        boolean protect = player.getPrayer().isActive(Prayer.PROTECT_ITEM);
        boolean skull = player.getCombat().isSkulled();
        boolean wilderness = player.wildernessLevel >= 21;
        player.putTemporaryAttribute(AttributeKey.IKOD_PROTECT_ITEM, protect);
        player.putTemporaryAttribute(AttributeKey.IKOD_SKULL, skull);
        player.putTemporaryAttribute(AttributeKey.IKOD_WILDY, wilderness);
        player.putTemporaryAttribute(AttributeKey.IKOD_PK, false);
        IKODInterfaceFlags flags = new IKODInterfaceFlags(protect, skull, wilderness, false);
        IKODInterfaceUpdater itemKeptInterfaceState = IKODInterfaceUpdater.create(player, flags);
        itemKeptInterfaceState.update(player);
        /*player.getPacketSender().sendAccessMask(Interface.ITEMS_KEPT_ON_DEATH, 18, 0, 4, 2);
        player.getPacketSender().sendAccessMask(Interface.ITEMS_KEPT_ON_DEATH, 21, 0, 42, 2);

        boolean skulled = player.getCombat().isSkulled();
        boolean ultimateIronMan = player.getGameMode().isUltimateIronman(); //todo
        int keepCount = getKeepCount(skulled, ultimateIronMan, player.getPrayer().isActive(Prayer.PROTECT_ITEM));

        ArrayList<Item> items = getItems(player);
        ArrayList<Item> keepItems = new ArrayList<>(keepCount);
        int keepCountRemaining = keepCount;
        long value = 0;
        for(Iterator<Item> it = items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            ItemDef def = item.getDef();
            if(!def.neverProtect && keepCountRemaining > 0) {
                int keepAmount = Math.min(item.getAmount(), keepCountRemaining);
                keepItems.add(new Item(item.getId(), keepAmount));
                keepCountRemaining -= keepAmount;
                item.incrementAmount(-keepAmount);
                if(item.getAmount() == 0) {
                    it.remove();
                    continue;
                }
            }
            value += getValue(item);
        }
        player.getPacketSender().sendItems(-1, 63834, 468, items.toArray(new Item[items.size()]));
        player.getPacketSender().sendItems(-1, 63718, 584, keepItems.toArray(new Item[keepItems.size()]));
        player.getPacketSender().sendClientScript(118, "isii1s", 0, "", keepCount, skulled ? 1 : 0, ultimateIronMan ? 1 : 0, NumberUtils.formatNumber(value) + " " + ("gp"));
        */
    }

    private static final List<Integer> CHARGED_UNTRADEABLES = Arrays.asList(
            12809, 12006, 20655, 20657, 19710,
            12926, 22550, 11283, 21633, 22002,
            12931, 13197, 13199, 22555, 20714,
            12904, 11907, 12899, 22545
    );

    private static boolean allowProtect(Player player, Item item) {  // should this item be allowed to be 'saved'?
        if (item.getDef().neverProtect)
            return false;
        if (!item.getDef().tradeable && item.getDef().breakTo != null)
            return false;
        if (CHARGED_UNTRADEABLES.contains(item.getId()))
            return true;
        if (item.getDef().breakId != 0)
            return true;
        if (item.getDef().combinedFrom != null)
            return true;
        if (item.getDef().upgradedFrom != null) {
            ItemDefinition broken = ItemDefinition.get(item.getDef().upgradedFrom.regularId);
            return broken.tradeable;
        }
        return item.getDef().tradeable || player.wildernessLevel > 20;
    }

    //todo - still want to clean this up
    public static void forLostItem(Player player, Killer killer, Consumer<Item> dropConsumer) {
        ArrayList<Item> items = getItems(player);
        if (items.isEmpty())
            return;
        player.getInventory().clear();
        player.getEquipment().clear();
        Item currency = new Item(COINS_995, 0);
        ArrayList<Item> loseItems = new ArrayList<>(items.size());
        ArrayList<Item> keepItems = new ArrayList<>();
        int keepCountRemaining = getKeepCount(player.getCombat().isSkulled(), false, player.getPrayer().isActive(Prayer.PROTECT_ITEM));
        global: for (Item item : items) {
            boolean lootingBag = isLootingBag(item);
            // UIM lose everything on death
            if (player.getGameMode().isUltimateIronman() && !lootingBag) {
                loseItems.add(item);
                continue;
            }
            // Attempt to protect the item
            if (keepCountRemaining > 0 && allowProtect(player, item)) {
                // If player is in wilderness, drop ether always
                if (player.wildernessLevel > 0) {
                    if (IKODChargeable.VIGGORAS_CHAINMACE.isCharged(item)) {
                        loseItems.add(new Item(21820, IKODChargeable.VIGGORAS_CHAINMACE.uncharge(item)));
                    } else if (IKODChargeable.CRAWS_BOW.isCharged(item)) {
                        loseItems.add(new Item(21820, IKODChargeable.CRAWS_BOW.uncharge(item)));
                    } else if (IKODChargeable.THAMMARONS_SCEPTRE.isCharged(item)) {
                        loseItems.add(new Item(21820, IKODChargeable.THAMMARONS_SCEPTRE.uncharge(item)));
                    }
                }
                int keepAmount = Math.min(item.getAmount(), keepCountRemaining);
                keepItems.add(new Item(item.getId(), keepAmount, item.copyOfAttributes()));
                keepCountRemaining -= keepAmount;
                item.incrementAmount(-keepAmount);
                if (item.getAmount() == 0)
                    continue;
            }
            // Uncharge chargeable items
            for (IKODChargeable chargeable : IKODChargeable.values()) {
                if (chargeable.isCharged(item) && chargeable.test(player, killer)) {
                    if (chargeable.getChargeItem() != -1) {
                        loseItems.add(new Item(chargeable.getChargeItem(), chargeable.uncharge(item)));
                    } else {
                        chargeable.uncharge(item);
                    }
                    loseItems.add(item);
                    continue global;
                }
            }
            // Delete chinchompas
            if (item.getDef().name.toLowerCase().contains("chinchompa") && killer == null) {
                continue;
            }
            /*
              PvP Death
             */
            if ((player.wildernessLevel > 0 || player.pvpAttackZone) && killer != null) {
                // Drop runes from rune pouch, deletes rune pouch
                if (item.getId() == 12791) {
                    for (Item rune : player.getRunePouch().getItems()) {
                        if (rune != null)
                            loseItems.add(rune);
                    }
                    player.getRunePouch().clear();
                    continue;
                }
            }
            // Always keep pets
            Pet pet = item.getDef().pet;
            if (pet != null) {
                keepItems.add(item);
                continue;
            }
            // Process any death prompt actions
            ItemDropPrompt dropPrompt = item.getDef().getCustomValueOrDefault("DROP_PROMPT", null);
            if (dropPrompt != null) {
                dropPrompt.getAction().accept(item);
            }

            /*
             * Breakable items
             * If not in wilderness, do not break item
             * If in wilderness, break item
             * Reward killer with repair cost
             */
            ItemBreaking breakable = item.getDef().breakTo;
            if (breakable != null) {
                // If not in the wilderness, keep item without breaking
                if (player.wildernessLevel < 1 && !player.pvpAttackZone) {
                    keepItems.add(item);
                    continue;
                }
                ItemDefinition brokenDef = ItemDefinition.get(breakable.brokenId);
                if (brokenDef == null) {
                    System.out.println("Broken Def is null: " + breakable.brokenId);
                    continue;
                }
                // If PvP death, reward killer with coins equal to repair cost
                if (!Objects.isNull(killer)) {
                    currency.incrementAmount(breakable.coinRepairCost);
                }
                // Keep broken item if it is untradeable
                if (!brokenDef.tradeable) {
                    item.setId(brokenDef.id);
                    keepItems.add(item);
                    continue;
                }
                // Lose broken item if it is tradeable
                if (player.wildernessLevel > 0 || player.pvpAttackZone) {
                    item.setId(brokenDef.id);
                }
            }

            /*
             * Upgraded items
             */
            ItemImbuing upgrade = item.getDef().upgradedFrom;
            if (upgrade != null) {
                // If not in the wilderness, keep item without removing upgrade
                if (player.wildernessLevel < 1 && !player.pvpAttackZone) {
                    keepItems.add(item);
                    continue;
                }
                ItemDefinition regularDef = ItemDefinition.get(upgrade.regularId);
                if (regularDef == null) {
                    System.out.println("Regular Def is null: " + upgrade.regularId);
                    continue;
                }

                //Always keep these upgrades in wilderness
                if (upgrade.name().toLowerCase().contains("slayer")
                        || upgrade.name().toLowerCase().contains("salve")) {
                    keepItems.add(item);
                    continue;
                }

                //if in wilderness and below lvl 20 and regular item not tradeable, keep that item
                if (!regularDef.tradeable) {
                    if (player.wildernessLevel > 0 && player.wildernessLevel < 30 || player.pvpAttackZone) {
                        keepItems.add(new Item(upgrade.regularId, 1, item.copyOfAttributes()));
                    }
                    continue;
                }

                //if in wilderness and item is tradeable set item to regular id
                if (player.wildernessLevel > 0 || player.pvpAttackZone) {
                    item.setId(regularDef.id);
                }
            }

            /*
             * Combined items
             */
            ItemCombining combined = item.getDef().combinedFrom;
            if (combined != null && (player.wildernessLevel > 0 || player.pvpAttackZone)) {
                if (Objects.isNull(killer)) {
                    keepItems.add(item);
                } else if (ItemDefinition.get(combined.mainId).tradeable) {
                    loseItems.add(new Item(combined.mainId, item.getAmount()));
                    loseItems.add(new Item(combined.accessoryId, item.getAmount()));
                } else {
                    ItemBreaking breakab = ItemDefinition.get(combined.mainId).breakTo;
                    if (ItemDefinition.get(combined.mainId).breakTo != null) {
                        item.setId(ItemDefinition.get(breakab.brokenId).id);
                        keepItems.add(item);
                        loseItems.add(new Item(combined.accessoryId, item.getAmount()));
                    } else {
                        keepItems.add(new Item(combined.mainId, item.getAmount()));
                        loseItems.add(new Item(combined.accessoryId, item.getAmount()));
                    }
                }
                continue;
            }

            // Keep all untradeables, except looting bag
            if (!isLootingBag(item) && !item.getDef().tradeable) {
                keepItems.add(item);
                continue;
            }

            // If the player is carrying a looting bag then we dump all of its contents into the lost items collection.
            if (lootingBag) {
                ItemContainer contents = player.getLootingBag();
                if (!contents.isEmpty()) {
                    List<Item> lost = Arrays.stream(contents.getItems()).filter(Objects::nonNull).collect(Collectors.toList());
                    loseItems.addAll(lost);
                    player.getLootingBag().clear();
                }
            }

            loseItems.add(item);
        }
        if (currency.getAmount() > 0)
            loseItems.add(currency);
        int size = Math.min(keepItems.size(), player.getInventory().getItems().length);
        for (int i = 0; i < size; i++)
            player.getInventory().set(i, keepItems.get(i));
        for (Item dropItem : loseItems)
            dropConsumer.accept(dropItem);
        if (killer == null)
            Loggers.logDangerousDeath(player.getUserId(), player.getName(), player.getIp(), -1, "", "", keepItems, loseItems);
        else
            Loggers.logDangerousDeath(player.getUserId(), player.getName(), player.getIp(), killer.player.getUserId(), killer.player.getName(), killer.player.getIp(), keepItems, loseItems);
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

    private static long getValue(Item item) {
        if (item.getId() == BLOOD_MONEY)
            return item.getAmount();
        if (item.getId() == COINS_995)
            return item.getAmount();
        long price = item.getDef().protectValue;
        if (price <= 0 && ((price = item.getDef().highAlchValue)) <= 0)
            return 0;
        return item.getAmount() * price;
    }

    static {
        EnumDefinition map = EnumDefinition.get(879);
        //for(int id : map.keys)
        //   ItemDef.get(id).neverProtect = id != 13190 && id != 13192; //true when not bonds

        /**
         * Custom protect items
         */
        ItemDefinition.get(12931).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); // Serpentine helm (charged)
        ItemDefinition.get(13197).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); // Tanzanite helm (charged)
        ItemDefinition.get(13199).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); // Magma helm (charged)
        ItemDefinition.get(12926).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); //Charged blowpipe (charged)
        ItemDefinition.get(22550).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Craws bow (charged)
        ItemDefinition.get(22547).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Craws bow (uncharged)
        ItemDefinition.get(22545).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Viggora's chainmace (charged)
        ItemDefinition.get(22542).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Viggora's chainmace (uncharged)
        ItemDefinition.get(22555).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Thammaron's sceptre (charged)
        ItemDefinition.get(22552).protectValue = (int) Math.min(Integer.MAX_VALUE, 30000 * 1000L); //Thammaron's sceptre (uncharged)
        ItemDefinition.get(11283).protectValue = (int) Math.min(Integer.MAX_VALUE, 20000 * 1000L); //Dragonfire shield (charged)
        ItemDefinition.get(21633).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Ancient wyvern (charged)
        ItemDefinition.get(22002).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Dragonfire ward (charged)
        ItemDefinition.get(12899).protectValue = (int) Math.min(Integer.MAX_VALUE, 25000 * 1000L); //Trident of the swamp (charged)
        ItemDefinition.get(11907).protectValue = (int) Math.min(Integer.MAX_VALUE, 5000 * 1000L); //Trident of the seas (charged)
        ItemDefinition.get(11905).protectValue = (int) Math.min(Integer.MAX_VALUE, 5000 * 1000L); //Trident of the seas (fully charged)
        ItemDefinition.get(12904).protectValue = (int) Math.min(Integer.MAX_VALUE, 10000 * 1000L); //Toxic staff of the dead
        ItemDefinition.get(20714).protectValue = (int) Math.min(Integer.MAX_VALUE, 6000 * 1000L); //Tome of fire
        ItemDefinition.get(19550).protectValue = (int) Math.min(Integer.MAX_VALUE, 15000 * 1000L); //Ring of suffering
        ItemDefinition.get(22613).protectValue = (int) Math.min(Integer.MAX_VALUE, 120000 * 1000L); //Vesta long

        InterfaceHandler.register(Interface.ITEMS_KEPT_ON_DEATH, h -> {
            h.actions[12] = (DefaultAction) (p, option, slot, itemId) -> {
                boolean protect = p.getTemporaryAttribute(AttributeKey.IKOD_PROTECT_ITEM);
                boolean skull = p.getTemporaryAttribute(AttributeKey.IKOD_SKULL);
                boolean wilderness = p.getTemporaryAttribute(AttributeKey.IKOD_WILDY);
                boolean pk = p.getTemporaryAttribute(AttributeKey.IKOD_PK);
                switch (slot) {
                    case 0:
                        protect = !protect;
                        p.putTemporaryAttribute(AttributeKey.IKOD_PROTECT_ITEM, protect);
                        break;
                    case 1:
                        skull = !skull;
                        p.putTemporaryAttribute(AttributeKey.IKOD_SKULL, skull);
                        break;
                    case 2:
                        pk = !pk;
                        p.putTemporaryAttribute(AttributeKey.IKOD_PK, pk);
                        break;
                    case 3:
                        wilderness = !wilderness;
                        p.putTemporaryAttribute(AttributeKey.IKOD_WILDY, wilderness);
                        break;
                }
                IKODInterfaceFlags flags = new IKODInterfaceFlags(protect, skull, wilderness, pk);
                IKODInterfaceUpdater updater = IKODInterfaceUpdater.create(p, flags);
                updater.update(p);
            };
            h.closedAction = (player, integer) -> {

            };
        });
    }

    private static boolean isLootingBag(Item item) {
        return item.getId() == 11941 || item.getId() == 22586;
    }

    protected static boolean isPlayerDeath(Player player, Killer killer) {
        return (player.wildernessLevel > 0 || player.pvpAttackZone) && killer != null;
    }
}
