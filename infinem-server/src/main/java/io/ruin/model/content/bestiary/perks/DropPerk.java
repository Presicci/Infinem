package io.ruin.model.content.bestiary.perks;

import io.ruin.model.content.bestiary.BestiaryPerk;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class DropPerk extends BestiaryPerk {
    @Getter protected double chance;

    public DropPerk(int requiredKillCount, double chance) {
        super(requiredKillCount);
        this.chance = chance;
    }
}
