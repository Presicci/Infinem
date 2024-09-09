package io.ruin.model.activities.combat.pestcontrol.rewards;

import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/5/2024
 */
@Getter
@AllArgsConstructor
public enum PCExperienceReward {
    ATTACK(2, 2800, StatType.Attack),
    STRENGTH(7, 2800, StatType.Strength),
    DEFENCE(12, 2800, StatType.Defence),
    RANGED(17, 2520, StatType.Ranged),
    MAGIC(22, 2520, StatType.Magic),
    HITPOINTS(27, 2600, StatType.Hitpoints),
    PRAYER(32, 1480, StatType.Prayer);

    private final int baseChild, experience;
    private final StatType statType;

    private double getExperience(int level) {
        int experienceStep = this == PRAYER ? 18 : (this == RANGED || this == MAGIC) ? 32 : 35;
        return (int) (Math.pow(level, 2) / 600D) * experienceStep;
    }

    public void purchase(Player player, int slot) {
        int rewardIndex = slot - baseChild;
        int cost = rewardIndex == 0 ? 1 : rewardIndex == 1 ? 10 : 100;
        if (player.getAttributeIntOrZero("PEST_POINTS") < cost) {
            player.sendMessage("You do not have enough Pest Points to purchase this reward.");
            return;
        }
        int level = player.getStats().get(statType).fixedLevel;
        if (level < 25) {
            return;
        }
        double multiplier = rewardIndex == 0 ? 1 : rewardIndex == 1 ? 1.01 : 1.1;
        double experience = getExperience(level) * cost * multiplier;
        player.getStats().addXp(statType, experience, false);
        player.incrementNumericAttribute("PEST_POINTS", -cost);
        player.removeTemporaryAttribute("PC_SHOP_SLOT");
    }

    public static final Map<Integer, PCExperienceReward> REWARDS = new HashMap<>();

    static {
        for (PCExperienceReward reward : values()) {
            for (int index = 0; index < 3; index++) {
                REWARDS.put(reward.baseChild + index, reward);
            }
        }
    }
}
