package io.ruin.model.skills.farming;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/30/2022
 */
public class BottomlessCompostBucket {

    private static final int[] COMPOSTS = { 6032, 6034, 21483 };
    private static final int EMPTY = 22994, FILLED = 22997;


    private static void check(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        if (charges == 0) {
            player.dialogue(new ItemDialogue().one(item.getId(), "The bucket is empty."));
            return;
        }
        int type = getType(item);
        player.dialogue(new ItemDialogue().one(item.getId(), "The bucket has " + (charges - (type * 10000)) + " buckets of " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost") + "."));
    }

    private static void fillCheck(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        if (charges == 0) {
            player.dialogue(
                    new ItemDialogue().one(item.getId(), "The bucket is empty. Would you like to add compost to it?"),
                    new OptionsDialogue(
                            new Option("Yes", () -> fillBucket(player, item)),
                            new Option("No")
                    )
            );
            return;
        }
        int type = getType(item);
        player.dialogue(
                new ItemDialogue().one(item.getId(), "The bucket has " + (charges - (type * 10000)) + " buckets of " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost")
                + ". Would you like to add compost to it?"),
                new OptionsDialogue(
                        new Option("Yes", () -> fillBucket(player, item, type)),
                        new Option("No")
                )
        );
    }

    private static void empty(Player player, Item item) {
        player.dialogue(
                new MessageDialogue("You are about to remove all buckets of compost from your Bottomless compost bucket and <col=ff0000>get nothing back</col>. Are you sure you want to continue?"),
                new OptionsDialogue(
                        new Option("Yes", () -> {
                            AttributeExtensions.setCharges(item, 0);
                            item.setId(EMPTY);
                        }),
                        new Option("No")
                )
        );
    }

    public static void removeCharge(Player player, Item item) {
        int charges = AttributeExtensions.getCharges(item);
        int type = getType(item);
        if (--charges <= type * 10000) {
            AttributeExtensions.setCharges(item, 0);
            item.setId(EMPTY);
            player.sendMessage("Your Bottomless compost bucket has run out of compost.");
            return;
        }
        AttributeExtensions.setCharges(item, charges);
    }

    public static int getType(Item item) {
        return (AttributeExtensions.getCharges(item) - 1) / 10000;
    }

    private static void fillBucket(Player player, Item item) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Compost", () -> fillBucket(player, item, 0)),
                        new Option("Supercompost", () -> fillBucket(player, item, 1)),
                        new Option("Ultracompost", () -> fillBucket(player, item, 2))
                )
        );
    }

    public static int fillFromBin(Player player, Item item, int amount, int type) {
        int charges = AttributeExtensions.getCharges(item);
        int amountToAdd = amount * 2;
        if ((charges > (10000 + type * 10000) || charges <= (type * 10000)) && charges != 0) {
            type = getType(item);
            player.dialogue(new MessageDialogue("You already have " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost")
                    + " in this bucket. If you want to add a different compost you have to empty it first."));
            return 0;
        }
        if (charges == (10000 + type * 10000) || type > 2) {
            player.dialogue(new MessageDialogue("Somehow you have filled the bottomless bucket, suppose it wasn't bottomless after all."));
            return 0;
        }
        if (amountToAdd > 10000) {
            amountToAdd = 10000;
        }
        if ((amountToAdd + charges) > (10000 + type * 10000)) {
            amountToAdd = 10000 - (charges - (10000 * type));
        }
        if (item.getId() == EMPTY)
            item.setId(FILLED);
        AttributeExtensions.setCharges(item, charges + amountToAdd + (type * 10000));
        return amountToAdd / 2;
    }

    private static void fillBucket(Player player, Item item, int type) {
        int charges = AttributeExtensions.getCharges(item);
        if ((charges > (10000 + type * 10000) || charges <= (type * 10000)) && charges != 0) {
            type = getType(item);
            player.dialogue(new MessageDialogue("You already have " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost")
                    + " in this bucket. If you want to add a different compost you have to empty it first."));
            return;
        }
        if (charges == (10000 + type * 10000) || type > 2) {
            player.dialogue(new MessageDialogue("Somehow you have filled the bottomless bucket, suppose it wasn't bottomless after all."));
            return;
        }
        int amountToFill = player.getInventory().getAmount(COMPOSTS[type]) * 2;
        amountToFill += player.getInventory().getAmount(ItemDef.get(COMPOSTS[type]).notedId) * 2;
        if (amountToFill == 0) {
            player.dialogue(new MessageDialogue("You don't have any " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost")
                    + " to add to the bucket."));
            return;
        }
        if (amountToFill > 10000) {
            amountToFill = 10000;
        }
        if ((amountToFill + charges) > (10000 + type * 10000)) {
            amountToFill = 10000 - (charges - (10000 * type));
        }
        int amountToRemove = amountToFill;
        if (amountToRemove <= player.getInventory().getAmount(COMPOSTS[type]) * 2) {
            player.getInventory().remove(COMPOSTS[type], amountToRemove/2);
        } else {
            amountToRemove -= player.getInventory().getAmount(COMPOSTS[type]) * 2;
            player.getInventory().remove(COMPOSTS[type], player.getInventory().getAmount(COMPOSTS[type]));
            player.getInventory().remove(ItemDef.get(COMPOSTS[type]).notedId, amountToRemove / 2);
        }
        if (item.getId() == EMPTY)
            item.setId(FILLED);
        AttributeExtensions.setCharges(item, charges + amountToFill + (type * 10000));
    }

    private static void addOne(Player player, Item item, Item used, int type) {
        int charges = AttributeExtensions.getCharges(item);
        if ((charges > (10000 + type * 10000) || charges <= (type * 10000)) && charges != 0) {
            type = getType(item);
            player.dialogue(new MessageDialogue("You already have " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost")
                    + " in this bucket. If you want to add a different compost you have to empty it first."));
            return;
        }
        if (charges == (10000 + type * 10000) || type > 2) {
            player.dialogue(new MessageDialogue("Somehow you have filled the bottomless bucket, suppose it wasn't bottomless after all."));
            return;
        }
        if (used.getDef().isNote() || used.getDef().stackable) {
            int amountToFill = player.getInventory().getAmount(used.getId()) * 2;
            if (amountToFill == 0) {
                player.dialogue(new MessageDialogue("You don't have any " + (type == 0 ? "compost" : type == 1 ? "supercompost" : "ultracompost")
                        + " to add to the bucket."));
                return;
            }
            if (amountToFill > 10000) {
                amountToFill = 10000;
            }
            if ((amountToFill + charges) > (10000 + type * 10000)) {
                amountToFill = 10000 - (charges - (10000 * type));
            }
            player.getInventory().remove(used.getId(), amountToFill / 2);
            if (item.getId() == EMPTY)
                item.setId(FILLED);
            AttributeExtensions.setCharges(item, amountToFill + (type * 10000));
        } else {
            if (item.getId() == EMPTY) {
                item.setId(FILLED);
                charges = type * 10000;
            }
            AttributeExtensions.setCharges(item, charges + 2);
            player.getInventory().remove(used);
        }
    }

    static {
        for (int index = 0; index < COMPOSTS.length; index++) {
            int itemId = COMPOSTS[index];
            int noteId = ItemDef.get(itemId).notedId;
            int finalIndex = index;
            ItemItemAction.register(itemId, EMPTY, ((player, primary, secondary) -> addOne(player, secondary, primary, finalIndex)));
            ItemItemAction.register(itemId, FILLED, ((player, primary, secondary) -> addOne(player, secondary, primary, finalIndex)));
            ItemItemAction.register(noteId, EMPTY, ((player, primary, secondary) -> addOne(player, secondary, primary, finalIndex)));
            ItemItemAction.register(noteId, FILLED, ((player, primary, secondary) -> addOne(player, secondary, primary, finalIndex)));
        }
        ItemAction.registerInventory(EMPTY, "fill", BottomlessCompostBucket::fillBucket);
        ItemAction.registerInventory(EMPTY, "check", BottomlessCompostBucket::check);
        ItemAction.registerInventory(FILLED, "fill/check", BottomlessCompostBucket::fillCheck);
        ItemAction.registerInventory(FILLED, "empty", BottomlessCompostBucket::empty);
    }
}
