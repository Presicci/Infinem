package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/5/2022
 */
public enum EquipSetTask {
    PYROMANCER(983,
            SetPiece.PYROMANCER_GARB,
            SetPiece.PYROMANCER_ROBE,
            SetPiece.PYROMANCER_HOOD,
            SetPiece.PYROMANCER_BOOTS
    ),
    PROSPECTOR(457,
            SetPiece.PROSPECTOR_HAT,
            SetPiece.PROSPECTOR_JACKET,
            SetPiece.PROSPECTOR_LEGS,
            SetPiece.PROSPECTOR_BOOTS
    ),
    GRACEFUL(455,
            SetPiece.GRACEFUL_HOOD,
            SetPiece.GRACEFUL_CAPE,
            SetPiece.GRACEFUL_TOP,
            SetPiece.GRACEFUL_LEGS,
            SetPiece.GRACEFUL_GLOVES,
            SetPiece.GRACEFUL_BOOTS
    ),
    YAKHIDE(515,    // Equip a full set of Yakhide Armour
            SetPiece.YAKHIDE_TOP,
            SetPiece.YAKHIDE_LEGS
    ),
    VOID(468,       // Equip a Full Void Knight Set
            SetPiece.VOID_TOP,
            SetPiece.VOID_BOTTOM,
            SetPiece.VOID_GLOVES,
            SetPiece.VOID_HELM
    ),
    MITHRIL(60,     // Equip a Full Mithril Set
            SetPiece.MITHRIL_CHEST,
            SetPiece.MITHRIL_LEGS,
            SetPiece.MITHRIL_HELM
    ),
    ADAMANT(139,
            SetPiece.ADAMANT_CHEST,
            SetPiece.ADAMANT_LEGS,
            SetPiece.ADAMANT_HELM
    ),
    RUNE(140,
            SetPiece.RUNE_CHEST,
            SetPiece.RUNE_LEGS,
            SetPiece.RUNE_HELM
    ),
    /*BARROWS(739,    // Equip any Full Barrows Armour Set
            SetPiece.BARROWS_HELM,
            SetPiece.BARROWS_CHEST,
            SetPiece.BARROWS_LEGS,
            SetPiece.BARROWS_WEAPON
    ),*/
    STUDDED(61, // Equip a Studded Body and Chaps
            SetPiece.STUDDED_BODY,
            SetPiece.STUDDED_CHAPS
    ),
    GREEN_DHIDE(141,    // Equip a Full Green Dragonhide Set
            SetPiece.GREEN_DHIDE_BODY,
            SetPiece.GREEN_DHIDE_CHAPS,
            SetPiece.GREEN_DHIDE_VAMBS
    ),
    BLUE_DHIDE(142,    // Equip a Full Blue Dragonhide Set
            SetPiece.BLUE_DHIDE_BODY,
            SetPiece.BLUE_DHIDE_CHAPS,
            SetPiece.BLUE_DHIDE_VAMBS
    ),
    RED_DHIDE(143,    // Equip a Full Red Dragonhide Set
            SetPiece.RED_DHIDE_BODY,
            SetPiece.RED_DHIDE_CHAPS,
            SetPiece.RED_DHIDE_VAMBS
    ),
    WIZARD(65, // Equip a Wizard Robe and Hat
            SetPiece.WIZARD_HAT,
            SetPiece.WIZARD_ROBE
    ),
    ANGLER(595,
            SetPiece.ANGLER_BOOTS,
            SetPiece.ANGLER_HAT,
            SetPiece.ANGLER_WADERS,
            SetPiece.ANGLER_TOP
    ),
    ZEALOT(1111, // Equip a Full Set of Zealot's Robes
            SetPiece.ZEALOT_HELM,
            SetPiece.ZEALOT_ROBE_TOP,
            SetPiece.ZEALOT_ROBE_BOTTOM,
            SetPiece.ZEALOT_BOOTS
    );

    private final int uuid;
    private final SetPiece[] set;

    EquipSetTask(int uuid, SetPiece... set) {
        this.uuid = uuid;
        this.set = set;
    }

    private enum SetPiece {
        BROODOO_SHIELD_COMBAT(Items.BROODOO_SHIELD_10_3, Items.BROODOO_SHIELD_9_3, Items.BROODOO_SHIELD_8_3, Items.BROODOO_SHIELD_7_3, Items.BROODOO_SHIELD_6_3,
                Items.BROODOO_SHIELD_5_3, Items.BROODOO_SHIELD_4_3, Items.BROODOO_SHIELD_3_3, Items.BROODOO_SHIELD_2_3, Items.BROODOO_SHIELD_1_3, Items.BROODOO_SHIELD_3),
        BROODOO_SHIELD_DISEASE(Items.BROODOO_SHIELD_10_2, Items.BROODOO_SHIELD_9_2, Items.BROODOO_SHIELD_8_2, Items.BROODOO_SHIELD_7_2, Items.BROODOO_SHIELD_6_2,
                Items.BROODOO_SHIELD_5_2, Items.BROODOO_SHIELD_4_2, Items.BROODOO_SHIELD_3_2, Items.BROODOO_SHIELD_2_2, Items.BROODOO_SHIELD_1_2, Items.BROODOO_SHIELD_2),
        BROODOO_SHIELD_POISON(Items.BROODOO_SHIELD_10, Items.BROODOO_SHIELD_9, Items.BROODOO_SHIELD_8, Items.BROODOO_SHIELD_7, Items.BROODOO_SHIELD_6,
                Items.BROODOO_SHIELD_5, Items.BROODOO_SHIELD_4, Items.BROODOO_SHIELD_3, Items.BROODOO_SHIELD_2, Items.BROODOO_SHIELD_1, Items.BROODOO_SHIELD),
        TRIBAL_MASK_COMBAT(Items.TRIBAL_MASK_3),
        TRIBAL_MASK_DISEASE(Items.TRIBAL_MASK_2),
        TRIBAL_MASK_POISON(Items.TRIBAL_MASK),
        PYROMANCER_GARB(Items.PYROMANCER_GARB),
        PYROMANCER_ROBE(Items.PYROMANCER_ROBE),
        PYROMANCER_HOOD(Items.PYROMANCER_HOOD),
        PYROMANCER_BOOTS(Items.PYROMANCER_BOOTS),
        PROSPECTOR_HAT(Items.PROSPECTOR_HELMET, 25549),
        PROSPECTOR_JACKET(Items.PROSPECTOR_JACKET, 25551),
        PROSPECTOR_LEGS(Items.PROSPECTOR_LEGS, 25553),
        PROSPECTOR_BOOTS(Items.PROSPECTOR_BOOTS, 25555),
        GRACEFUL_HOOD(11850, 13580, 13592, 13604, 13616, 13628, 13668, 21063, 24745, 25071),
        GRACEFUL_CAPE(11852, 13582, 13594, 13606, 13618, 13630, 13670, 21066, 24748, 25074),
        GRACEFUL_TOP(11854, 13584, 13596, 13608, 13620, 13632, 13672, 21069, 24751, 25077),
        GRACEFUL_LEGS(11856, 13586, 13598, 13610, 13622, 13634, 13674, 21072, 24754, 25080),
        GRACEFUL_GLOVES(11858, 13588, 13600, 13612, 13624, 13636, 13676, 21075, 24757, 25083),
        GRACEFUL_BOOTS(11860, 13590, 13602, 13614, 13626, 13638, 13678, 21078, 24760, 25086),
        YAKHIDE_TOP(Items.YAKHIDE_BODY),
        YAKHIDE_LEGS(Items.YAKHIDE_LEGS),
        VOID_TOP(8839, 13072),
        VOID_BOTTOM(8840, 13073),
        VOID_GLOVES(8842),
        VOID_HELM(11663, 11664, 11665),
        MITHRIL_HELM(Items.MITHRIL_FULL_HELM, Items.MITHRIL_FULL_HELM_G, Items.MITHRIL_FULL_HELM_T),
        MITHRIL_CHEST(Items.MITHRIL_PLATEBODY, Items.MITHRIL_PLATEBODY_G, Items.MITHRIL_PLATEBODY_T),
        MITHRIL_LEGS(Items.MITHRIL_PLATELEGS, Items.MITHRIL_PLATELEGS_G, Items.MITHRIL_PLATELEGS_T,
                Items.MITHRIL_PLATESKIRT, Items.MITHRIL_PLATESKIRT_G, Items.MITHRIL_PLATESKIRT_T),
        ADAMANT_HELM(Items.ADAMANT_FULL_HELM, Items.ADAMANT_FULL_HELM_G, Items.ADAMANT_FULL_HELM_T),
        ADAMANT_CHEST(Items.ADAMANT_PLATEBODY, Items.ADAMANT_PLATEBODY_G, Items.ADAMANT_PLATEBODY_T),
        ADAMANT_LEGS(Items.ADAMANT_PLATELEGS, Items.ADAMANT_PLATELEGS_G, Items.ADAMANT_PLATELEGS_T,
                Items.ADAMANT_PLATESKIRT, Items.ADAMANT_PLATESKIRT_G, Items.ADAMANT_PLATESKIRT_T),
        RUNE_HELM(Items.RUNE_FULL_HELM, Items.RUNE_FULL_HELM_G, Items.RUNE_FULL_HELM_T),
        RUNE_CHEST(Items.RUNE_PLATEBODY, Items.RUNE_PLATEBODY_G, Items.RUNE_PLATEBODY_T),
        RUNE_LEGS(Items.RUNE_PLATELEGS, Items.RUNE_PLATELEGS_G, Items.RUNE_PLATELEGS_T,
                Items.RUNE_PLATESKIRT, Items.RUNE_PLATESKIRT_G, Items.RUNE_PLATESKIRT_T),
        /*BARROWS_HELM(true, true,
                Items.GUTHANS_HELM, Items.GUTHANS_HELM_25, Items.GUTHANS_HELM_50, Items.GUTHANS_HELM_75, Items.GUTHANS_HELM_100,
                Items.DHAROKS_HELM, Items.DHAROKS_HELM_25, Items.DHAROKS_HELM_50, Items.DHAROKS_HELM_75, Items.DHAROKS_HELM_100,
                Items.KARILS_COIF, Items.KARILS_COIF_25, Items.KARILS_COIF_50, Items.KARILS_COIF_75, Items.KARILS_COIF_100,
                Items.TORAGS_HELM, Items.TORAGS_HELM_25, Items.TORAGS_HELM_50, Items.TORAGS_HELM_75, Items.TORAGS_HELM_100,
                Items.VERACS_HELM, Items.VERACS_HELM_25, Items.VERACS_HELM_50, Items.VERACS_HELM_75, Items.VERACS_HELM_100,
                Items.AHRIMS_HOOD, Items.AHRIMS_HOOD_25, Items.AHRIMS_HOOD_50, Items.AHRIMS_HOOD_75, Items.AHRIMS_HOOD_100
        ),
        BARROWS_CHEST(true, true,
                Items.GUTHANS_PLATEBODY, Items.GUTHANS_PLATEBODY_25, Items.GUTHANS_PLATEBODY_50, Items.GUTHANS_PLATEBODY_75, Items.GUTHANS_PLATEBODY_100,
                Items.DHAROKS_PLATEBODY, Items.DHAROKS_PLATEBODY_25, Items.DHAROKS_PLATEBODY_50, Items.DHAROKS_PLATEBODY_75, Items.DHAROKS_PLATEBODY_100,
                Items.KARILS_LEATHERTOP, Items.KARILS_LEATHERTOP_25, Items.KARILS_LEATHERTOP_50, Items.KARILS_LEATHERTOP_75, Items.KARILS_LEATHERTOP_100,
                Items.TORAGS_PLATEBODY, Items.TORAGS_PLATEBODY_25, Items.TORAGS_PLATEBODY_50, Items.TORAGS_PLATEBODY_75, Items.TORAGS_PLATEBODY_100,
                Items.VERACS_BRASSARD, Items.VERACS_BRASSARD_25, Items.VERACS_BRASSARD_50, Items.VERACS_BRASSARD_75, Items.VERACS_BRASSARD_100,
                Items.AHRIMS_ROBETOP, Items.AHRIMS_ROBETOP_25, Items.AHRIMS_ROBETOP_50, Items.AHRIMS_ROBETOP_75, Items.AHRIMS_ROBETOP_100
        ),
        BARROWS_LEGS(true, true,
                Items.GUTHANS_CHAINSKIRT, Items.GUTHANS_CHAINSKIRT_25, Items.GUTHANS_CHAINSKIRT_50, Items.GUTHANS_CHAINSKIRT_75, Items.GUTHANS_CHAINSKIRT_100,
                Items.DHAROKS_PLATELEGS, Items.DHAROKS_PLATELEGS_25, Items.DHAROKS_PLATELEGS_50, Items.DHAROKS_PLATELEGS_75, Items.DHAROKS_PLATELEGS_100,
                Items.KARILS_LEATHERSKIRT, Items.KARILS_LEATHERSKIRT_25, Items.KARILS_LEATHERSKIRT_50, Items.KARILS_LEATHERSKIRT_75, Items.KARILS_LEATHERSKIRT_100,
                Items.TORAGS_PLATELEGS, Items.TORAGS_PLATELEGS_25, Items.TORAGS_PLATELEGS_50, Items.TORAGS_PLATELEGS_75, Items.TORAGS_PLATELEGS_100,
                Items.VERACS_PLATESKIRT, Items.VERACS_PLATESKIRT_25, Items.VERACS_PLATESKIRT_50, Items.VERACS_PLATESKIRT_75, Items.VERACS_PLATESKIRT_100,
                Items.AHRIMS_ROBESKIRT, Items.AHRIMS_ROBESKIRT_25, Items.AHRIMS_ROBESKIRT_50, Items.AHRIMS_ROBESKIRT_75, Items.AHRIMS_ROBESKIRT_100
        ),
        BARROWS_WEAPON(true, true,
                Items.GUTHANS_WARSPEAR, Items.GUTHANS_WARSPEAR_25, Items.GUTHANS_WARSPEAR_50, Items.GUTHANS_WARSPEAR_75, Items.GUTHANS_WARSPEAR_100,
                Items.DHAROKS_GREATAXE, Items.DHAROKS_GREATAXE_25, Items.DHAROKS_GREATAXE_50, Items.DHAROKS_GREATAXE_75, Items.DHAROKS_GREATAXE_100,
                Items.KARILS_CROSSBOW, Items.KARILS_CROSSBOW_25, Items.KARILS_CROSSBOW_50, Items.KARILS_CROSSBOW_75, Items.KARILS_CROSSBOW_100,
                Items.TORAGS_HAMMERS, Items.TORAGS_HAMMERS_25, Items.TORAGS_HAMMERS_50, Items.TORAGS_HAMMERS_75, Items.TORAGS_HAMMERS_100,
                Items.VERACS_FLAIL, Items.VERACS_FLAIL_25, Items.VERACS_FLAIL_50, Items.VERACS_FLAIL_75, Items.VERACS_FLAIL_100,
                Items.AHRIMS_STAFF, Items.AHRIMS_STAFF_25, Items.AHRIMS_STAFF_50, Items.AHRIMS_STAFF_75, Items.AHRIMS_STAFF_100
        ),*/
        STUDDED_BODY(Items.STUDDED_BODY, Items.STUDDED_BODY_G, Items.STUDDED_BODY_T),
        STUDDED_CHAPS(Items.STUDDED_CHAPS, Items.STUDDED_CHAPS_G, Items.STUDDED_CHAPS_T),
        GREEN_DHIDE_BODY(Items.GREEN_DHIDE_BODY, Items.GREEN_DHIDE_BODY_T, Items.GREEN_DHIDE_BODY_G),
        GREEN_DHIDE_CHAPS(Items.GREEN_DHIDE_CHAPS, Items.GREEN_DHIDE_CHAPS_T, Items.GREEN_DHIDE_CHAPS_G),
        GREEN_DHIDE_VAMBS(Items.GREEN_DHIDE_VAMB),
        BLUE_DHIDE_BODY(Items.BLUE_DHIDE_BODY, Items.BLUE_DHIDE_BODY_T, Items.BLUE_DHIDE_BODY_G),
        BLUE_DHIDE_CHAPS(Items.BLUE_DHIDE_CHAPS, Items.BLUE_DHIDE_CHAPS_T, Items.BLUE_DHIDE_CHAPS_G),
        BLUE_DHIDE_VAMBS(Items.BLUE_DHIDE_VAMB),
        RED_DHIDE_BODY(Items.RED_DHIDE_BODY, Items.RED_DHIDE_BODY_T, Items.RED_DHIDE_BODY_G),
        RED_DHIDE_CHAPS(Items.RED_DHIDE_CHAPS, Items.RED_DHIDE_CHAPS_T, Items.RED_DHIDE_CHAPS_G),
        RED_DHIDE_VAMBS(Items.RED_DHIDE_VAMB),
        WIZARD_ROBE(Items.BLUE_WIZARD_ROBE, Items.BLACK_ROBE, Items.BLUE_WIZARD_ROBE_G, Items.BLUE_WIZARD_ROBE_T, Items.BLACK_WIZARD_ROBE_G, Items.BLACK_WIZARD_ROBE_T),
        WIZARD_HAT(Items.BLUE_WIZARD_HAT, Items.WIZARD_HAT, Items.BLUE_WIZARD_HAT_G, Items.BLUE_WIZARD_HAT_T, Items.BLACK_WIZARD_HAT_G, Items.BLACK_WIZARD_HAT_T),
        ANGLER_BOOTS(Items.ANGLER_BOOTS),
        ANGLER_WADERS(Items.ANGLER_BOOTS),
        ANGLER_HAT(Items.ANGLER_HAT),
        ANGLER_TOP(Items.ANGLER_TOP),
        ZEALOT_HELM(25438),
        ZEALOT_ROBE_TOP(25434),
        ZEALOT_ROBE_BOTTOM(25436),
        ZEALOT_BOOTS(25440);

        public final int[] itemIds;
        public final boolean respectIndex, compareNames;

        SetPiece(int... itemIds) {
            this(false, itemIds);
        }

        SetPiece(boolean respectIndex, int... itemIds) {
            this(respectIndex, false, itemIds);
        }

        SetPiece(boolean respectIndex, boolean compareNames, int... itemIds) {
            this.respectIndex = respectIndex;
            this.compareNames = compareNames;
            this.itemIds = itemIds;
        }
    }

    static {
        for (EquipSetTask set : values()) {
            for (SetPiece s : set.set) {
                for (int i : s.itemIds) {
                    EquipAction.register(i, (player -> {
                        if (s.respectIndex) {
                            int index = -1;
                            for (SetPiece slot : set.set) {
                                int[] itemIds = slot.itemIds;
                                if (index == -1) {
                                    for (int idx = 0; idx < itemIds.length; idx++) {
                                        int id = itemIds[idx];
                                        if (player.getEquipment().hasId(id)) {
                                            ItemDefinition def = ItemDefinition.get(id);
                                            index = idx;
                                            break;
                                        }
                                    }
                                    if (index == -1)
                                        return;
                                } else {
                                    ItemDefinition compareItemDefinition = ItemDefinition.get(itemIds[index]);
                                    int equipSlot = compareItemDefinition.equipSlot;
                                    if (s.compareNames) {
                                        String compareName = compareItemDefinition.name.toLowerCase().replaceAll("\\d", "").trim();
                                        Item equippedItem = player.getEquipment().get(equipSlot);
                                        if (equippedItem == null || !equippedItem.getDef().name.toLowerCase().contains(compareName))
                                            return;
                                    } else {
                                        if (!player.getEquipment().hasId(itemIds[index]))
                                            return;
                                    }
                                }
                            }
                            player.getTaskManager().doLookupByUUID(set.uuid, 1);
                        } else {
                            for (SetPiece slot : set.set) {
                                if (!player.getEquipment().hasAtLeastOneOf(slot.itemIds)) {
                                    return;
                                }
                            }
                            player.getTaskManager().doLookupByUUID(set.uuid, 1);
                        }
                    }));
                }
            }
        }
    }
}
