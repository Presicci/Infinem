package io.ruin.model.skills.runecrafting;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Items;
import io.ruin.model.item.containers.equipment.EquipAction;
import io.ruin.model.item.containers.equipment.UnequipAction;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/19/2022
 */
@AllArgsConstructor
public enum Tiara {
    AIR(Items.AIR_TIARA, Config.AIR_TIARA),
    MIND(Items.MIND_TIARA, Config.MIND_TIARA),
    WATER(Items.WATER_TIARA, Config.WATER_TIARA),
    EARTH(Items.EARTH_TIARA, Config.EARTH_TIARA),
    FIRE(Items.FIRE_TIARA, Config.FIRE_TIARA),
    BODY(Items.BODY_TIARA, Config.BODY_TIARA),
    COSMIC(Items.COSMIC_TIARA, Config.COSMIC_TIARA),
    LAW(Items.LAW_TIARA, Config.LAW_TIARA),
    NATURE(Items.NATURE_TIARA, Config.NATURE_TIARA),
    CHAOS(Items.CHAOS_TIARA, Config.CHAOS_TIARA),
    DEATH(Items.DEATH_RUNE, Config.DEATH_TIARA),
    WRATH(22121, Config.WRATH_TIARA);

    private final int itemId;
    private final Config config;

    static {
        for (Tiara tiara : values()) {
            EquipAction.register(tiara.itemId, (player -> {
                tiara.config.set(player, 1);
            }));
            UnequipAction.register(tiara.itemId, (player) -> {
                tiara.config.set(player, 0);
            });
        }
    }
}
