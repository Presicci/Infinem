package io.ruin.model.skills.construction.actions;


import io.ruin.model.entity.attributes.AttributeKey;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.construction.Buildable;
import io.ruin.model.skills.construction.Hotspot;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CostumeRoom {

    static {
        for (Buildable b : Hotspot.FANCY_DRESS_BOX.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.FANCY_DRESS_BOX.open(player, -1, b));
            ItemObjectAction.register(open, (player, item, obj) -> depositItem(player, item, true, b, CostumeStorage.FANCY_DRESS_BOX));
        }
        for (Buildable b : Hotspot.TOY_BOX.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.TOY_BOX.open(player, -1, b));
            ItemObjectAction.register(open, (player, item, obj) -> depositItem(player, item, true, b, CostumeStorage.TOY_BOX));
        }
        for (Buildable b :Hotspot.ARMOUR_CASE.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.ARMOUR_CASE.open(player, -1, b));
            ItemObjectAction.register(open, (player, item, obj) -> depositItem(player, item, true, b, CostumeStorage.ARMOUR_CASE));
        }
        for (Buildable b :Hotspot.MAGIC_WARDROBE.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.MAGIC_WARDROBE.open(player, -1, b));
            ItemObjectAction.register(open, (player, item, obj) -> depositItem(player, item, true, b, CostumeStorage.MAGIC_WARDROBE));
        }
        for (Buildable b : Hotspot.CAPE_RACK.getBuildables()) {
            int open = b.getBuiltObjects()[0];
            ObjectAction.register(open, "search", (player, obj) -> CostumeStorage.CAPE_RACK.open(player, -1, b));
            ItemObjectAction.register(open, (player, item, obj) -> depositItem(player, item, true, b, CostumeStorage.CAPE_RACK));
        }
        for (Buildable b : Hotspot.TREASURE_CHEST.getBuildables()) {
            int closed = b.getBuiltObjects()[0];
            int open = closed + 1;
            ObjectAction.register(closed, "open", (player, obj) -> open(player, obj, open));
            ObjectAction.register(open, "close", (player, obj) -> close(player, obj, closed));
        }
        ObjectAction.register(18805, "search", (player, obj) -> clueDialogue(player, Buildable.OAK_TREASURE_CHEST));
        ItemObjectAction.register(18805, (player, item, obj) -> depositItem(player, item, true, Buildable.OAK_TREASURE_CHEST, CostumeStorage.EASY_TREASURE_TRAILS));

        ObjectAction.register(18807, "search", (player, obj) -> clueDialogue(player, Buildable.TEAK_TREASURE_CHEST));
        ItemObjectAction.register(18807, (player, item, obj) -> depositItem(player, item, true, Buildable.TEAK_TREASURE_CHEST, CostumeStorage.EASY_TREASURE_TRAILS, CostumeStorage.MEDIUM_TREASURE_TRAILS));

        ObjectAction.register(18809, "search", (player, obj) -> clueDialogue(player, Buildable.MAHOGANY_TREASURE_CHEST));
        ItemObjectAction.register(18809, (player, item, obj) -> depositItem(player, item, true, Buildable.MAHOGANY_TREASURE_CHEST, CostumeStorage.BEGINNER_TREASURE_TRAILS, CostumeStorage.EASY_TREASURE_TRAILS, CostumeStorage.MEDIUM_TREASURE_TRAILS, CostumeStorage.HARD_TREASURE_TRAILS, CostumeStorage.ELITE_TREASURE_TRAILS, CostumeStorage.MASTER_TREASURE_TRAILS));

    }

    private static void clueDialogue(Player player, Buildable buildable) {
        switch (buildable) {
            default:
                player.dialogue(new OptionsDialogue(
                        new Option("Beginner treasure trails", () -> CostumeStorage.BEGINNER_TREASURE_TRAILS.open(player, 1, Buildable.OAK_TREASURE_CHEST)),
                        new Option("Easy treasure trails", () -> CostumeStorage.EASY_TREASURE_TRAILS.open(player, 1, Buildable.OAK_TREASURE_CHEST))
                ));
                break;
            case TEAK_TREASURE_CHEST:
                player.dialogue(new OptionsDialogue(
                        new Option("Beginner treasure trails", () -> CostumeStorage.BEGINNER_TREASURE_TRAILS.open(player, 2, Buildable.TEAK_TREASURE_CHEST)),
                        new Option("Easy treasure trails", () -> CostumeStorage.EASY_TREASURE_TRAILS.open(player, 2, Buildable.TEAK_TREASURE_CHEST)),
                        new Option("Medium treasure trails", () -> CostumeStorage.MEDIUM_TREASURE_TRAILS.open(player, 2, Buildable.TEAK_TREASURE_CHEST))
                ));
                break;
            case MAHOGANY_TREASURE_CHEST:
                player.dialogue(new OptionsDialogue(
                        new Option("Beginner treasure trails", () -> CostumeStorage.BEGINNER_TREASURE_TRAILS.open(player, 5, Buildable.MAHOGANY_TREASURE_CHEST)),
                        new Option("Easy treasure trails", () -> CostumeStorage.EASY_TREASURE_TRAILS.open(player, 5, Buildable.MAHOGANY_TREASURE_CHEST)),
                        new Option("Medium treasure trails", () -> CostumeStorage.MEDIUM_TREASURE_TRAILS.open(player, 5, Buildable.MAHOGANY_TREASURE_CHEST)),
                        new Option("Hard treasure trails", () -> CostumeStorage.HARD_TREASURE_TRAILS.open(player, 5, Buildable.MAHOGANY_TREASURE_CHEST)),
                        new Option("More...", () -> player.dialogue(new OptionsDialogue(
                                new Option("Elite treasure trails", () -> CostumeStorage.ELITE_TREASURE_TRAILS.open(player, 5, Buildable.MAHOGANY_TREASURE_CHEST)),
                                new Option("Master treasure trails", () -> CostumeStorage.MASTER_TREASURE_TRAILS.open(player, 5, Buildable.MAHOGANY_TREASURE_CHEST)),
                                new Option("More...", () -> clueDialogue(player, buildable))
                        )))
                ));
                break;
        }
    }

    private static void open(Player player, GameObject obj, int open) {
        player.animate(536);
        obj.setId(open);
    }

    private static void close(Player player, GameObject obj, int closed) {
        player.animate(535);
        obj.setId(closed);
    }

    private static void withdrawItem(Player player, int itemId) {
        CostumeStorage cs = player.getTemporaryAttribute(AttributeKey.COSTUME_STORAGE);
        Costume costume = cs.getByItem(itemId);
        Map<Costume, Item[]> sets = cs.getSets(player);
        Item[] pieces = sets.get(costume);
        if (pieces == null || costume == null) {
            return;
        }
        int pieceIndex = costume.getPieceIndex(itemId);
        if (pieces[pieceIndex] == null)
            return;
        if (player.getGameMode().isUltimateIronman() && !cs.fullSetStored(player, costume) && pieces.length > 1) {
            player.sendMessage("Ultimate ironman can only withdraw complete sets from storage.");
            return;
        }
        player.getInventory().add(itemId, 1);
        int newAmount = pieces[pieceIndex].getAmount() - 1;
        if (newAmount == 0)
            pieces[pieceIndex] = null;
        else
            pieces[pieceIndex].setAmount(newAmount);
        cs.sendItems(player);
    }

    private static void withdrawSet(Player player, int slot) {
        CostumeStorage type = player.getTemporaryAttribute(AttributeKey.COSTUME_STORAGE);
        if (type == null)
            return;
        slot /= 36;
        if (slot < 0 || slot > type.getCostumes().length) {
            throw new IllegalArgumentException(""+slot);
        }
        Map<Costume, Item[]> storedSets = type.getSets(player);
        if (storedSets == null)
            throw new IllegalArgumentException();
        Costume costume = type.getCostumes()[slot];
        Item[] stored = storedSets.get(costume);
        if (stored == null) {
            return;
        }
        if (player.getGameMode().isUltimateIronman() && !type.fullSetStored(player, costume)) {
            player.sendMessage("Ultimate ironman can only withdraw from complete sets from storage.");
            return;
        }
        if (!player.getInventory().hasFreeSlots(stored.length)) {
            player.sendMessage("You'll need at least " + stored.length + " free inventory slots to withdraw that set.");
            return;
        }
        int index = 0;
        for (Item piece : stored) {
            if (piece == null) {
                ++index;
                continue;
            }
            if (piece.getAmount() > 1) {
                Item itemCopy = piece.copy();
                itemCopy.setAmount(1);
                player.getInventory().add(itemCopy);
                stored[index].incrementAmount(-1);
            } else {
                player.getInventory().add(piece);
                stored[index] = null;
            }
            ++index;
        }
        type.sendItems(player);
    }

    private static void depositItem(Player player, Item item, boolean messages, Buildable b, CostumeStorage... validTypes) {
        if (item == null)
            return;
        CostumeStorage type = null;
        Costume costume = null;
        for (CostumeStorage cs : CostumeStorage.values()) {
            costume = cs.getByItem(item.getId());
            if (costume != null) {
                type = cs;
                break;
            }
        }
        if (costume == null || !Arrays.asList(validTypes).contains(type)) {
            if (messages)
                player.sendMessage("You can't store that item there.");
            return;
        }
        if (item.getAttributeHash() != 0) {
            if (messages)
                player.sendMessage("You can't store modified items in there.");
            return;
        }
        if (!hasStorageSpace(player, b, type))
            return;
        if (player.getGameMode().isUltimateIronman() && costume.pieces.length > 1 && !type.willCompleteSet(player, costume, item)) {
            CostumeStorage finalType = type;
            Costume finalCostume = costume;
            boolean reopen = player.isVisibleInterface(675);
            player.dialogue(new OptionsDialogue("You can only withdraw this item once you complete the set, continue?",
                    new Option("Deposit", () -> {
                        handleItemDeposit(player, item, messages, finalType, finalCostume);
                        if (reopen)
                            finalType.open(player, b == Buildable.OAK_TREASURE_CHEST ? 1 : b == Buildable.TEAK_TREASURE_CHEST ? 2 : b == Buildable.MAHOGANY_TREASURE_CHEST ? 5 : -1, b);
                    }),
                    new Option("Do not deposit", () -> {
                        if (reopen)
                            finalType.open(player, b == Buildable.OAK_TREASURE_CHEST ? 1 : b == Buildable.TEAK_TREASURE_CHEST ? 2 : b == Buildable.MAHOGANY_TREASURE_CHEST ? 5 : -1, b);
                    })
            ));
        } else {
            handleItemDeposit(player, item, messages, type, costume);
        }
    }

    private static void handleItemDeposit(Player player, Item item, boolean messages, CostumeStorage type, Costume costume) {
        Map<Costume, Item[]> sets = type.getSets(player);
        Item[] pieces = sets.get(costume);
        int pieceIndex = costume.getPieceIndex(item.getId());
        if (pieces == null) {
            pieces = new Item[costume.pieces.length];
            pieces[pieceIndex] = new Item(item.getId(), 1);
            player.getInventory().remove(item.getId(), 1);
        } else {
            if (pieces[pieceIndex] == null) {
                pieces[pieceIndex] = new Item(item.getId(), 1);
                player.getInventory().remove(item.getId(), 1);
            } else {
                if (player.getGameMode().isUltimateIronman()) {
                    if (messages)
                        player.sendMessage("As an Ultimate ironman you can only store one of each item.");
                    return;
                }
                if (pieces[pieceIndex].getId() == item.getId()) {
                    pieces[pieceIndex].incrementAmount(1);
                    player.getInventory().remove(item.getId(), 1);
                } else {
                    if (messages)
                        player.sendMessage("You have a different item of that type stored already.");
                    return;
                }
            }
        }
        type.getSets(player).put(costume, pieces);
        if (messages)
            player.sendFilteredMessage("You place the item in the " + getStorageName(type) + ".");
    }

    private static void depositSet(Player player, Item item, Buildable b, CostumeStorage type) {
        if (item == null)
            return;
        Costume costume = type.getByItem(item.getId());
        if (costume == null) {
            player.sendMessage("You can't store that item there.");
            return;
        }
        if (!hasStorageSpace(player, b, type))
            return;
        if (player.getGameMode().isUltimateIronman() &&  !type.hasFullSet(player, costume)) {
            boolean reopen = player.isVisibleInterface(675);
            player.dialogue(new OptionsDialogue("You can only withdraw this item once you complete the set, continue?",
                    new Option("Deposit", () -> {
                        handleSetDeposit(player, costume, type);
                        if (reopen)
                            type.open(player, b == Buildable.OAK_TREASURE_CHEST ? 1 : b == Buildable.TEAK_TREASURE_CHEST ? 2 : b == Buildable.MAHOGANY_TREASURE_CHEST ? 5 : -1, b);
                    }),
                    new Option("Do not deposit", () -> {
                        if (reopen)
                            type.open(player, b == Buildable.OAK_TREASURE_CHEST ? 1 : b == Buildable.TEAK_TREASURE_CHEST ? 2 : b == Buildable.MAHOGANY_TREASURE_CHEST ? 5 : -1, b);
                    })
            ));
        } else {
            handleSetDeposit(player, costume, type);
        }
    }

    private static void handleSetDeposit(Player player, Costume costume, CostumeStorage type) {
        Map<Costume, Item[]> sets = type.getSets(player);
        Item[] pieces = sets.get(costume);
        if (pieces == null) {
            pieces = new Item[costume.pieces.length];
        }
        for (int index = 0; index < pieces.length; index++) {
            for (Item piece : costume.pieces[index]) {
                int id = piece.getId();
                List<Item> items = player.getInventory().collectItemsWithoutAttributes(id);
                if (items != null && items.size() > 0) {
                    if (pieces[index] == null) {
                        if (player.getGameMode().isUltimateIronman()) {
                            pieces[index] = new Item(id, 1);
                            player.getInventory().remove(id, 1);
                        } else {
                            pieces[index] = new Item(id, items.size());
                            player.getInventory().removeAll(items.toArray(new Item[0]));
                        }
                    } else {
                        if (pieces[index].getId() != id)
                            continue;
                        if (player.getGameMode().isUltimateIronman()) {
                            continue;
                        }
                        pieces[index].incrementAmount(items.size());
                        player.getInventory().removeAll(items.toArray(new Item[0]));
                    }
                    break;
                }
            }
        }
        type.getSets(player).put(costume, pieces);
        player.sendFilteredMessage("You place the set in the " + getStorageName(type) + ".");
    }

    private static void depositInventory(Player player) {
        CostumeStorage costumeStorage = player.getTemporaryAttribute(AttributeKey.COSTUME_STORAGE);
        Buildable buildable = player.getTemporaryAttribute(AttributeKey.COSTUME_BUILDABLE);
        for (Item item : player.getInventory().getItems()) {
            if (item == null)
                continue;
            depositItem(player, item, false, buildable, costumeStorage);
        }
        costumeStorage.sendItems(player);
    }

    private static boolean hasStorageSpace(Player player, Buildable buildable, CostumeStorage type) {
        int maxStored = getMaxStorage(buildable);
        int currentStored = type.countSpaceUsed(player);
        if (currentStored >= maxStored) {
            if (type == CostumeStorage.CAPE_RACK) {
                if (maxStored == 0) {
                    player.sendMessage("The oak cape rack can not hold capes of accomplishment.");
                } else {
                    player.sendMessage("That cape rack can only hold up to " + maxStored + " capes of accomplishment.");
                }
            } else {
                player.sendMessage("There's no more space in there.");
            }
            return false;
        }
        return true;
    }

    public static int getMaxStorage(Buildable b) {
        switch (b) {
            case OAK_ARMOUR_CASE:
                return 25;
            case TEAK_ARMOUR_CASE:
                return 50;

            case OAK_MAGIC_WARDROBE:
                return 7;
            case CARVED_OAK_MAGIC_WARDROBE:
                return 14;
            case TEAK_MAGIC_WARDROBE:
                return 21;
            case CARVED_TEAK_MAGIC_WARDROBE:
                return 28;
            case MAHOGANY_MAGIC_WARDROBE:
                return 35;
            case GILDED_MAGIC_WARDROBE:
                return 42;

            case OAK_CAPE_RACK:
                return 0;
            case TEAK_CAPE_RACK:
                return 1;
            case MAHOGANY_CAPE_RACK:
                return 5;
            case GILDED_CAPE_RACK:
                return 10;

            case OAK_FANCY_DRESS_BOX:
                return 2;
            case TEAK_FANCY_DRESS_BOX:
                return 4;

            default:
                return Integer.MAX_VALUE;
        }
    }

    public static String getStorageName(CostumeStorage costumeStorage) {
        switch (costumeStorage) {
            case TOY_BOX:
                return "toy box";
            case ARMOUR_CASE:
                return "armour case";
            case MAGIC_WARDROBE:
                return "magic wardrobe";
            case BEGINNER_TREASURE_TRAILS:
            case EASY_TREASURE_TRAILS:
            case MEDIUM_TREASURE_TRAILS:
            case HARD_TREASURE_TRAILS:
            case ELITE_TREASURE_TRAILS:
            case MASTER_TREASURE_TRAILS:
                return "treasure chest";
            case CAPE_RACK:
                return "cape rack";
            case FANCY_DRESS_BOX:
                return "fancy dress box";
            default:
                return "storage container";
        }
    }

    static {
        InterfaceHandler.register(675, h -> {
            h.actions[4] = (DefaultAction) (p, option, slot, itemId) -> {
                if (slot % 36 == 0) {
                    withdrawSet(p, slot);
                    return;
                }
                if (option == 1) {
                    withdrawItem(p, itemId);
                } else {
                    new Item(itemId).examine(p);
                }
            };
            h.actions[7] = (OptionAction) (player, option) -> {
                Buildable buildable = player.getTemporaryAttribute(AttributeKey.COSTUME_BUILDABLE);
                int clueScrollLevel = buildable == Buildable.OAK_TREASURE_CHEST ? 1 : buildable == Buildable.TEAK_TREASURE_CHEST ? 2 : 5;
                switch (option) {
                    default:
                        clueDialogue(player, buildable);
                        break;
                    case 2:
                        CostumeStorage.BEGINNER_TREASURE_TRAILS.open(player, clueScrollLevel, buildable);
                        break;
                    case 3:
                        CostumeStorage.EASY_TREASURE_TRAILS.open(player, clueScrollLevel, buildable);
                        break;
                    case 4:
                        CostumeStorage.MEDIUM_TREASURE_TRAILS.open(player, clueScrollLevel, buildable);
                        break;
                    case 5:
                        CostumeStorage.HARD_TREASURE_TRAILS.open(player, clueScrollLevel, buildable);
                        break;
                    case 6:
                        CostumeStorage.ELITE_TREASURE_TRAILS.open(player, clueScrollLevel, buildable);
                        break;
                    case 7:
                        CostumeStorage.MASTER_TREASURE_TRAILS.open(player, clueScrollLevel, buildable);
                        break;
                }
            };
            h.actions[8] = (SimpleAction) Config.COSTUME_DEPOSIT_SET::toggle;
            h.actions[10] = (SimpleAction) (player) -> {
                if (player.getGameMode().isUltimateIronman()) {
                    Buildable buildable = player.getTemporaryAttribute(AttributeKey.COSTUME_BUILDABLE);
                    CostumeStorage costumeStorage = player.getTemporaryAttribute(AttributeKey.COSTUME_STORAGE);
                    player.dialogue(new OptionsDialogue("You can only withdraw this item once you complete the set, continue?",
                            new Option("Deposit", () -> {
                                depositInventory(player);
                                costumeStorage.open(player, buildable == Buildable.OAK_TREASURE_CHEST ? 1 : buildable == Buildable.TEAK_TREASURE_CHEST ? 2 : buildable == Buildable.MAHOGANY_TREASURE_CHEST ? 5 : -1, buildable);
                            }),
                            new Option("Do not deposit", () -> {
                                costumeStorage.open(player, buildable == Buildable.OAK_TREASURE_CHEST ? 1 : buildable == Buildable.TEAK_TREASURE_CHEST ? 2 : buildable == Buildable.MAHOGANY_TREASURE_CHEST ? 5 : -1, buildable);
                            })
                    ));
                } else {
                    depositInventory(player);
                }
            };
        });
        InterfaceHandler.register(674, h -> {
            h.actions[0] = (DefaultAction) (p, option, slot, itemId) -> {
                if (option == 1) {
                    if (Config.COSTUME_DEPOSIT_SET.get(p) == 0) {
                        CostumeStorage costumeStorage = p.getTemporaryAttribute(AttributeKey.COSTUME_STORAGE);
                        depositItem(p, p.getInventory().get(slot), true, p.getTemporaryAttribute(AttributeKey.COSTUME_BUILDABLE), costumeStorage);
                        costumeStorage.sendItems(p);
                    } else {
                        CostumeStorage costumeStorage = p.getTemporaryAttribute(AttributeKey.COSTUME_STORAGE);
                        depositSet(p, p.getInventory().get(slot), p.getTemporaryAttribute(AttributeKey.COSTUME_BUILDABLE), costumeStorage);
                        costumeStorage.sendItems(p);
                    }
                } else {
                    new Item(itemId).examine(p);
                }
            };
        });
    }
}
