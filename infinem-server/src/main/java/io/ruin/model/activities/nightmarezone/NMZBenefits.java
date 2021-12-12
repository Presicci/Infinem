package io.ruin.model.activities.nightmarezone;

import io.ruin.model.inter.utils.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum NMZBenefits {
    // TODO reduce prices by 1/4 when adding dose storing
    SUPER_RANGING(11725, 1000, Config.NMZ_SUPER_RANGE_DOSES),
    SUPER_MAGIC(11729, 1000, Config.NMZ_SUPER_MAGIC_DOSES),
    OVERLOAD(11733, 6000, Config.NMZ_OVERLOAD_DOSES),
    ABSORPTION(11737,4000, Config.NMZ_ABSORPTION_DOSES);

    private final int id, price;
    private final Config config;

    public static NMZBenefits getBenefits(int id) {
        for (NMZBenefits benefits : values())
            if (benefits.id == id)
                return benefits;
        return null;
    }
}
