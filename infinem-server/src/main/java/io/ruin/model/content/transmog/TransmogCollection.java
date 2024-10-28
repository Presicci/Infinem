package io.ruin.model.content.transmog;

import com.google.gson.annotations.Expose;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.NotificationInterface;
import io.ruin.model.inter.utils.Config;
import io.ruin.utility.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/6/2024
 */
public class TransmogCollection {

    @Getter @Expose private final Set<Integer> collectedTransmogs = new HashSet<>();
    @Expose private final Map<Integer, Integer> transmogs = new HashMap<>();
    private Map<Integer, Integer> previews = new HashMap<>();

    @Setter private Player player;

    public void addToCollection(UnlockableTransmog... unlockableTransmogs) {
        for (UnlockableTransmog t : unlockableTransmogs) {
            t.unlock(player, false);
        }
    }

    public void addToCollection(int... itemIds) {
        for (int id : itemIds) {
            addToCollection(id, false);
        }
    }

    public boolean addToCollection(int itemId, boolean showNotification) {
        if (collectedTransmogs.add(itemId)) {
            ItemDefinition def = ItemDefinition.get(itemId);
            player.sendMessage("New transmog added to your collection: " + Color.RED.wrap(def.name));
            if (showNotification && Config.TRANSMOG_POPUP.get(player) == 0) {
                player.getPacketSender().sendPopupNotification(0xff981f, "New Transmog!", Color.WHITE.wrap(def.name));
            }
            return true;
        }
        return false;
    }

    public boolean hasTransmog(int itemId) {
        return collectedTransmogs.contains(itemId);
    }

    public void applyTransmog() {
        Map<Integer, Integer> preview = previews;
        previews = new HashMap<>();
        for (int key : preview.keySet()) {
            int itemId = preview.getOrDefault(key, -2);
            if (itemId != -2) {
                if (itemId == -1) {
                    clearTransmog(key);
                } else if (!hasTransmog(itemId)) {
                    clearTransmog(key);
                } else {
                    setTransmog(itemId);
                }
            }
        }
        player.getAppearance().update();
    }

    public void clearPreview() {

    }

    public int getItemIdForSlot(TransmogSlot slot) {
        return transmogs.getOrDefault(slot.getEquipmentSlot(), -1);
    }

    protected void clearSlot(int slot) {
        if (previews.containsKey(slot)) {
            previews.remove(slot);
        } else {
            previews.put(slot, -1);
        }
        TransmogInterface.sendSlot(player, slot);
    }

    protected void setTransmogPreview(int itemId) {
        int slot = ItemDefinition.get(itemId).equipSlot;
        if (slot == -1) {
            player.sendMessage("Improper slot for: " + itemId);
            return;
        }
        previews.put(slot, itemId);
        TransmogInterface.sendSlot(player, slot);
    }

    protected void clearTransmog(int slot) {
        transmogs.remove(slot);
        TransmogInterface.sendSlot(player, slot);
    }

    protected void setTransmog(int itemId) {
        int slot = ItemDefinition.get(itemId).equipSlot;
        if (slot == -1) {
            player.sendMessage("Improper slot for: " + itemId);
            return;
        }
        transmogs.put(slot, itemId);
        TransmogInterface.sendSlot(player, slot);
    }

    public void clearCollection() {
        collectedTransmogs.clear();
    }

    public int getTransmogIdForSlot(int slot) {
        return transmogs.getOrDefault(slot, -1);
    }

    public int getPreviewIdForSlot(int slot) {
        return previews.getOrDefault(slot, -2);
    }

    public boolean hasTransmogs() {
        return !transmogs.isEmpty();
    }
}
