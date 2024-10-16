package io.ruin.model.item.containers.bank;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.handlers.TabEquipment;
import io.ruin.model.inter.handlers.TabInventory;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/16/2024
 */
public class BankEquipment {

    private static void clickEquippedItem(Player player, int option, int equipmentSlot) {
        Item item = player.getEquipment().get(equipmentSlot);
        if (option == 2) {
            player.getBank().deposit(item, item.getAmount(), true);
        } else {
            TabEquipment.itemAction(player, option, equipmentSlot);
        }
    }

    static {
        InterfaceHandler.register(Interface.BANK, h -> {
            h.actions[84] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_HAT);
            h.actions[85] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_CAPE);
            h.actions[86] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_AMULET);
            h.actions[87] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_WEAPON);
            h.actions[88] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_CHEST);
            h.actions[89] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_SHIELD);
            h.actions[90] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_LEGS);
            h.actions[91] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_HANDS);
            h.actions[92] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_FEET);
            h.actions[93] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_RING);
            h.actions[94] = (OptionAction) (player, option) -> clickEquippedItem(player, option, Equipment.SLOT_AMMO);
        });
        InterfaceHandler.register(Interface.BANK_INVENTORY, h -> {
            h.actions[4] = new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    Item item = player.getInventory().get(slot, itemId);
                    if (item == null)
                        return;
                    if (option == 1)
                        player.getEquipment().equip(item);
                    else if (option == 9)
                        player.getBank().deposit(item, item.getAmount(), true);
                    else
                        item.examine(player);
                }

                @Override
                public void handleDrag(Player player, int fromSlot, int fromItemId, int toInterfaceId, int toChildId, int toSlot, int toItemId) {
                    TabInventory.drag(player, fromSlot, toSlot);
                }
            };
        });
    }
}
