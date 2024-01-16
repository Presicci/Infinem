package io.ruin.model.item.actions.impl.equipaction;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.equipment.EquipAction;
import io.ruin.model.item.containers.equipment.UnequipAction;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/16/2024
 */
@AllArgsConstructor
public enum KaramjaGloves {
    //KARAMJA_GLOVES_1(11136),
    //KARAMJA_GLOVES_2(11138),
    KARAMJA_GLOVES_3(11140),
    KARAMJA_GLOVES_4(13103);

    private final int itemId;

    static {
        for (KaramjaGloves gloves : values()) {
            EquipAction.register(gloves.itemId, (player -> {
                Config.KARAMJA_GLOVES_GEM_MINE.set(player, 1);
            }));
            UnequipAction.register(gloves.itemId, (player) -> {
                Config.KARAMJA_GLOVES_GEM_MINE.set(player, 0);
            });
        }
    }
}
