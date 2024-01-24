package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 5/26/2023
 */
public abstract class ChargeableItem {

    protected abstract int getChargedId();

    protected abstract int getUnchargedId();

    protected abstract int getChargeItem();

    protected abstract int getMaxCharges();

    protected abstract int getChargesPerItem();

    protected abstract boolean doesReturnItems();

    protected static void register(ChargeableItem chargeableItem) {
        ItemAction.registerInventory(chargeableItem.getChargedId(), "check", ChargeableItem::checkCharges);
        ItemAction.registerInventory(chargeableItem.getChargedId(), "uncharge", ((player, item) -> uncharge(chargeableItem, player, item)));

        ItemAction.registerEquipment(chargeableItem.getChargedId(), "check", ChargeableItem::checkCharges);

        if (chargeableItem.getUnchargedId() > 0) {
            ItemAction.registerInventory(chargeableItem.getUnchargedId(), "check", ChargeableItem::checkCharges);
            ItemAction.registerEquipment(chargeableItem.getUnchargedId(), "check", ChargeableItem::checkCharges);
        }
        if (chargeableItem.getChargeItem() > 0) {
            ItemItemAction.register(chargeableItem.getUnchargedId(), chargeableItem.getChargeItem(), ((player, primary, secondary) -> charge(chargeableItem, player, primary, secondary)));
            ItemItemAction.register(chargeableItem.getChargedId(), chargeableItem.getChargeItem(), ((player, primary, secondary) -> charge(chargeableItem, player, primary, secondary)));
        }
    }

    private static void charge(ChargeableItem chargeableItem, Player player, Item item, Item chargeItem) {
        String itemName = item.getDef().name.replace(" (uncharged)", "");
        String chargeItemName = chargeItem.getDef().name;
        if (item.getCharges() >= chargeableItem.getMaxCharges()) {
            player.sendMessage("Your " + itemName + " is already fully charged.");
            return;
        }
        player.integerInput("How many " + chargeItemName.toLowerCase() + "s would you like to charge the " + itemName + " with?", value -> {
            int charges = Math.min(Math.min(chargeItem.getAmount() * chargeableItem.getChargesPerItem(), chargeableItem.getMaxCharges() - item.getCharges()), value * chargeableItem.getChargesPerItem());
            if (charges <= 0)
                return;
            int amountToRemove = (int) Math.ceil((double) charges/chargeableItem.getChargesPerItem());
            chargeItem.remove(amountToRemove);
            item.setCharges(item.getCharges() + charges);
            item.setId(chargeableItem.getChargedId());
            player.sendFilteredMessage("You charge your " + itemName + " with " + NumberUtils.formatNumber(amountToRemove) + " " + chargeItemName + "s.");
        });
    }

    protected static void uncharge(ChargeableItem chargeableItem, Player player, Item item) {
        String itemName = item.getDef().name.replace(" (uncharged)", "").toLowerCase();
        String chargeItemName =  ItemDef.get(chargeableItem.getChargeItem()).name.toLowerCase();
        player.dialogue(
                new YesNoDialogue("Are you sure you'd like to uncharge the " + itemName + "?",
                        "The " + chargeItemName + "s within will " + (chargeableItem.doesReturnItems() ? "" : Color.RED.wrap("not ")) + "be returned to you."
                        , item, () -> {
                    removeCharges(chargeableItem, player, item);
                })
        );
    }

    public static void removeCharge(ChargeableItem chargeableItem, Player player, Item item, int amount) {
        if (amount >= item.getCharges()) {
            int uncharged = chargeableItem.getUnchargedId();
            if (uncharged > 0) {
                item.removeCharges();
                item.setId(uncharged);
                player.sendMessage(Color.RED.wrap("Your " + ItemDef.get(chargeableItem.getChargedId()).name + " has run out of charges."));
            } else {
                item.remove();
                player.sendMessage(Color.RED.wrap("Your " + ItemDef.get(chargeableItem.getChargedId()).name + " has run out of charges and turned to dust."));
            }
        } else {
            item.setCharges(item.getCharges() - amount);
        }
    }

    protected static void removeCharges(ChargeableItem chargeableItem, Player player, Item item) {
        if (chargeableItem.doesReturnItems() && (!player.getInventory().hasId(chargeableItem.getChargeItem()) && !player.getInventory().hasFreeSlots(1))) {
            player.dialogue(new MessageDialogue("You do not have enough inventory space to remove the charges from that item."));
            return;
        }
        int amountToRemove = item.getCharges();
        item.removeCharges();
        item.setId(chargeableItem.getUnchargedId());
        if (chargeableItem.doesReturnItems())
            player.getInventory().add(chargeableItem.getChargeItem(), (int) Math.floor((double) amountToRemove/chargeableItem.getChargesPerItem()));
    }

    protected static void checkCharges(Player player, Item item) {
        player.sendMessage("Your " + item.getDef().name.replace(" (uncharged)", "") + " currently holds " + NumberUtils.formatNumber(item.getCharges()) + " charges.");
    }
}
