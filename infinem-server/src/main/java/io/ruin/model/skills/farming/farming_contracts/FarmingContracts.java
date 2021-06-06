package io.ruin.model.skills.farming.farming_contracts;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.*;
import io.ruin.model.skills.farming.patch.PatchData;
import io.ruin.model.stat.StatType;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/4/2021
 */
@AllArgsConstructor
public enum FarmingContracts {
    // Easy
    POTATOES(45, 0,1, AllotmentCrop.POTATO, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    ONIONS(45, 0,1, AllotmentCrop.ONION, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    CABBAGES(45, 0,1, AllotmentCrop.CABBAGE, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    TOMATOES(45, 0,1, AllotmentCrop.TOMATO, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    SWEETCORN(45, 0,1, AllotmentCrop.SWEETCORN, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    STRAWBERRIES(45, 0,1, AllotmentCrop.STRAWBERRY, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),

    REDBERRIES(45, 0,2, BushCrop.REDBERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),
    CADAVABERRIES(45, 0,2, BushCrop.CADAVABERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),
    DWELLBERRIES(45, 0,2, BushCrop.DWELLBERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),
    JANGERBERRIES(48, 0,2, BushCrop.JANGERBERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),

    CACTUS(55, 0,3, CactusCrop.CACTUS, new PatchData[] { PatchData.FARMING_GUILD_CACTUS }, true),

    MARIGOLD(45, 0,1, FlowerCrop.MARIGOLDS, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),
    ROSEMARY(45, 0,1, FlowerCrop.ROSEMARY, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),
    NASTURTIUM(45, 0,1, FlowerCrop.NASTURTIUM, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),
    WOAD(45, 0,1, FlowerCrop.WOAD, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),
    LIMPWURT(45, 0,1, FlowerCrop.LIMPWURT, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),

    APPLE(85, 0,3, FruitTreeCrop.APPLE, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    BANANA(85, 0,3, FruitTreeCrop.BANANA, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    ORANGE(85, 0,3, FruitTreeCrop.ORANGE, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    CURRY(85, 0,3, FruitTreeCrop.CURRY, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),

    GUAM(65, 0,2, HerbCrop.GUAM, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    MARRENTILL(65, 0,2, HerbCrop.MARRENTILL, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    TARROMIN(65, 0,2, HerbCrop.TARROMIN, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    HARRALANDER(65, 0,2, HerbCrop.HARRALANDER, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    RANARR(65, 0,2, HerbCrop.RANARR, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    TOADFLAX(65, 0,2, HerbCrop.TOADFLAX, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    IRIT(65, 0,2, HerbCrop.IRIT, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    AVANTOE(65, 0,2, HerbCrop.AVANTOE, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),

    OAK(65, 0,3, WoodTreeCrop.OAK, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),
    WILLOW(65, 0,3, WoodTreeCrop.WILLOW, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),
    MAPLE(65, 0,3, WoodTreeCrop.MAPLE, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),

    // Medium
    STRAWBERRIES1(65, 1,2, AllotmentCrop.STRAWBERRY, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    WATERMELONS(65, 1,3, AllotmentCrop.WATERMELON, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    SNAPE_GRASS(65, 1,2, AllotmentCrop.SNAPE_GRASS, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),

    JANGERBERRIES1(65, 1,3, BushCrop.JANGERBERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),
    WHITEBERRIES(65, 1,3, BushCrop.WHITEBERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),
    POISON_IVY(70, 1,3, BushCrop.POISON_IVY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),

    CACTUS1(65, 1,4, CactusCrop.CACTUS, new PatchData[] { PatchData.FARMING_GUILD_CACTUS }, true),
    POTATO_CACTUS(65, 1,2, CactusCrop.POTATO_CACTUS, new PatchData[] { PatchData.FARMING_GUILD_CACTUS }, true),

    WHITE_LILY(65, 1,2, FlowerCrop.WHITE_LILY, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),

    CURRY1(85, 1,4, FruitTreeCrop.CURRY, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    PINEAPPLE(85, 1,4, FruitTreeCrop.PINEAPPLE, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    PAPAYA(85, 1,4, FruitTreeCrop.PAPAYA, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    PALM(85, 1,4, FruitTreeCrop.PALM, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),

    IRIT1(65, 1,3, HerbCrop.IRIT, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    AVANTOE1(65, 1,3, HerbCrop.AVANTOE, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    KWUARM(65, 1,3, HerbCrop.KWUARM, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    SNAPDRAGON(65, 1,3, HerbCrop.SNAPDRAGON, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    CADANTINE(67, 1,3, HerbCrop.CADANTINE, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    LANTADYME(63, 1,3, HerbCrop.LANTADYME, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),

    MAPLE1(65, 1,4, WoodTreeCrop.MAPLE, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),
    YEW(65, 1,4, WoodTreeCrop.YEW, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),
    MAGIC(75, 1,4, WoodTreeCrop.MAGIC, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),

    // Hard
    WATERMELONS1(85, 2,4, AllotmentCrop.WATERMELON, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),
    SNAPE_GRASS1(85, 2,3, AllotmentCrop.SNAPE_GRASS, new PatchData[] { PatchData.FARMING_GUILD_NORTH, PatchData.FARMING_GUILD_SOUTH }, false),

    WHITEBERRIES1(85, 2,4, BushCrop.WHITEBERRY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),
    POISON_IVY1(85, 2,4, BushCrop.POISON_IVY, new PatchData[] { PatchData.FARMING_GUILD_BUSH }, true),

    POTATO_CACTUS1(85, 2,3, CactusCrop.POTATO_CACTUS, new PatchData[] { PatchData.FARMING_GUILD_CACTUS }, true),

    WHITE_LILY1(85, 2,3, FlowerCrop.WHITE_LILY, new PatchData[] { PatchData.FARMING_GUILD_FLOWER }, false),

    PALM1(85, 2,5, FruitTreeCrop.PALM, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),
    DRAGONFRUIT(85, 2,5, FruitTreeCrop.DRAGONFRUIT, new PatchData[] { PatchData.FARMING_GUILD_FRUIT }, true),

    SNAPDRAGON1(85, 2,4, HerbCrop.SNAPDRAGON, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    CADANTINE1(85, 2,4, HerbCrop.CADANTINE, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    LANTADYME1(85, 2,4, HerbCrop.LANTADYME, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    DWARF_WEED(85, 2,4, HerbCrop.DWARF_WEED, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),
    TORSTOL(85, 2,4, HerbCrop.TORSTOL, new PatchData[] { PatchData.FARMING_GUILD_HERB }, false),

    REDWOOD(90, 2,5, RedwoodCrop.INSTANCE, new PatchData[] { PatchData.FARMING_GUILD_REDWOOD }, true),
    CELASTRUS(85, 2,5, CelastrusCrop.INSTANCE, new PatchData[] { PatchData.FARMING_GUILD_CELASTRUS }, true),

    MAPLE2(85, 2,5, WoodTreeCrop.MAPLE, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),
    YEW1(85, 2,5, WoodTreeCrop.YEW, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true),
    MAGIC2(85, 2,5, WoodTreeCrop.MAGIC, new PatchData[] { PatchData.FARMING_GUILD_TREE }, true)
    ;

    public int unlockLevel, difficulty, tier;
    public Crop crop;
    public PatchData[] patches;
    public boolean checkHealth;

    public static FarmingContracts generateContract(Player player, int difficulty) {
        if (player.farmingContract != null && player.farmingContract.difficulty <= difficulty) {
            return null;
        }
        List<FarmingContracts> possibleTasks = new ArrayList<>();
        for (FarmingContracts contract : values()) {
            if (contract.difficulty == difficulty && player.getStats().get(StatType.Farming).fixedLevel >= contract.unlockLevel) {
                possibleTasks.add(contract);
            }
        }
        player.farmingContract = possibleTasks.get(Random.get(1, possibleTasks.size()) - 1);
        player.contractCompleted = false;
        return player.farmingContract;
    }

    public static void completeFarmingContract(Player player, Crop crop, PatchData patchData) {
        if (player.farmingContract == null || crop == null || crop != player.farmingContract.crop || player.contractCompleted) {
            return;
        }
        if (Arrays.stream(player.farmingContract.patches).anyMatch(p -> p == patchData)) {
            player.sendMessage("<col=ff0000>You have completed your farming contract!  Go see Guildmaster Jane for your reward!</col>");
            PlayerCounter.FARMING_CONTRACTS_COMPLETED.increment(player, 1);
            player.contractCompleted = true;
        }
    }

    public boolean rewardContract(Player player) {
        Item seedPack = SeedPack.createSeedPack(tier);
        if (player.getInventory().hasFreeSlots(1)) {
            player.getInventory().add(seedPack);
            return true;
        } else {
            return false;
        }
    }
}
