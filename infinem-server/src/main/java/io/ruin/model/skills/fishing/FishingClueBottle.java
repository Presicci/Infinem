package io.ruin.model.skills.fishing;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.content.ActivitySpotlight;
import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;

public enum FishingClueBottle {

    BEGINNER(23129, ClueType.BEGINNER, 1),
    EASY(13648, ClueType.EASY, 20),
    MEDIUM(13649, ClueType.MEDIUM, 40),
    HARD(13650, ClueType.HARD, 60),
    ELITE(13651, ClueType.ELITE, 80);

    public final int bottleId, levelThreshold;
    public final ClueType clueType;

    FishingClueBottle(int bottleId, ClueType clueType, int levelThreshold) {
        this.bottleId = bottleId;
        this.clueType = clueType;
        this.levelThreshold = levelThreshold;
    }

    public static void roll(Player player, FishingCatch fish, boolean barehand) {
        FishingClueBottle bottle = null;
        int fishLevel = fish.levelReq;
        if (barehand)
            fishLevel -= 20;

        for (FishingClueBottle cb : values()) {
            if (fishLevel >= cb.levelThreshold)
                bottle = cb;
        }
        if (bottle == null)
            return;
        double chance = ((100D + player.getStats().get(StatType.Fishing).currentLevel) / fish.petOdds);
        if (ActivitySpotlight.isActive(ActivitySpotlight.QUADRUPLE_CLUE_BOTTLE_CHANCE))
            chance *= 4;
        if (Random.get() < chance) {
            player.getInventory().addOrDrop(bottle.bottleId, 1);
            player.sendMessage("You catch a bottle!");
        }
    }

    static {
        for (FishingClueBottle clueBottle : values())
            ItemAction.registerInventory(clueBottle.bottleId, "open", (player, item) -> {
                player.getInventory().remove(item);
                if (player.getRelicManager().hasRelic(Relic.TREASURE_HUNTER)) {
                    player.getInventory().add(clueBottle.clueType.boxId, 2);
                } else {
                    player.getInventory().add(clueBottle.clueType.boxId, 1);
                }
                player.sendMessage("You crack the bottle and find a clue scroll inside!");
            });
    }

}
