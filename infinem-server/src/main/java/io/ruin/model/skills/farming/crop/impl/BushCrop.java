package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.farming.crop.Crop;

import static io.ruin.cache.ItemID.COINS_995;

public enum BushCrop implements Crop {
    REDBERRY(5101, 1951, 10, 5, 11.5, 64, 4.5, 5, new Item(Items.CABBAGES_10, 4), 44966, PlayerCounter.GROWN_REDBERRY, 89),
    CADAVABERRY(5102, 753, 22, 6, 18, 102.5, 7, 15, new Item(Items.TOMATOES_5, 3), 37472, PlayerCounter.GROWN_CADAVABERRY, 74),
    DWELLBERRY(5103, 2126, 36, 7, 31.5, 177.5, 12, 26, new Item(Items.STRAWBERRIES_5, 3), 32119, PlayerCounter.GROWN_DWELLBERRY, 64),
    JANGERBERRY(5104, 247, 48, 8, 50.5, 284.5, 19, 38, new Item(Items.WATERMELON, 6), 28104, PlayerCounter.GROWN_JANGERBERRY, 56),
    WHITEBERRY(5105, 239, 59, 8, 78, 437.5, 4.5, 51, new Item(Items.MUSHROOM, 8), 28104, PlayerCounter.GROWN_WHITEBERRY, 56),
    POISON_IVY(5106, 6018, 70, 8, 120, 675, 4.5, 197, null, 28104, PlayerCounter.GROWN_POISON_IVY, 56);

    private final int seed, levelReq, totalStages, containerIndex, produceId, petOdds;
    private final double plantXP, harvestXP, checkHealthXP;
    private final PlayerCounter counter;
    private final Item payment;
    private final int hesporiSeedChance;

    BushCrop(int seed, int produceId, int levelReq, int totalStages, double plantXP, double checkHealthXP, double harvestXP, int containerIndex, Item payment, int petOdds, PlayerCounter counter, int hesporiSeedChance) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.totalStages = totalStages;
        this.containerIndex = containerIndex;
        this.produceId = produceId;
        this.plantXP = plantXP;
        this.harvestXP = harvestXP;
        this.checkHealthXP = checkHealthXP;
        this.counter = counter;
        this.payment = payment;
        this.petOdds = petOdds;
        this.hesporiSeedChance = hesporiSeedChance;
    }

    @Override
    public Item getPayment() {
        return payment;
    }

    @Override
    public int getPetOdds() {
        return petOdds;
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

    public double getCheckHealthXP() {
        return checkHealthXP;
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
        return TimeUtils.getMinutesToMillis(20);
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 3:
                return 2.0 / 128.0;
            case 2:
                return 4.0 / 128.0;
            case 1:
                return 8.0 / 128.0;
            case 0:
            default:
                return 16.0 / 128.0;
        }
    }
}
