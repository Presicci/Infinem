package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 3/31/2022
 */
public class SeaweedCrop implements Crop {
    public static final SeaweedCrop INSTANCE = new SeaweedCrop();

    @Override
    public int getSeed() {
        return 21490;
    }

    @Override
    public int getLevelReq() {
        return 23;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(10);
    }

    @Override
    public int getTotalStages() {
        return 4;
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
        return 19;
    }

    @Override
    public int getContainerIndex() {
        return 4;
    }

    @Override
    public int getProduceId() {
        return 21504;
    }

    @Override
    public int getPetOdds() {
        return 7500;
    }

    @Override
    public double getHarvestXP() {
        return 21;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_SEAWEED;
    }

    @Override
    public Item getPayment() {
        return new Item(21555, 200);
    }
}
