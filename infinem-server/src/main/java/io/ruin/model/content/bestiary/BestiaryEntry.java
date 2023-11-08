package io.ruin.model.content.bestiary;

import io.ruin.model.content.bestiary.perks.BestiaryPerk;
import io.ruin.model.content.bestiary.perks.impl.*;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryEntry {

    private final int killCount;

    private static final List<BestiaryPerk> perks = Arrays.asList(
            new DamagePerk(),
            new AccuracyPerk(),
            new ExtraDropPerk(),
            new NotedDropPerk(),
            new ReducedEnemyAccuracyPerk(),
            new RespawnPerk()
    );

    public BestiaryEntry(int killCount) {
        this.killCount = killCount;
    }

    public double getPerkMultiplier(Class<?> perkType) {
        for (BestiaryPerk perk : perks) {
            if (perkType.isInstance(perk))
                return perk.getMultiplier(killCount);
        }
        return 0;
    }

    public String generateRewardString() {
        perks.sort((o1, o2) -> o2.getFill(killCount) - o1.getFill(killCount));
        StringBuilder sb = new StringBuilder();
        for (BestiaryPerk perk : perks) {
            sb.append(perk.getString(killCount));
        }
        sb.deleteCharAt(sb.length() - 1);   // Trim trailing |
        return sb.toString();
    }
}
