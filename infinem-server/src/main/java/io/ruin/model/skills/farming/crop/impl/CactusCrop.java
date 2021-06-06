package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
@AllArgsConstructor
public enum CactusCrop implements Crop {

    CACTUS(5280, 6016, 55, 66.5, 25, 374, 7, TimeUtils.getMinutesToMillis(80), 8, new Item(COINS_995, 5000), PlayerCounter.GROWN_CACTUS),
    POTATO_CACTUS(22873, 3138, 64, 68, 68, 230, 7, TimeUtils.getMinutesToMillis(10), 32, new Item(COINS_995, 5000), PlayerCounter.GROWN_POTATO_CACTUS);

    private final int seedId, produceId, levelReq;
    private final double plantXP, harvestXP, checkHealthXP;
    private final int totalStages;
    private final long stageTime;
    private final int containerIndex;
    private final Item payment;
    private final PlayerCounter counter;

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
        return stageTime;
    }

    @Override
    public int getTotalStages() {
        return totalStages;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
            case 3:
                return 0.7 / 6;
            case 2:
                return 0.15 / 6;
            case 1:
                return 0.2 / 6;
            case 0:
            default:
                return 0.3 / 6;
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

    public double getCheckHealthXP() {
        return checkHealthXP;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

    @Override
    public Item getPayment() {
        return payment;
    }
}
