package io.ruin.model.skills.cooking;

import io.ruin.model.content.tasksystem.relics.Relic;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Items;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/14/2023
 */
public enum Churnable {
    CHEESE(Items.CHEESE, 64.0, Items.BUCKET_OF_MILK, Items.BUCKET),
    CHEESE2(Items.CHEESE, 46.0, Items.POT_OF_CREAM),
    CHEESE3(Items.CHEESE, 23.5, Items.PAT_OF_BUTTER),
    CHEESE4(Items.CHEESE, 23.5, 24788),
    PAT_OF_BUTTER(Items.PAT_OF_BUTTER, 40.5, Items.BUCKET_OF_MILK, Items.BUCKET),
    PAT_OF_BUTTER2(Items.PAT_OF_BUTTER, 22.5, Items.POT_OF_CREAM),
    PAT_OF_CREAM(Items.POT_OF_CREAM, 18.0, Items.BUCKET_OF_MILK, Items.BUCKET);

    private final int productId, requiredItem, reqReplacement;
    private final double cookingExperience;

    Churnable(int productId, double cookingExperience, int requiredItem, int reqReplacement) {
        this.productId = productId;
        this.cookingExperience = cookingExperience;
        this.requiredItem = requiredItem;
        this.reqReplacement = reqReplacement;
    }

    Churnable(int productId, double cookingExperience, int requiredItem) {
        this(productId, cookingExperience, requiredItem, -1);
    }

    private static void churnOptions(Player player) {
        SkillItem[] skillItems = new SkillItem[ChurnProduct.values().length];
        for (int index = 0; index < ChurnProduct.values().length; index++) {
            skillItems[index] = ChurnProduct.values()[index].getSkillItem();
        }
        SkillDialogue.make(player, skillItems);
    }

    private static Churnable getIngredient(Player player, ChurnProduct churnProduct) {
        for (Churnable churn : churnProduct.potentialAction) {
            if (player.getInventory().hasId(churn.requiredItem)) {
                return churn;
            }
        }
        return null;
    }

    private static void churn(Player player, ChurnProduct churnProduct, int amount) {
        player.closeInterfaces();
        if (!player.getStats().check(StatType.Cooking, churnProduct.levelRequirement, "make this"))
            return;
        if (getIngredient(player, churnProduct) == null) {
            player.sendMessage("You need " + churnProduct.requirementsString + " to make this.");
            return;
        }
        player.startEvent(event -> {
            int amt = amount;
            while(amt --> 0) {
                Churnable churn = getIngredient(player, churnProduct);
                if (churn == null)
                    return;
                player.animate(2793);
                if (!player.getRelicManager().hasRelicEnalbed(Relic.PRODUCTION_MASTER)) {
                    event.delay(6);
                }
                if(!player.getInventory().hasId(churn.requiredItem)) {
                    return;
                }
                if (churn.reqReplacement == -1) {
                    player.getInventory().remove(churn.requiredItem, 1);
                    player.getInventory().add(churn.productId);
                } else {
                    player.getInventory().remove(churn.requiredItem, 1);
                    player.getInventory().add(churn.productId);
                    player.getInventory().addOrDrop(churn.reqReplacement, 1);
                }
                player.getStats().addXp(StatType.Cooking, churn.cookingExperience, true);
            }
            player.resetAnimation();
        });
    }

    static {
        ObjectAction.register("dairy churn", "churn", ((player, obj) -> churnOptions(player)));
    }

    @AllArgsConstructor
    private enum ChurnProduct {
        CHEESE(48, "milk, cream, or butter", Arrays.asList(Churnable.CHEESE, Churnable.CHEESE2, Churnable.CHEESE3, Churnable.CHEESE4)),
        PAT_OF_BUTTER(38, "milk or cream", Arrays.asList(Churnable.PAT_OF_BUTTER, Churnable.PAT_OF_BUTTER2)),
        PAT_OF_CREAM(21, "milk", Collections.singletonList(Churnable.PAT_OF_CREAM));

        private final int levelRequirement;
        private final String requirementsString;
        private final List<Churnable> potentialAction;

        private SkillItem getSkillItem() {
            return new SkillItem(potentialAction.get(0).productId).addAction(((player, amount, event) -> churn(player, this, amount)));
        }
    }
}
