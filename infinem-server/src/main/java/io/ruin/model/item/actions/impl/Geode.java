package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.item.actions.ItemAction;

public enum Geode {

    BEGINNER(23442, ClueType.BEGINNER.boxId, 5),
    EASY(20358, ClueType.EASY.boxId, 4),
    MEDIUM(20360, ClueType.MEDIUM.boxId, 3),
    HARD(20362, ClueType.HARD.boxId, 2),
    ELITE(20364, ClueType.ELITE.boxId, 1);

    public final int itemID, reward, weight;

    Geode(int itemID, int reward, int weight) {
        this.itemID = itemID;
        this.reward = reward;
        this.weight = weight;
    }

    public static int getRandomGeode() {
        int roll = Random.get(1, 11);
        for (Geode geode : Geode.values()){
            roll -= geode.weight;
            if (roll <= 0) {
                return geode.itemID;
            }
        }
        return BEGINNER.itemID; // Default case
    }

    static {
        for(Geode geode : values()) {
            ItemAction.registerInventory(geode.itemID, "open", (player, item) -> {
                player.getInventory().remove(item);
                player.getInventory().add(geode.reward, 1);
                player.sendMessage("You open the geode and find a clue scroll.");
            });
        }
    }

}
