package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.farming.crop.Crop;

public enum HopsCrop implements Crop {
    BARLEY(5305, 6006, 3, 4, 8.5, 9.5, 49, new Item(Items.COMPOST, 3), 112416, PlayerCounter.HARVESTED_BARLEY, 244),
    HAMMERSTONE(5307, 5994, 4, 4, 9, 10, 4, new Item(Items.MARIGOLDS, 1), 112416, PlayerCounter.HARVESTED_HAMMERSTONE, 244),
    ASGARNIAN(5308, 5996, 8, 5, 10.5, 12, 11, new Item(Items.ONIONS_10, 1), 89993, PlayerCounter.HARVESTED_ASGARNIAN, 179),
    JUTE(5306, 5931, 13, 5, 13, 14.5, 56, new Item(Items.BARLEY_MALT, 6), 89993, PlayerCounter.HARVESTED_JUTE, 179),
    YANILLIAN(5309, 5998, 16, 6, 14.5, 16, 19, new Item(Items.TOMATOES_5, 1), 74944, PlayerCounter.HARVESTED_YANILLIAN, 149),
    KRANDORIAN(5310, 6000, 21, 7, 17.5, 19.5, 28, new Item(Items.CABBAGES_10, 3), 64238, PlayerCounter.HARVESTED_KRANDORIAN, 128),
    WILDBLOOD(5311, 6002, 28, 8, 23, 26, 38, new Item(Items.NASTURTIUMS, 1), 56208, PlayerCounter.HARVESTED_WILDBLOOD, 112);

    private final int seed, levelReq, totalStages, containerIndex, produceId, petOdds;
    private final double plantXP, harvestXP;
    private final PlayerCounter counter;
    private final Item payment;
    private final int hesporiSeedChance;

    HopsCrop(int seed, int produceId, int levelReq, int totalStages, double plantXP, double harvestXP, int containerIndex, Item payment, int petOdds, PlayerCounter counter, int hesporiSeedChance) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.totalStages = totalStages;
        this.containerIndex = containerIndex;
        this.produceId = produceId;
        this.plantXP = plantXP;
        this.harvestXP = harvestXP;
        this.counter = counter;
        this.petOdds = petOdds;
        this.payment = payment;
        this.hesporiSeedChance = hesporiSeedChance;
    }

    @Override
    public int getPetOdds() {
        return petOdds;
    }

    @Override
    public Item getPayment() {
        return payment;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public int getTotalStages() {
        return totalStages;
    }

    @Override
    public int getContainerIndex() {
        return containerIndex;
    }

    @Override
    public int getProduceId() {
        return produceId;
    }

    @Override
    public double getPlantXP() {
        return plantXP;
    }

    @Override
    public double getHarvestXP() {
        return harvestXP;
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
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(10);
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 3:
                return 3.0 / 128.0;
            case 2:
                return 5.0 / 128.0;
            case 1:
                return 9.0 / 128.0;
            case 0:
            default:
                return 18.0 / 128.0;
        }
    }

}
