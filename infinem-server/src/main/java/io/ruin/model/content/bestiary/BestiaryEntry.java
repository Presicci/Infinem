package io.ruin.model.content.bestiary;

import io.ruin.model.content.bestiary.perks.BestiaryPerk;
import io.ruin.model.content.bestiary.perks.impl.*;
import io.ruin.model.entity.player.Player;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public class BestiaryEntry {

    @Getter private final String name;
    private final int killCount;
    private final Player player;

    @Getter private List<BestiaryPerk> perks = new ArrayList<>();

    public BestiaryEntry(Player player, String name, int killCount) {
        this.player = player;
        this.name = name;
        this.killCount = killCount;
        boolean isBoss = BestiaryDef.isBoss(name);
        perks.add(new DamagePerk(isBoss));
        perks.add(new AccuracyPerk(isBoss));
        perks.add(new ExtraDropPerk(isBoss));
        perks.add(new NotedDropPerk(isBoss));
        perks.add(new ReducedEnemyAccuracyPerk(isBoss));
        perks.add(new RespawnPerk(isBoss));
        perks.add(new LuckPerk(isBoss));
        perks.add(new GoldPickupPerk());
        List<Consumer<BestiaryEntry>> modifiers = BestiaryEntryModifiers.MODIFIERS.getOrDefault(name, Collections.emptyList());
        modifiers.forEach(mod -> mod.accept(this));
    }

    public void clearPerks() {
        perks = new ArrayList<>();
    }

    public void addPerk(BestiaryPerk perk) {
        perks.add(perk);
    }

    public void removePerk(Class<? extends BestiaryPerk> clazz) {
        BestiaryPerk perkToRemove = null;
        for (BestiaryPerk perk : perks) {
            if (clazz.isInstance(perk)) {
                perkToRemove = perk;
                break;
            }
        }
        if (perkToRemove != null) perks.remove(perkToRemove);
    }

    public double getPerkMultiplier(Class<?> perkType, double defaultMulti) {
        if (name == null || name.isEmpty()) return defaultMulti;
        for (BestiaryPerk perk : perks) {
            if (player.hasAttribute(getPerkKey(perkType))) {
                return defaultMulti;
            }
            if (perkType.isInstance(perk)) {
                return perk.getMultiplier(uniqueEntryKillcountModifier(killCount));
            }
        }
        return defaultMulti;
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
        int count = uniqueEntryKillcountModifier(killCount);
        perks.sort((o1, o2) -> o2.getFill(count) - o1.getFill(count));
        return perks;
    }

    public String generateRewardString() {
        int count = uniqueEntryKillcountModifier(killCount);
        perks.sort((o1, o2) -> o2.getFill(count) - o1.getFill(count));
        StringBuilder sb = new StringBuilder();
        for (BestiaryPerk perk : perks) {
            sb.append(perk.getString(count));
            sb.append(!perk.hasUnlocked(count) ? "2" : player.hasAttribute(getPerkKey(perk.getClass())) ? "0" : "1");
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);   // Trim trailing |
        return sb.toString();
    }

    public int uniqueEntryKillcountModifier(int killCount) {
        if (!BestiaryDef.isBoss(name)) {
            int uniqueEntries = player.getBestiary().getKillCounts().size();
            killCount += uniqueEntries >= 400 ? 1000 : uniqueEntries >= 200 ? 500 : uniqueEntries >= 100 ? 250 : uniqueEntries >= 50 ? 100 : uniqueEntries >= 25 ? 50 : 0;
        }
        return killCount;
    }
}
