package io.ruin.model.content.tasksystem.relics.impl.fragments;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.item.Item;
import io.ruin.model.item.attributes.AttributeExtensions;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 12/29/2024
 */
public class RelicFragmentInterface {

    public static void open(Player player) {
        player.openInterface(InterfaceType.MAIN, 1012);
        player.getPacketSender().sendAccessMask(1012, 3, 0, 100, AccessMasks.ClickOp1);
        sendFragments(player);
        player.getPacketSender().sendInventoryOverlay("Socket");
    }

    public static void updateFragments(Player player) {
        player.getPacketSender().sendClientScript(10082, "is", 0, getString(player));
        updateFragmentSprites(player);
    }

    private static void sendFragments(Player player) {
        player.getPacketSender().sendClientScript(10082, "is", 1, getString(player));
        updateFragmentSprites(player);
    }

    private static String getString(Player player) {
        StringBuilder sb = new StringBuilder();
        for (FragmentType type : FragmentType.values()) {
            if (sb.length() > 0) sb.append("|");
            sb.append(type);
            sb.append("|");
            sb.append(getFragmentString(player, type));
        }
        return sb.toString();
    }

    private static String getFragmentString(Player player, FragmentType type) {
        Item fragmentItem = player.getRelicFragmentManager().getFragment(type);
        if (fragmentItem == null) return "";
        return FragmentItem.getModString(fragmentItem);
    }

    private static void updateFragmentSprites(Player player) {
        for (FragmentType type : FragmentType.values()) {
            if (player.getRelicFragmentManager().getFragment(type) == null) {
                player.getPacketSender().sendClientScript(10083, "iii", 1012 << 16 | 3, type.getSpriteIndex(), type.getInactiveSprite());
            } else {
                player.getPacketSender().sendClientScript(10083, "iii", 1012 << 16 | 3, type.getSpriteIndex(), type.getActiveSprite());
            }
        }
    }

    private static void removeFragment(Player player, int slot) {
        FragmentType type = FragmentType.BY_SPRITE_INDEX.get(slot);
        if (type == null) {
            updateFragments(player);
            return;
        }
        Item fragment = player.getRelicFragmentManager().getFragment(type);
        if (fragment == null) {
            updateFragments(player);
            return;
        }
        if (!player.getInventory().hasFreeSlots(1)) {
            player.sendMessage("You don't have enough inventory space to remove that fragment.");
            return;
        }
        player.getInventory().add(fragment);
        player.getRelicFragmentManager().removeFragment(type);
        updateFragments(player);
    }

    private static void socketFragment(Player player, int slot) {
        Item item = player.getInventory().get(slot);
        if (item == null || !FragmentItem.isFragment(item)) {
            player.sendMessage("Only relic fragments are able to be socketed.");
            return;
        }
        FragmentType type = FragmentItem.getType(item);
        if (type == null) {
            player.sendMessage("Null! ");
            AttributeExtensions.printAttributes(item);
            return;
        }
        if (player.getRelicFragmentManager().getFragment(type) == null) {
            player.getRelicFragmentManager().putFragment(type, item);
            item.remove();
        } else {
            Item prevFragment = player.getRelicFragmentManager().getFragment(type);
            player.getRelicFragmentManager().putFragment(type, item);
            item.remove();
            player.getInventory().add(prevFragment);
        }
        updateFragments(player);
    }

    static {
        InterfaceHandler.register(1012, h -> {
            h.actions[3] = (SlotAction) RelicFragmentInterface::removeFragment;
        });
        InterfaceHandler.register(Interface.GENERIC_INVENTORY_OVERLAY, h -> {
            h.interfaceOverlayAction(1012, 0, (DefaultAction) (player, option, slot, itemId) -> {
                if (option == 1) socketFragment(player, slot);
                else player.getInventory().get(slot).examine(player);
            });
        });
    }
}