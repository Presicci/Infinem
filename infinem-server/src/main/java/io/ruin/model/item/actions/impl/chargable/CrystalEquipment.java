package io.ruin.model.item.actions.impl.chargable;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;

import java.util.Arrays;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/17/2023
 */
public enum CrystalEquipment {
    // Tools
    PICKAXE(23680, 23682, Items.DRAGON_PICKAXE, 23953),
    AXE(23673, 23675, Items.DRAGON_AXE, 23953),
    HARPOON(23762, 23764, Items.DRAGON_HARPOON, 23953),
    // Armor
    SHIELD(23991, 23993, 4207),
    HELM(23971, 23973, 23956),
    BODY(23975, 23977, 23956),
    LEGS(23979, 23981, 23956),
    // Weapons
    HALBERD(23987, 23989, 4207),
    BOW(23983, 23985, 4207),
    BLADE_OF_SAELDOR(23995, 23997),
    BOW_OF_FAERDHINEN(25865, 25862);

    private final int activeId, inactiveId;
    private final int[] revertIds;

    CrystalEquipment(int activeId, int inactiveId, int... revertIds) {
        this.activeId = activeId;
        this.inactiveId = inactiveId;
        this.revertIds = revertIds;
    }

    private static final CrystalEquipment[] CRYSTAL_WEAPONS = {
            HALBERD, BOW, BOW_OF_FAERDHINEN, BLADE_OF_SAELDOR
    };

    private static final CrystalEquipment[] CRYSTAL_ARMOUR = {
            SHIELD, HELM, BODY, LEGS
    };

    private static final int SHARD = 23962;
    private static final int MAX_CHARGES = 20000;
    private static final int[] SIGNETS = { 23943, 25543, 25545 };

    private static void check(Player player, Item item) {
        player.sendMessage("Your " + item.getDef().name + " currently has " + NumberUtils.formatNumber(item.getCharges()) + " charges.");
    }

    private void charge(Player player, Item crystalItem, Item shards) {
        int currentCharges = crystalItem.getCharges();
        int shardsToUse = Math.min((MAX_CHARGES - currentCharges) / 100, shards.getAmount());
        if (shardsToUse == 0) {
            player.sendMessage("Your " + crystalItem.getDef().name + " already has maximum charges.");
            return;
        }
        player.integerInput("How many shards would you like to add? (Up to " + NumberUtils.formatNumber(shardsToUse) + ")", (input) -> {
            int finalShard = Math.min(input, shardsToUse);
            int chargesToAdd = 100 * finalShard;
            if (crystalItem.getId() == inactiveId) {
                crystalItem.setId(activeId);
            }
            crystalItem.setCharges(currentCharges + chargesToAdd);
            shards.remove(finalShard);
            check(player, crystalItem);
        });
    }

    private void uncharge(Player player, Item item) {
        int charges = item.getCharges();
        int shards = (int) Math.floor(charges / 100D);
        if (shards > 0 & !player.getInventory().hasRoomFor(Items.CRYSTAL_SHARD, shards)) {
            player.sendMessage("You need more space to uncharge that.");
            return;
        }
        String name = item.getDef().name.toLowerCase();
        player.dialogue(
                new MessageDialogue("Uncharging the " + name + " will return " + shards + " shards to you.<brWould you like to continue?"),
                new OptionsDialogue("Uncharge the " + name + "?",
                        new Option("Yes", () -> {
                            item.removeCharges();
                            item.setId(inactiveId);
                            if (shards > 0) player.getInventory().add(Items.CRYSTAL_SHARD, shards);
                        }),
                        new Option("No")
                )
        );
    }

    private void revert(Player player, Item item) {
        if (revertIds.length > 1) {
            int slotsRequired = revertIds.length - 1;
            if (!player.getInventory().hasFreeSlots(slotsRequired)) {
                player.sendMessage("You need " + slotsRequired + " free slots to revert this item.");
                return;
            }
        }
        String name = item.getDef().name.toLowerCase();
        player.dialogue(
                new MessageDialogue("Reverting the " + name + " will return the seed"
                        + ((revertIds.length > 1) ? " and the " + ItemDefinition.get(revertIds[1]).name : "")
                        + ". Crystal shards will NOT be returned.<br>Continue?"
                ),
                new OptionsDialogue("Revert the " + name + "?",
                        new Option("Yes", () -> {
                            item.removeCharges();
                            item.setId(revertIds[0]);
                            if (revertIds.length > 1) {
                                for (int index = 1; index < revertIds.length; index++) {
                                    player.getInventory().add(revertIds[index]);
                                }
                            }
                        }),
                        new Option("No")
                )
        );
    }

    public void removeCharge(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (item == null || item.getId() != activeId)
            item = player.getInventory().findItemIgnoringAttributes(activeId, false);
        removeCharge(player, item);
    }

    public void removeCharge(Player player, Item item) {
        if (item != null && item.getId() == activeId) {
            int currentCharges = item.getCharges();
            if (currentCharges <= 0) {
                System.err.println("Tried to remove charge with no available charges! player: " + player.getName() + ", item: " + this);
                return;
            }
            // Signet gives a 10% chance to not use charges
            if (hasSignet(player) && Random.rollDie(10, 1))
                return;
            item.setCharges(currentCharges - 1);
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
            return tool.getCharges() > 0;
        }
        return false;
    }

    static {
        for (CrystalEquipment equipment : CrystalEquipment.values()) {
            ItemAction.registerInventory(equipment.activeId, "check", CrystalEquipment::check);
            ItemAction.registerInventory(equipment.activeId, "uncharge", equipment::uncharge);
            if (equipment != BLADE_OF_SAELDOR)  // Blade of saeldor doesn't have equip options for some reason
                ItemAction.registerEquipment(equipment.activeId, "check", CrystalEquipment::check);
            ItemItemAction.register(equipment.activeId, SHARD, equipment::charge);
            ItemItemAction.register(equipment.inactiveId, SHARD, equipment::charge);
            if (equipment.revertIds.length > 0) {
                ItemAction.registerInventory(equipment.activeId, "revert", equipment::revert);
                ItemAction.registerInventory(equipment.inactiveId, "revert", equipment::revert);
            }
            if (Arrays.stream(CRYSTAL_WEAPONS).anyMatch(e -> e == equipment)) {
                ItemDefinition.get(equipment.activeId).addPreTargetDefendListener((player, item, hit, target) -> equipment.removeCharge(player, item));
            }
            if (Arrays.stream(CRYSTAL_ARMOUR).anyMatch(e -> e == equipment)) {
                ItemDefinition.get(equipment.activeId).addPostDamageListener((player, item, hit) -> {
                    if (hit.damage > 0) equipment.removeCharge(player, item);
                });
            }
        }
    }
}
