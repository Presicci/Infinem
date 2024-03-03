package io.ruin.model.content.tasksystem.tasks.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.EquipAction;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 4/5/2022
 */
public enum EquipSetTask {
    VOID(468, // Equip a Full Void Knight Set
            SetPiece.VOID_TOP,
            SetPiece.VOID_BOTTOM,
            SetPiece.VOID_GLOVES,
            SetPiece.VOID_HELM
    ),
    MITHRIL(60, // Equip a Full Mithril Set
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
    );

    private final int uuid;
    private final SetPiece[] set;

    EquipSetTask(int uuid, SetPiece... set) {
        this.uuid = uuid;
        this.set = set;
    }

    private enum SetPiece {
        VOID_TOP(8839),
        VOID_BOTTOM(8840),
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
        WIZARD_HAT(Items.BLUE_WIZARD_HAT, Items.WIZARD_HAT, Items.BLUE_WIZARD_HAT_G, Items.BLUE_WIZARD_HAT_T, Items.BLACK_WIZARD_HAT_G, Items.BLACK_WIZARD_HAT_T);

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
                                            ItemDef def = ItemDef.get(id);
                                            index = idx;
                                            break;
                                        }
                                    }
                                    if (index == -1)
                                        return;
                                } else {
                                    ItemDef compareItemDef = ItemDef.get(itemIds[index]);
                                    int equipSlot = compareItemDef.equipSlot;
                                    if (s.compareNames) {
                                        String compareName = compareItemDef.name.toLowerCase().replaceAll("\\d", "").trim();
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
