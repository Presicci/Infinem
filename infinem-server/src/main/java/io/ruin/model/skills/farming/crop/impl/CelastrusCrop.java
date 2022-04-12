package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.farming.crop.TreeCrop;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
public class CelastrusCrop implements TreeCrop {

    //7910 17 fully harvested
    public static final CelastrusCrop INSTANCE = new CelastrusCrop();

    private CelastrusCrop() {

    }

    @Override
    public int getPetOdds() {
        return 9000;
    }

    @Override
    public int getSeed() {
        return 22869;
    }

    @Override
    public int getLevelReq() {
        return 85;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(160);
    }

    @Override
    public int getTotalStages() {
        return 5;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 3:
                return 1.0 / 128.0;
            case 2:
                return 2.0 / 128.0;
            case 1:
                return 4.0 / 128.0;
            case 0:
            default:
                return 8.0 / 128.0;
        }
    }

    @Override
    public double getPlantXP() {
        return 204;
    }

    @Override
    public int getContainerIndex() {
        return 8;
    }

    @Override
    public int getProduceId() {
        return 22935;
    }

    @Override
    public double getHarvestXP() {
        return 23.5;
    }

    public double getCheckHealthXP() {
        return 14130;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_CELASTRUS_TREE;
    }

    @Override
    public int getSapling() {
        return 22856;
    }

    @Override
    public int getSeedling() {
        return 22848;
    }

    @Override
    public int getWateredSeedling() {
        return 22852;
    }

    @Override
    public Item getPayment() {
        return new Item(Items.POTATO_CACTUS, 8);
    }
}
