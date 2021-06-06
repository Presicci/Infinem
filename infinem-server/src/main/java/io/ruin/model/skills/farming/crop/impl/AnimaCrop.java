package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
public enum AnimaCrop implements Crop {
    ATTAS(22881, 76, 8, 100, 8),
    IASOR(22883, 76, 8, 100, 17),
    KRONOS(22885, 76, 8, 100, 26);

    private final int seed, levelReq, totalStages, containerIndex;
    private final double plantXP;

    AnimaCrop(int seed, int levelReq, int totalStages, double plantXP, int containerIndex) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.totalStages = totalStages;
        this.containerIndex = containerIndex;
        this.plantXP = plantXP;
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
        return -1;
    }

    @Override
    public double getPlantXP() {
        return plantXP;
    }

    @Override
    public double getHarvestXP() {
        return 0;
    }

    @Override
    public PlayerCounter getCounter() {
        return null;
    }

    @Override
    public Item getPayment() {
        return null;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(640);
    }

    @Override
    public double getDiseaseChance(int compostType) {
        return 0;
    }

}
