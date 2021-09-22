package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/6/2021
 */
@AllArgsConstructor
public enum BelladonnaCrop implements Crop {
    BELLADONNA(5281, 2398, 63, 91, 512, 4, FlowerCrop.MARIGOLDS, null, 4, 8000, PlayerCounter.HARVESTED_NIGHTSHADE);

    private final int seedId, produceId, levelReq;
    private final double plantXP, harvestXP;
    private final int totalStages;
    private final FlowerCrop protectionFlower;
    private final Item payment;
    private final int containerIndex;
    private final int petOdds;
    private final PlayerCounter counter;

    @Override
    public Item getPayment() {
        return payment;
    }

    public FlowerCrop getProtectionFlower() {
        return protectionFlower;
    }

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
        return TimeUtils.getMinutesToMillis(80);
    }

    @Override
    public int getTotalStages() {
        return totalStages;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
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
        return produceId;
    }

    @Override
    public double getHarvestXP() {
        return harvestXP;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }
}
