package io.ruin.model.skills.woodcutting;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.Geode;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 7/5/2022
 */
public enum ClueNest {

    BEGINNER(23127,ClueType.BEGINNER.boxId, 5),
    EASY(19712, ClueType.EASY.boxId, 4),
    MEDIUM(19714, ClueType.MEDIUM.boxId, 3),
    HARD(19716, ClueType.HARD.boxId, 2),
    ELITE(19718, ClueType.ELITE.boxId, 1);

    public final int itemID, reward, weight;

    ClueNest(int itemID, int reward, int weight) {
        this.itemID = itemID;
        this.reward = reward;
        this.weight = weight;
    }

    public static int getRandomNest() {
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
        for(ClueNest nest : values()) {
            ItemAction.registerInventory(nest.itemID, "open", (player, item) -> {
                if (player.getInventory().isFull()) {
                    player.sendMessage("You don't have enough inventory space to do that.");
                    return;
                }
                item.setId(5075);   // Empty nest
                player.getInventory().add(nest.reward, 1);
                PlayerCounter.OPENED_BIRDS_NESTS.increment(player, 1);
                player.sendFilteredMessage("You take the clue box out of the bird's nest.");
            });
        }
    }

}
