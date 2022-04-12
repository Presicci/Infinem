package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.Crop;


public enum MushroomCrop implements Crop {
    BITTERCAP(5282, 6004, 53, 61.5, 57.7, 4, PlayerCounter.HARVESTED_BITTERCAP);

    MushroomCrop(int seed, int herbId, int levelReq, double plantXP, double pickXP, int containerIndex, PlayerCounter counter) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.containerIndex = containerIndex;
        this.herbId = herbId;
        this.plantXP = plantXP;
        this.pickXP = pickXP;
        this.counter = counter;
    }

    int seed, levelReq, containerIndex, herbId;
    double plantXP, pickXP;
	PlayerCounter counter;

    @Override
    public int getPetOdds() {
        return 7500;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
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
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(40);
    }

    @Override
    public int getTotalStages() {
        return 6;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 3:
                return 2.0 / 128.0;
            case 2:
                return 3.0 / 128.0;
            case 1:
                return 6.0 / 128.0;
            case 0:
            default:
                return 12.0 / 128.0;
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
        return herbId;
    }

    @Override
    public double getHarvestXP() {
        return pickXP;
    }


}
