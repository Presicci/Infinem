package io.ruin.model.item.actions;

import io.ruin.cache.def.ObjectDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;

import java.util.HashMap;

public interface ItemObjectAction {

    void handle(Player player, Item item, GameObject obj);

    /**
     * Register actions without a specific item.
     */

    static void register(int objectId, ItemObjectAction action) {
        ObjectDefinition objDef = ObjectDefinition.get(objectId);
        objDef.defaultItemAction = action;
    }

    static void register(String objectName, ItemObjectAction action) {
        ObjectDefinition.forEach(objectDef -> {
            if(objectDef.name.equalsIgnoreCase(objectName))
                register(objectDef.id, action);
        });
    }

    static void register(GameObject obj, ItemObjectAction action) {
        obj.defaultItemAction = action;
    }

    /**
     * Register actions with specific items.
     */

    static void register(int itemId, int objectId, ItemObjectAction action) {
        ObjectDefinition objDef = ObjectDefinition.get(objectId);
        if(objDef.itemActions == null)
            objDef.itemActions = new HashMap<>();
        objDef.itemActions.put(itemId, action);
    }

    static void register(int itemId, String objectName, ItemObjectAction action) {
        ObjectDefinition.forEach(objDef -> {
            if(objDef.name.equalsIgnoreCase(objectName))
                register(itemId, objDef.id, action);
        });
    }

    static void register(int itemId, GameObject obj, ItemObjectAction action) {
        if(obj.itemActions == null)
            obj.itemActions = new HashMap<>();
        obj.itemActions.put(itemId, action);
    }

    /**
     * Handle actions
     */

    static void handleAction(Player player, Item item, GameObject obj) {
        /**
         * Actions associated with the varpbit set showId.
         */
        ObjectDefinition configDef = obj.getConfigDef(player);
        if(configDef.itemActions != null) {
            ItemObjectAction action = configDef.itemActions.get(item.getId());
            if(action != null) {
                action.handle(player, item, obj);
                return;
            }

        }
        if(configDef.defaultItemAction != null) {
            configDef.defaultItemAction.handle(player, item, obj);
            return;
        }
        /**
         * Actions unique to the given obj.
         */
        if(obj.itemActions != null) {
            ItemObjectAction action = obj.itemActions.get(item.getId());
            if(action != null) {
                action.handle(player, item, obj);
                return;
            }
        }
        if(obj.defaultItemAction != null) {
            obj.defaultItemAction.handle(player, item, obj);
            return;
        }
        /**
         * Actions shared by the given obj.
         */
        ObjectDefinition def = obj.getDef();
        if(def.itemActions != null) {
            ItemObjectAction action = def.itemActions.get(item.getId());
            if(action != null) {
                action.handle(player, item, obj);
                return;
            }

        }
        if(def.defaultItemAction != null) {
            def.defaultItemAction.handle(player, item, obj);
            return;
        }
        /**
         * No action, hehe.
         */
        player.sendMessage("Nothing interesting happens.");
    }

}