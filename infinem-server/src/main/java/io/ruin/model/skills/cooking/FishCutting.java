package io.ruin.model.skills.cooking;

import io.ruin.api.utils.Random;
import io.ruin.cache.def.ItemDefinition;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.Tool;
import io.ruin.model.stat.StatType;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/2/2024
 */
public enum FishCutting {
    LEAPING_TROUT(Items.LEAPING_TROUT, Items.ROE, (double) 1 /150, (double) 99 /150, (double) 1 /2),
    LEAPING_SALMON(Items.LEAPING_SALMON, Items.ROE, (double) 1 /70, 1, (double) 3 /4, 80),
    LEAPING_STURGEON(Items.LEAPING_STURGEON, Items.CAVIAR, (double) 1 /80, 1, (double) 5 /6, 80);

    private final int fishId, cutId, levelForMaxChance;
    private final double baseChance, maxChance, offcutsChance;

    FishCutting(int fishId, int cutId, double baseChance, double maxChance, double offcutsChance) {
        this(fishId, cutId, baseChance, maxChance, offcutsChance, 99);
    }

    FishCutting(int fishId, int cutId, double baseChance, double maxChance, double offcutsChance, int levelForMaxChance) {
        this.fishId = fishId;
        this.cutId = cutId;
        this.baseChance = baseChance;
        this.maxChance = maxChance;
        this.offcutsChance = offcutsChance;
        this.levelForMaxChance = levelForMaxChance;
    }

    private void cut(Player player, Item fish) {
        double chance = getChance(player.getStats().get(StatType.Cooking).currentLevel);
        player.getInventory().remove(fish.getId(), 1);
        if (Random.get() < chance) {
            player.getInventory().addOrDrop(cutId, 1);
            player.getStats().addXp(StatType.Cooking, this == LEAPING_STURGEON ? 15 : 10, true);
            if (Random.get() < offcutsChance) {
                player.getInventory().addOrDrop(Items.FISH_OFFCUTS, 1);
                player.sendFilteredMessage("You gut the fish expertly, removing " + ItemDefinition.get(cutId).name + " and some fish offcuts.");
            } else {
                player.sendFilteredMessage("You gut the fish well, removing " + ItemDefinition.get(cutId).name + ".");
            }
        } else {
            player.sendFilteredMessage("You gut the fish sloppily, not producing anything usable.");
        }
    }

    private double getChance(int level) {
        return ((maxChance - baseChance) / (double) (levelForMaxChance - 1)) * level;
    }

    static {
        for (FishCutting fish : values()) {
            ItemItemAction.register(Tool.KNIFE, fish.fishId, (player, primary, fishItem) -> fish.cut(player, fishItem));
        }
    }
}
