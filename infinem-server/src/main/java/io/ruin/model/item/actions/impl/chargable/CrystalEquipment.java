package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.attributes.AttributeTypes;
import io.ruin.model.item.containers.Equipment;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/17/2023
 */
@AllArgsConstructor
public enum CrystalEquipment {
    // Tools
    HARPOON(23762, 23764),
    // Weapons
    BLADE_OF_SAELDOR(23995, 23997),
    BOW_OF_FAERDHINEN(25865, 25862);

    private final int activeId, inactiveId;

    private static final int SHARD = 23962;
    private static final int MAX_CHARGES = 20000;
    private static final int[] SIGNETS = { 23943, 25543, 25545 };

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
        removeCharge(player, player.getEquipment().get(Equipment.SLOT_WEAPON));
    }

    public void removeCharge(Player player, Item item) {
        if (item == null || item.getId() != activeId)
            item = player.getInventory().findItemIgnoringAttributes(activeId, false);
        if ((item != null && item.getId() == activeId)) {
            int currentCharges = AttributeExtensions.getCharges(item);
            if (currentCharges <= 0) {
                System.err.println("Tried to remove charge with no available charges! player: " + player.getName() + ", item: " + this);
                return;
            }
            // Signet gives a 10% chance to not use charges
            if (hasSignet(player) && Random.rollDie(10, 1))
                return;
            AttributeExtensions.deincrementCharges(item, 1);
            if (currentCharges - 1 <= 0) {
                item.setId(inactiveId);
                player.sendMessage("Your " + item.getDef().name + " has run out of charges.");
            }
        }
    }

    private boolean hasSignet(Player player) {
        // Signet effect does not work on these
        if (this == BOW_OF_FAERDHINEN || this == BLADE_OF_SAELDOR)
            return false;
        Item ring = player.getEquipment().get(Equipment.SLOT_RING);
        if (ring == null) return false;
        return Arrays.stream(SIGNETS).anyMatch(s -> s == ring.getId());
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

    private static final CrystalEquipment[] CRYSTAL_WEAPONS = {
            BOW_OF_FAERDHINEN, BLADE_OF_SAELDOR
    };

    static {
        for (CrystalEquipment equipment : CrystalEquipment.values()) {
            ItemAction.registerInventory(equipment.activeId, "check", CrystalEquipment::check);
            if (equipment != BLADE_OF_SAELDOR)  // Blade of saeldor doesn't have equip options for some reason
                ItemAction.registerEquipment(equipment.activeId, "check", CrystalEquipment::check);
            ItemItemAction.register(equipment.activeId, SHARD, equipment::charge);
            ItemItemAction.register(equipment.inactiveId, SHARD, equipment::charge);
            if (Arrays.stream(CRYSTAL_WEAPONS).anyMatch(e -> e == equipment)) {
                ItemDefinition.get(equipment.activeId).addPreTargetDefendListener((player, item, hit, target) -> equipment.removeCharge(player, item));
            }
        }
    }
}
