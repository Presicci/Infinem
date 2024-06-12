package io.ruin.model.content.bestiary;

import io.ruin.model.content.bestiary.perks.BestiaryPerk;
import io.ruin.model.content.bestiary.perks.impl.*;
import io.ruin.model.entity.player.Player;
import lombok.Getter;

import java.util.*;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryEntry {

    @Getter private final String name;
    private final int killCount;
    private final Player player;

    private static List<BestiaryPerk> perks;

    public BestiaryEntry(Player player, String name, int killCount) {
        this.player = player;
        this.name = name;
        this.killCount = killCount;
        boolean isBoss = BestiaryDef.isBoss(name);
        perks = Arrays.asList(
                new DamagePerk(isBoss),
                new AccuracyPerk(isBoss),
                new ExtraDropPerk(isBoss),
                new NotedDropPerk(isBoss),
                new ReducedEnemyAccuracyPerk(isBoss),
                new RespawnPerk(isBoss),
                new LuckPerk(isBoss),
                new GoldPickupPerk()
        );
    }

    public double getPerkMultiplier(Class<?> perkType) {
        for (BestiaryPerk perk : perks) {
            if (player.hasAttribute(getPerkKey(perkType))) {
                player.sendMessage("Perk disabled: " + perkType.getSimpleName() + ", returning value: " + perk.getMultiplier(0));
                return perk.getMultiplier(0);
            }
            if (perkType.isInstance(perk))
                return perk.getMultiplier(killCount);
        }
        return 0;
    }

    public void togglePerk(Class<?> perkType) {
        for (BestiaryPerk perk : perks) {
            if (perkType.isInstance(perk)) {
                String key = getPerkKey(perkType);
                if (player.hasAttribute(key)) {
                    player.removeAttribute(key);
                } else {
                    player.putAttribute(key, 1);
                }
            }
        }
    }

    private String abbreviatePerkName(Class<?> perkType) {
        String name = perkType.getSimpleName().replace("Perk", "");
        name = name.replaceAll("[a-z]", "");
        return name.toUpperCase();
    }

    public String getPerkKey(Class<?> perkType) {
        return name.replace(" ", "_").toUpperCase() + "_" + abbreviatePerkName(perkType);
    }

    public List<BestiaryPerk> getSortedPerks() {
        perks.sort((o1, o2) -> o2.getFill(killCount) - o1.getFill(killCount));
        return perks;
    }

    public String generateRewardString() {
        perks.sort((o1, o2) -> o2.getFill(killCount) - o1.getFill(killCount));
        StringBuilder sb = new StringBuilder();
        for (BestiaryPerk perk : perks) {
            sb.append(perk.getString(killCount));
            sb.append(!perk.hasUnlocked(killCount) ? "2" : player.hasAttribute(getPerkKey(perk.getClass())) ? "0" : "1");
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);   // Trim trailing |
        return sb.toString();
    }
}
