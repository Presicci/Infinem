package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.Crop;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/30/2022
 */
public class VineCrop implements Crop {
    public static final VineCrop INSTANCE = new VineCrop();

    @Override
    public int getSeed() {
        return 13657;
    }

    @Override
    public int getLevelReq() {
        return 36;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(5);
    }

    @Override
    public int getTotalStages() {
        return 7;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        return 0;
    }

    @Override
    public double getPlantXP() {
        return 31.5;
    }

    @Override
    public int getContainerIndex() {
        return 2;
    }

    @Override
    public int getProduceId() {
        return 1987;
    }

    @Override
    public int getPetOdds() {
        return 385426;
    }

    @Override
    public double getHarvestXP() {
        return 40;
    }

    public double getCheckHealthXP() {
        return 625;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_GRAPE_VINES;
    }
}
