package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.woodcutting.Tree;

import static io.ruin.cache.ItemID.COINS_995;

public enum WoodTreeCrop implements TreeCrop {
    OAK(5312, 6043, 5370, 5358, 5364, Tree.OAK, 15, 14, 467.3, 4, new Item(Items.TOMATOES_5, 1), 8, 22483, PlayerCounter.GROWN_OAK, 44),
    WILLOW(5313, 6045, 5371, 5359, 5365, Tree.WILLOW, 30, 25, 1456.5, 6, new Item(Items.APPLES_5, 1), 15, 16059, PlayerCounter.GROWN_WILLOW, 32),
    MAPLE(5314, 6047, 5372, 5360, 5366, Tree.MAPLE, 45, 45, 3403.4, 8, new Item(Items.ORANGES_5, 1), 24, 14052, PlayerCounter.GROWN_MAPLE, 28),
    YEW(5315, 6049, 5373, 5361, 5367, Tree.YEW, 60, 81, 7069, 10, new Item(Items.CACTUS_SPINE, 10), 35, 11242, PlayerCounter.GROWN_YEW, 22),
    MAGIC(5316, 6051, 5374, 5362, 5368, Tree.MAGIC, 75, 145.5, 13768.3, 12, new Item(Items.COCONUT, 25), 48, 9368, PlayerCounter.GROWN_MAGIC, 18);


    WoodTreeCrop(int seedId, int roots, int sapling, int seedling, int wateredSeedling, Tree treeType, int levelReq, double plantXP, double harvestXP, int totalStages, Item payment, int containerIndex, int petOdds, PlayerCounter counter, int hesporiSeedChance) {
        this.plantXP = plantXP;
        this.checkHealthXP = harvestXP;
        this.seedId = seedId;
        this.treeType = treeType;
        this.levelReq = levelReq;
        this.containerIndex = containerIndex;
        this.totalStages = totalStages;
        this.roots = roots;
        this.counter = counter;
        this.payment = payment;
        this.sapling = sapling;
        this.seedling = seedling;
        this.wateredSeedling = wateredSeedling;
        this.petOdds = petOdds;
        this.hesporiSeedChance = hesporiSeedChance;
    }


	private final Item payment;
    private final double plantXP, checkHealthXP;
    private final Tree treeType;
    private final int seedId, levelReq, containerIndex, sapling, seedling, wateredSeedling, petOdds;
    private final int totalStages;
    private final int roots;
    private final PlayerCounter counter;
    private final int hesporiSeedChance;

    @Override
    public int getPetOdds() {
        return petOdds;
    }

    @Override
    public int getSeed() {
        return seedId;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(40);
    }

    @Override
    public int getTotalStages() {
        return totalStages;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 3:
                return 1.0 / 128.0;
            case 2:
                return 2.0 / 128.0;
            case 1:
                return 5.0 / 128.0;
            case 0:
            default:
                return 10.0 / 128.0;
        }
    }

    @Override
    public double getPlantXP() {
        return plantXP;
    }

    @Override
    public int getContainerIndex() {
        return containerIndex;
    }

    @Override
    public int getProduceId() {
        return treeType.log;
    }

    @Override
    public double getHarvestXP() {
        return checkHealthXP;
    }

    public Tree getTreeType() {
        return treeType;
    }

    public int getRoots() {
        return roots;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

    @Override
    public int getHesporiSeedChance() {
        return hesporiSeedChance;
    }

    @Override
    public Item getPayment() {
        return payment;
    }

    @Override
    public int getSapling() {
        return sapling;
    }

    @Override
    public int getSeedling() {
        return seedling;
    }

    @Override
    public int getWateredSeedling() {
        return wateredSeedling;
    }
}
