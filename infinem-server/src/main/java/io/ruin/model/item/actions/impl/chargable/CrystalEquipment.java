package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/17/2023
 */
@AllArgsConstructor
public enum CrystalEquipment {

    HARPOON(23762, 23764);

    private final int activeId, inactiveId;

    private static final int SHARD = 23962;
    private static final int MAX_CHARGES = 20000;

    static {
        for (CrystalEquipment equipment : CrystalEquipment.values()) {
            ItemAction.registerInventory(equipment.activeId, "check", CrystalEquipment::check);
            ItemAction.registerEquipment(equipment.activeId, "check", CrystalEquipment::check);
            ItemItemAction.register(equipment.activeId, SHARD, equipment::charge);
            ItemItemAction.register(equipment.inactiveId, SHARD, equipment::charge);
        }
    }

    private static void check(Player player, Item item) {
        player.sendMessage("Your " + item.getDef().name + " currently has " + NumberUtils.formatNumber(item.getAttributeInt(AttributeTypes.CHARGES)) + " charges.");
    }

    private void charge(Player player, Item crystalItem, Item shards) {
        int currentCharges = AttributeExtensions.getCharges(crystalItem);
        int shardsToUse = Math.min((MAX_CHARGES - currentCharges) / 100, shards.getAmount());
        if (shardsToUse == 0) {
            player.sendMessage("Your " + crystalItem.getDef().name + " already has maximum charges.");
            return;
        }
        int chargesToAdd = 100 * shardsToUse;
        if (crystalItem.getId() == inactiveId) {
            crystalItem.setId(activeId);
        }
        crystalItem.putAttribute(AttributeTypes.CHARGES, currentCharges + chargesToAdd);
        shards.remove(shardsToUse);
        player.dialogue(new ItemDialogue().one(crystalItem.getId(), "Your " + crystalItem.getDef().name + " now holds " + NumberUtils.formatNumber(AttributeExtensions.getCharges(crystalItem)) + " charges."));
    }

    public void removeCharge(Player player) {
        Item tool = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (tool == null || tool.getId() != activeId)
            tool = player.getInventory().findItemIgnoringAttributes(activeId, false);
        if ((tool != null && tool.getId() == activeId)) {
            int currentCharges = AttributeExtensions.getCharges(tool);
            if (currentCharges <= 0) {
                System.err.println("Tried to remove charge with no available charges! player: " + player.getName() + ", tool: " + this.toString());
                return;
            }
            AttributeExtensions.deincrementCharges(tool, 1);
            if (currentCharges - 1 <= 0) {
                tool.setId(inactiveId);
                player.sendMessage("Your " + tool.getDef().name + " has run out of charges.");
            }
        }
    }

    public boolean hasCharge(Player player) {
        Item tool = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (tool == null || tool.getId() != activeId)
            tool = player.getInventory().findItemIgnoringAttributes(activeId, false);
        if ((tool != null && tool.getId() == activeId)) {
            return AttributeExtensions.getCharges(tool) > 0;
        }
        return false;
    }
}
