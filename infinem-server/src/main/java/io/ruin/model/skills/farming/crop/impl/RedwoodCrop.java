package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;

import static io.ruin.cache.ItemID.COINS_995;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/3/2021
 */
public class RedwoodCrop implements TreeCrop {

    public static final RedwoodCrop INSTANCE = new RedwoodCrop();

    private RedwoodCrop() {

    }

    @Override
    public int getPetOdds() {
        return 5000;
    }

    @Override
    public int getSeed() {
        return 22871;
    }

    @Override
    public int getLevelReq() {
        return 90;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(640);
    }

    @Override
    public int getTotalStages() {
        return 10;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch(compostType) {
            case 3:
                return 0.5 / 128.0;
            case 2:
                return 1.0 / 128.0;
            case 1:
                return 2.0 / 128.0;
            case 0:
            default:
                return 5.0 / 128.0;
        }
    }

    @Override
    public double getPlantXP() {
        return 230;
    }

    @Override
    public int getContainerIndex() {
        return 8;
    }

    @Override
    public int getProduceId() {
        return 19669;
    }

    @Override
    public double getHarvestXP() {
        return 0;
    }

    public double getCheckHealthXP() {
        return 22450;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_REDWOOD_TREE;
    }

    @Override
    public int getSapling() {
        return 22859;
    }

    @Override
    public int getSeedling() {
        return 22850;
    }

    @Override
    public int getWateredSeedling() {
        return 22854;
    }

    @Override
    public Item getPayment() {
        return new Item(COINS_995, 30000);
    }
}
