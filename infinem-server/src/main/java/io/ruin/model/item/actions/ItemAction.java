package io.ruin.model.item.actions;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ItemAction {

    void handle(Player player, Item item);

    /**
     * Inventory
     */

    static void registerInventory(int itemId, int option, ItemAction action) {
        ItemDefinition def = ItemDefinition.get(itemId);
        if(def.inventoryActions == null)
            def.inventoryActions = new ItemAction[5];
        def.inventoryActions[option - 1] = action;
    }

    static boolean registerInventory(int itemId, String optionName, ItemAction action) {
        int option = ItemDefinition.get(itemId).getOption(optionName);
        if(option == -1)
            return false;
        registerInventory(itemId, option, action);
        return true;
    }

    static void registerInventory(int itemId, Consumer<ItemAction[]> actionsConsumer) {
        ItemAction[] actions = new ItemAction[5 + 1];
        actionsConsumer.accept(actions);
        ItemDefinition.get(itemId).inventoryActions = Arrays.copyOfRange(actions, 1, actions.length);
    }

    /**
     * Equipment
     */

    static void registerEquipment(int itemId, int option, ItemAction action) {
        ItemDefinition def = ItemDefinition.get(itemId);
        if(def.equipmentActions == null)
            def.equipmentActions = new ItemAction[9];
        def.equipmentActions[option - 1] = action;
    }

    static boolean registerEquipment(int itemId, String optionName, ItemAction action) {
        ItemDefinition def = ItemDefinition.get(itemId);
        int option = -1;
        for(int i = 451; i < 459; i++) { //tbh might go past 458, but doubt it..
            if (def.attributes == null) {
                System.err.println(itemId + " doesn't have equip params and trying to register equipment action!");
                return false;
            }
            String s = (String) def.attributes.get(i);
            if(optionName.equalsIgnoreCase(s)) {
                option = (i - 451) + 2; //plus two because "Remove" is always 1
                break;
            }
        }
        if(option == -1)
            return false;
        registerEquipment(itemId, option, action);
        return true;
    }

    static void registerEquipment(int itemId, Consumer<ItemAction[]> actionsConsumer) {
        ItemAction[] actions = new ItemAction[7 + 1];
        actionsConsumer.accept(actions);
        ItemDefinition.get(itemId).equipmentActions = Arrays.copyOfRange(actions, 1, actions.length);
    }

    /**
     * Inventory + Equipment
     */

    static void register(int itemId, BiConsumer<ItemAction[], ItemAction[]> actionsConsumer) {
        ItemAction[] invActions = new ItemAction[5 + 1];
        ItemAction[] equipActions = new ItemAction[6 + 1];
        actionsConsumer.accept(invActions, equipActions);
        ItemDefinition.get(itemId).inventoryActions = Arrays.copyOfRange(invActions, 1, invActions.length);
        ItemDefinition.get(itemId).equipmentActions = Arrays.copyOfRange(equipActions, 1, equipActions.length);
    }

    /**
     * Misc
     */

    static void registerTick(int itemId, ItemAction action) {
        //This applies for all equipped items.
        ItemDefinition.get(itemId).tickAction = action;
    }

    static void registerAttack(int itemId, ItemAction action) {
        //This applies for equipped weapon.
        ItemDefinition.get(itemId).attackAction = action;
    }

    static void registerDefend(int itemId, ItemAction action) {
        //This applies for all equipped items.
        ItemDefinition.get(itemId).defendAction = action;
    }

}