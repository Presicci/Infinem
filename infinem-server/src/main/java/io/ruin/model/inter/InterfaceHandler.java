package io.ruin.model.inter;

import com.google.common.collect.Maps;
import io.ruin.cache.def.InterfaceDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.actions.SimpleAction;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InterfaceHandler {

    public static final Map<Integer, InterfaceHandler> handlers = Maps.newConcurrentMap();

    public static final InterfaceHandler EMPTY_HANDLER = new InterfaceHandler();

    public static final Map<Integer, InterfaceHandler> GENERIC_INTERFACE_OVERLAY_ACTIONS = Maps.newConcurrentMap();

    public final int id;

    public final InterfaceAction[] actions;

    public BiConsumer<Player, Integer> closedAction;

    public InterfaceHandler() {
        this.id = -1;
        this.actions = new InterfaceAction[0];
    }

    protected InterfaceHandler(int id) {
        this.id = id;
        this.actions = new InterfaceAction[InterfaceDefinition.COUNTS[id]];
    }

    public static void register(int interfaceId, Consumer<InterfaceHandler> consumer) {
        InterfaceHandler handler = handlers.computeIfAbsent(interfaceId, InterfaceHandler::new);
        consumer.accept(handler);
    }

    public void action(int childID, InterfaceAction action) {
        actions[childID] = action;
    }

    public void action(WidgetInfo widgetInfo, InterfaceAction action) {
        action(widgetInfo.getChildId(), action);
    }

    public void simpleAction(int childID, SimpleAction action) {
        action(childID, action);
    }

    public void simpleAction(WidgetInfo widgetInfo, SimpleAction action) {
        simpleAction(widgetInfo.getChildId(), action);
    }

    public void interfaceOverlayAction(int companionInterfaceId, int childID, InterfaceAction action) {
        GENERIC_INTERFACE_OVERLAY_ACTIONS.computeIfAbsent(companionInterfaceId, InterfaceHandler::new).actions[childID] = action;
    }

    public static InterfaceAction getAction(Player player, int interfaceHash) {
        int interfaceId = interfaceHash >> 16;
        int childId = interfaceHash & 0xffff;
        if(childId == 65535)
            childId = -1;
        return getAction(player, interfaceId, childId);
    }

    public static InterfaceAction getAction(Player player, int interfaceId, int childId) {
        if(interfaceId < 0 || interfaceId >= InterfaceDefinition.COUNTS.length)
            return null;
        if(!player.isVisibleInterface(interfaceId)) {
            return null;
        }
        if (interfaceId == Interface.GENERIC_INVENTORY_OVERLAY) {
            for (int companionInterfaceId : GENERIC_INTERFACE_OVERLAY_ACTIONS.keySet()) {
                if (player.isVisibleInterface(companionInterfaceId)) {
                    InterfaceHandler handler = GENERIC_INTERFACE_OVERLAY_ACTIONS.get(companionInterfaceId);
                    if(handler == null)
                        return null;
                    if(childId < 0 || childId >= handler.actions.length)
                        return null;
                    return handler.actions[childId];
                }
            }
        }
        InterfaceHandler handler = handlers.get(interfaceId);
        if(handler == null)
            return null;
        if(childId < 0 || childId >= handler.actions.length)
            return null;
        return handler.actions[childId];
    }

}