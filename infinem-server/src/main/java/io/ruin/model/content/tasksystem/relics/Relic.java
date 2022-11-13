package io.ruin.model.content.tasksystem.relics;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/11/2022
 */
@AllArgsConstructor
public enum Relic {
    ENDLESS_HARVEST(1,
            "- Resources gathered from Fishing, Woodcutting and Mining will be multiplied by 2. Experience is granted per resource gathered." +
            "\n- The resources you gather are sent directly to your Bank if you have space. If not, they will be placed in your Inventory."),
    PRODUCTION_MASTER(1,
            "- When doing the following activities, all items will be processed at once, awarding full XP:" +
            "\n- Smelting ores, smithing bars and making cannonballs" +
            "\n- Fletching logs and cutting bolt tips" +
            "\n- Cleaning herbs and making potions" +
            "\n- Cooking food and making jugs of wine" +
            "\n- Crafting leather, uncut gems, glass, jewellery, pottery, battlestaves, and spinning flax/wool")
    ;

    @Getter
    private final int tier;
    @Getter
    private final String description;

    public static int tiers;

    static {
        for (Relic relic : Relic.values()) {
            if (relic.getTier() > tiers) {
                tiers = relic.getTier();
            }
        }
    }
}
