package io.ruin.model.content.bestiary;

import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryPerk implements Comparable<BestiaryPerk> {

    @Getter private final int requiredKillCount;

    protected BestiaryPerk(int requiredKillCount) {
        this.requiredKillCount = requiredKillCount;
    }

    @Override
    public int compareTo(BestiaryPerk other) {
        return requiredKillCount - other.getRequiredKillCount();
    }
}
