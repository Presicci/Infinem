package io.ruin.model.content.bestiary.perks;

import io.ruin.model.content.bestiary.BestiaryPerk;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class DamagePerk extends BestiaryPerk {
    @Getter
    private double boost;

    public DamagePerk(int requiredKillCount, double boost) {
        super(requiredKillCount);
        this.boost = boost;
    }
}
