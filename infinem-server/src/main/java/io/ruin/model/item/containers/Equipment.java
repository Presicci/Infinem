package io.ruin.model.item.containers;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.RangedWeapon;
import io.ruin.model.combat.SetEffect;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.impl.MaxCapeVariants;
import io.ruin.model.item.actions.impl.chargable.Blowpipe;
import io.ruin.model.skills.construction.actions.CombatRoom;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Misc;

import java.util.Map;
import java.util.function.Predicate;

public class Equipment extends ItemContainer {

    public static final int SLOT_HAT = 0, SLOT_CAPE = 1, SLOT_AMULET = 2, SLOT_WEAPON = 3, SLOT_CHEST = 4,
            SLOT_SHIELD = 5, SLOT_LEGS = 7, SLOT_HANDS = 9, SLOT_FEET = 10, SLOT_RING = 12, SLOT_AMMO = 13;

    public int[] bonuses = new int[16];

    public double weight;

    public void equip(Item selectedItem) {
        ItemDefinition selectedDef = selectedItem.getDef();
        int equipSlot = selectedDef.equipSlot;
        if(equipSlot == -1 || selectedDef.equipOption == -1) {
            player.sendMessage("You can't wear this item.");
            return;
        }
        if(selectedDef.equipReqs != null) {
            for(int req : selectedDef.equipReqs) {
                int statId = req >> 8;
                int lvl = req & 0xff;
                Stat stat = player.getStats().get(statId);
                if (lvl > 99) {
                    if (!stat.hasVirtualLevel(lvl)) {
                        player.sendMessage("You need a virtual " + StatType.get(statId).name() + " level of " + lvl + " (" + Misc.abbreviateItemQuantity(Stat.getVirtualExperience(lvl)) + " xp) to equip this item.");
                        return;
                    }
                } else {
                    if (stat.fixedLevel < lvl) {
                        player.sendMessage("You need " + StatType.get(statId).descriptiveName + " level of " + lvl + " to equip this item.");
                        return;
                    }
                }
            }
        }
        if(selectedDef.maxType && !MaxCapeVariants.unlocked(player)) {
            player.sendMessage("You don't have the required stats to wear this.");
            return;
        }
        Predicate<Player> equipCheck = selectedDef.getCustomValueOrDefault("EQUIP_CHECK", null);
        if (equipCheck != null && !equipCheck.test(player))
            return;
        if(player.getDuel().isBlocked(selectedDef)) {
            player.sendMessage("That item cannot be equipped in this duel!");
            return;
        }

        if(player.getDuel().isToggled(DuelRule.NO_WEAPON_SWITCH) && equipSlot == Equipment.SLOT_WEAPON) {
            player.sendMessage("Weapon switching is disabled for this fight!");
            return;
        }

        if(!CombatRoom.allowEquip(player, selectedDef)) {
            return;
        }

        if (get(SLOT_WEAPON) != null
                && (get(SLOT_WEAPON).getId() == 22816 || get(SLOT_WEAPON).getId() == 22817)
                && (equipSlot == SLOT_HANDS || equipSlot == SLOT_WEAPON || equipSlot == SLOT_SHIELD)) {
            player.sendMessage("You must return the cormorant to Alry before equipping this.");
            return;
        }
        if (get(SLOT_WEAPON) != null
                && (get(SLOT_WEAPON).getId() == Items.FALCONERS_GLOVE || get(SLOT_WEAPON).getId() == Items.FALCONERS_GLOVE_2)
                && (equipSlot == SLOT_HANDS || equipSlot == SLOT_WEAPON || equipSlot == SLOT_SHIELD)) {
            player.sendMessage("You must return the falcon to Matthias before equipping this.");
            return;
        }

        if(selectedDef.name.toLowerCase().contains("goblin mail")) {
            player.sendMessage("You can't wear this item.");
            return;
        }

        Item addLast = null;
        Inventory inventory = player.getInventory();
        if(equipSlot == SLOT_SHIELD) {
            Item weapon = get(SLOT_WEAPON);
            if(weapon != null) {
                if(weapon.getDef().twoHanded) {
                    if(inventory.getFreeSlots() == 0 && get(SLOT_SHIELD) != null) {
                        player.sendMessage("You don't have enough free inventory space to do that.");
                        return;
                    }
                    addLast = weapon;
                    set(SLOT_WEAPON, null);
                }
            }
        } else if(equipSlot == SLOT_WEAPON) {
            Item shield = get(SLOT_SHIELD);
            if(shield != null) {
                if(selectedDef.twoHanded) {
                    if(inventory.getFreeSlots() == 0 && get(SLOT_WEAPON) != null) {
                        player.sendMessage("You don't have enough free inventory space to do that.");
                        return;
                    }
                    addLast = shield;
                    set(SLOT_SHIELD, null);
                }
            }
        }
        Item worn = get(equipSlot);
        if (worn == null) {
            selectedItem.remove();
            set(equipSlot, selectedItem);
        } else {
            ItemDefinition equippedDef = worn.getDef();
            int selectedId = selectedItem.getId();
            int selectedAmount = selectedItem.getAmount();
            Map<String, String> attributeCopy = selectedItem.copyOfAttributes();
            if(worn.getId() == selectedId && selectedDef.stackable) {
                selectedItem.remove();
                worn.incrementAmount(selectedAmount);
            } else {
                Item inventoryStack = null;
                Map<String, String> attributes = worn.copyOfAttributes();
                if (worn.getDef().stackable)
                    inventoryStack = inventory.findItem(worn.getId());
                if (inventoryStack != null) {
                    selectedItem.remove();
                    inventoryStack.incrementAmount(worn.getAmount());
                } else {
                    selectedItem.setId(worn.getId());
                    selectedItem.setAmount(worn.getAmount());
                    worn.clearAttributes();
                    worn.putAttributes(selectedItem.copyOfAttributes());
                }
                worn.setId(selectedId);
                worn.setAmount(selectedAmount);
                worn.putAttributes(attributeCopy);
                selectedItem.clearAttributes();
                selectedItem.putAttributes(attributes);
                if (equipSlot == SLOT_WEAPON || worn.getId() == 12853 || worn.getId() == 12851) {
                    TabCombat.resetAutocast(player);
                }
                if (equippedDef.unequipAction != null) {
                    equippedDef.unequipAction.handle(player);
                }
                playEquipSound(equippedDef);
            }
        }
        if(addLast != null) {
            inventory.add(addLast);
            if (addLast.getDef().unequipAction != null) {
                addLast.getDef().unequipAction.handle(player);
            }
            playEquipSound(addLast.getDef());
        }
        if (selectedDef.equipAction != null) {
            selectedDef.equipAction.handle(player);
        }
        if(!player.recentlyEquipped.isDelayed() && equipSlot == Equipment.SLOT_WEAPON) {
            player.recentlyEquipped.delay(1);
           // player.resetAnimation();
        }
        playEquipSound(selectedDef);
        player.closeDialogue();
    }

    private void playEquipSound(ItemDefinition itemDefinition) {
        if (itemDefinition.weaponType != null && itemDefinition.weaponType.equipSound > 0) {
            player.privateSound(itemDefinition.weaponType.equipSound);
        } else {
            String name = itemDefinition.name.toLowerCase();
            if (name.contains("platebody") || name.contains("chainbody")) {
                player.privateSound(2239);
            } else if (name.contains("platelegs") || name.contains("plateskirt")) {
                player.privateSound(2242);
            } else if (name.contains("chaps") || name.contains("vambraces") || name.contains("d'hide") || name.contains("leather")) {
                player.privateSound(2241);
            } else if (name.contains("arrow")) {
                player.privateSound(2244);
            } else if (name.contains("bolt")) {
                player.privateSound(2235);
            } else {
                player.privateSound(2238);
            }
        }
    }

    public boolean unequip(Item equipped) {
        Inventory inventory = player.getInventory();
        Item inventoryStack = null;
        if (equipped.getId() == 22816 || equipped.getId() == 22817) {
            player.sendMessage("You must return the cormorant to Alry.");
            return false;
        } else if (equipped.getId() == Items.FALCONERS_GLOVE || equipped.getId() == Items.FALCONERS_GLOVE_2) {
            player.sendMessage("You must return the falcon to Matthias.");
            return false;
        }
        if(equipped.getDef().stackable)
            inventoryStack = inventory.findItem(equipped.getId());
        if(inventoryStack != null) {
            equipped.remove();
            inventoryStack.incrementAmount(equipped.getAmount());
        } else {
            int freeSlot = inventory.freeSlot();
            if(freeSlot == -1) {
                player.sendMessage("You don't have enough free space to do that.");
                return false;
            }
            equipped.remove();
            inventory.set(freeSlot, equipped);
        }
        if (equipped.getId() == 12853 || equipped.getId() == 12851) {
            TabCombat.resetAutocast(player);
        }
        if (equipped.getDef().unequipAction != null) {
            equipped.getDef().unequipAction.handle(player);
        }
        playEquipSound(equipped.getDef());
        return true;
    }
    @Override
    public boolean sendUpdates() {
        int updatedAppearanceSlots = updatedCount;
        if(updatedSlots[SLOT_RING])
            updatedAppearanceSlots--;
        if(updatedSlots[SLOT_AMMO])
            updatedAppearanceSlots--;
        if(updatedSlots[SLOT_WEAPON])
            player.getCombat().updateWeapon(false);
        if(updatedAppearanceSlots > 0)
            player.getAppearance().update();
        if(!super.sendUpdates())
            return false;
        /**
         * Reset bonuses/weight
         */
        for(int i = 0; i < bonuses.length; i++)
            bonuses[i] = 0;
        weight = 0.0;
        /**
         * Calculate bonuses/weight
         */
        boolean ignoreRangedAmmoStr = false;
        Item wep = get(SLOT_WEAPON);
        if(wep != null) {
            if(wep.getId() == 12926) { //blowpipe
                Blowpipe.Dart dart = Blowpipe.getDart(wep);
                if(dart != Blowpipe.Dart.NONE) //should always be true
                    bonuses[EquipmentStats.RANGED_STRENGTH] += ItemDefinition.get(dart.id).equipBonuses[EquipmentStats.RANGED_STRENGTH];
                ignoreRangedAmmoStr = true;
            } else {
                RangedWeapon rangedWep = wep.getDef().rangedWeapon;
                ignoreRangedAmmoStr = rangedWep != null && rangedWep.allowedAmmo == null;
            }
        }
        for(Item item : getItems()) {
            if(item != null) {
                ItemDefinition def = item.getDef();
                if(def.equipBonuses != null) {
                    boolean wilderness = def.wilderness; //If its pvp armor
                    boolean inWilderness = player.wildernessLevel > 0 || player.tournament != null;
                    for(int i = 0; i < def.equipBonuses.length; i++) {
                        int bonus = def.equipBonuses[i];
                        if (bonus == 0)
                            continue;
                        if(ignoreRangedAmmoStr && def.equipSlot == SLOT_AMMO && i == EquipmentStats.RANGED_STRENGTH)
                            continue;
                        if (wilderness && !inWilderness)
                            bonus *= .75;
                        if (item.getId() == 11283 || item.getId() == 22002 || item.getId() == 21633) {
                            if (i == EquipmentStats.STAB_DEFENCE || i == EquipmentStats.SLASH_DEFENCE
                                    || i == EquipmentStats.CRUSH_DEFENCE || i == EquipmentStats.RANGE_DEFENCE) {
                                bonus -= (50 - item.getCharges());
                            }
                        }
                        bonuses[i] += bonus;
                    }
                }
                weight += def.weightEquipment;
            }
        }
        if (SetEffect.VERAC_DAMNED.hasPieces(player)) {
            bonuses[EquipmentStats.PRAYER] += 7;
        }
        int prayerBonus = bonuses[EquipmentStats.PRAYER];
        if (prayerBonus >= 15) {
            player.getTaskManager().doLookupByUUID(152);    // Reach a Prayer Bonus of 15
        }
        if (prayerBonus >= 30) {
            player.getTaskManager().doLookupByUUID(216);    // Reach a Prayer Bonus of 30
        }
        /**
         * Update equipment stats interface
         */
        if(player.isVisibleInterface(Interface.EQUIPMENT_STATS))
            EquipmentStats.update(player, Interface.EQUIPMENT_STATS, 24);
        return true;
    }

    @Override
    public boolean hasId(int id) {
        return getId(ItemDefinition.get(id).equipSlot) == id;
    }

}