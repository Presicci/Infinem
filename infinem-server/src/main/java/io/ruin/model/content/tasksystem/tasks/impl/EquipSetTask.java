package io.ruin.model.content.tasksystem.tasks.impl;

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
    STUDDED(61, // Equip a Studded Body and Chaps
            SetPiece.STUDDED_BODY,
            SetPiece.STUDDED_CHAPS
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
        MITHRIL_HELM(Items.MITHRIL_FULL_HELM),
        MITHRIL_CHEST(Items.MITHRIL_PLATEBODY),
        MITHRIL_LEGS(Items.MITHRIL_PLATELEGS, Items.MITHRIL_PLATESKIRT),
        STUDDED_BODY(Items.STUDDED_BODY),
        STUDDED_CHAPS(Items.STUDDED_CHAPS);

        public final int[] itemIds;

        SetPiece(int... itemIds) {
            this.itemIds = itemIds;
        }
    }

    static {
        for (EquipSetTask set : values()) {
            for (SetPiece s : set.set) {
                for (int i : s.itemIds) {
                    EquipAction.register(i, (player -> {
                        for (SetPiece slot : set.set) {
                            if (!player.getEquipment().hasAtLeastOneOf(slot.itemIds)) {
                                return;
                            }
                        }
                        player.getTaskManager().doLookupByUUID(set.uuid, 1);
                    }));
                }
            }
        }
    }
}
