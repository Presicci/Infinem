package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.item.pet.Pet;

import static io.ruin.cache.ItemID.COINS_995;

public class WintertodtCrate {

    public static final LootTable TABLE = new LootTable()
            .addTable(234,
                    new LootItem(1522, 50, 150, 1),       //Oak logs
                    new LootItem(6334, 35, 60, 1),        //Teak logs
                    new LootItem(8836, 20, 50, 1),        //Mahogany logs
                    new LootItem(1516, 20, 50, 1),        //Yew logs
                    new LootItem(7937, 200, 400, 1),      //Pure essence
                    new LootItem(445, 20, 70, 1),         //Gold ore
                    new LootItem(450, 5, 15, 1),          //Adamantite ore
                    new LootItem(452, 1, 3, 1),           //Runite ore
                    new LootItem(5295, 1, 5, 1),          //Ranarr seed
                    new LootItem(5300, 1, 10, 1),         //Snapdragon seed
                    new LootItem(5314, 1, 5, 1),          //Maple seed
                    new LootItem(5315, 1, 4, 1),          //Yew seeds
                    new LootItem(5316, 1, 5, 1),          //Magic seed
                    new LootItem(5317, 1, 5, 1),          //Spirit seed
                    new LootItem(5321, 1, 3, 1),          //Watermelon seed
                    new LootItem(1618, 2, 10, 1),         //Uncut diamond
                    new LootItem(13422, 5, 24, 1),        //Saltpetre
                    new LootItem(COINS_995, 5050, 8000, 1),     //Coins
                    new LootItem(384, 5, 21, 1),          //Raw shark
                    new LootItem(13574, 10, 20, 1),       //Dynamite
                    new LootItem(20718, 5, 20, 1)         //Burnt pages
            ).addTable(10,
                    new LootItem(Items.PYROMANCER_GARB, 1, 1000),
                    new LootItem(Items.PYROMANCER_ROBE, 1, 1000),
                    new LootItem(Items.PYROMANCER_HOOD, 1, 1000),
                    new LootItem(Items.PYROMANCER_BOOTS, 1, 1000),
                    new LootItem(Items.WARM_GLOVES, 1, 1000),
                    new LootItem(Items.BRUMA_TORCH, 1, 1000),
                    new LootItem(Items.TOME_OF_FIRE, 1, 150),
                    new LootItem(Items.DRAGON_AXE, 1, 15)
            );

    static {
        ItemAction.registerInventory(20703, "open", (player, item) -> {
            item.remove();
            Item firstReward = TABLE.rollItem();
            Item secondReward = TABLE.rollItem();
            if (Random.rollDie(2500))
                Pet.PHOENIX.unlock(player);
            player.getInventory().addOrDrop(firstReward.getId(), firstReward.getAmount());
            player.getInventory().addOrDrop(secondReward.getId(), secondReward.getAmount());
            player.dialogue(new MessageDialogue("You have earned: " + firstReward.getDef().name + " x " + firstReward.getAmount() + ", "
                    + secondReward.getDef().name + " x " + secondReward.getAmount() + "."));
            player.getCollectionLog().collect(firstReward);
            player.getCollectionLog().collect(secondReward);
        });
    }
}
