package io.ruin.model.activities.combat.nightmarezone;

import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum NMZPotion {
    SUPER_RANGING(11725, 250, Config.NMZ_SUPER_RANGE_DOSES),
    SUPER_MAGIC(11729, 250, Config.NMZ_SUPER_MAGIC_DOSES),
    OVERLOAD(11733, 1500, Config.NMZ_OVERLOAD_DOSES),
    ABSORPTION(11737,1000, Config.NMZ_ABSORPTION_DOSES);

    private final int id, price;
    private final Config config;

    public static NMZPotion getBenefits(int id) {
        for (NMZPotion benefits : values())
            if (benefits.id == id)
                return benefits;
        return null;
    }
}
