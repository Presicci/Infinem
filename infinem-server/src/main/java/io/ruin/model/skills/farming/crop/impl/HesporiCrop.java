package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/29/2022
 */
public class HesporiCrop implements Crop {

    public static final HesporiCrop INSTANCE = new HesporiCrop();

    @Override
    public int getPetOdds() {
        return -1;
    }

    @Override
    public int getSeed() {
        return 22875;
    }

    @Override
    public int getLevelReq() {
        return 65;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(640);
    }

    @Override
    public int getTotalStages() {
        return 3;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        return 0;
    }

    @Override
    public double getPlantXP() {
        return 62;
    }

    @Override
    public int getContainerIndex() {
        return 4;
    }

    @Override
    public int getProduceId() {
        return 23044;
    }

    @Override
    public double getHarvestXP() {
        return 12600;
    }

    @Override
    public PlayerCounter getCounter() {
        return null;
    }

    @Override
    public Item getPayment() {
        return null;
    }
}
