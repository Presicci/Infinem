package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.item.actions.ItemAction;

public enum Geode {

    BEGINNER(23442, ClueType.BEGINNER.boxId),
    EASY(20358, ClueType.EASY.boxId),
    MEDIUM(20360, ClueType.MEDIUM.boxId),
    HARD(20362, ClueType.HARD.boxId),
    ELITE(20364, ClueType.ELITE.boxId);

    public final int itemID, reward;

    Geode(int itemID, int reward) {
        this.itemID = itemID;
        this.reward = reward;
    }

    public static int getRandomGeode() {
        int randomGeode = Random.get(Geode.values().length - 1);
        return Geode.values()[randomGeode].itemID;
    }

    static {
        for(Geode geode : values()) {
            ItemAction.registerInventory(geode.itemID, "open", (player, item) -> {
                item.setId(geode.reward);
                player.sendMessage("You open the geode and find a clue scroll.");
            });
        }
    }

}
