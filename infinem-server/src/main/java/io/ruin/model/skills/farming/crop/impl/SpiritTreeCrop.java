package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.Items;
import io.ruin.model.skills.farming.crop.TreeCrop;

public class SpiritTreeCrop implements TreeCrop {

    public static final SpiritTreeCrop INSTANCE = new SpiritTreeCrop();

    private SpiritTreeCrop() {

    }

    @Override
    public int getPetOdds() {
        return 5000;
    }

    @Override
    public int getSeed() {
        return 5317;
    }

    @Override
    public int getLevelReq() {
        return 83;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(320);
    }

    @Override
    public int getTotalStages() {
        return 11;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
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
        return 199;
    }

    @Override
    public int getContainerIndex() {
        return 8;
    }

    @Override
    public int getProduceId() {
        return -1;
    }

    @Override
    public double getHarvestXP() {
        return 0;
    }

    public double getCheckHealthXP() {
        return 19301;
    }

    @Override
    public PlayerCounter getCounter() {
        return PlayerCounter.GROWN_SPIRIT_TREE;
    }

    @Override
    public int getHesporiSeedChance() {
        return 10;
    }

    @Override
    public int getSapling() {
        return 5375;
    }

    @Override
    public int getSeedling() {
        return 5363;
    }

    @Override
    public int getWateredSeedling() {
        return 5369;
    }

    @Override
    public Item getPayment() {
        return new Item(Items.MONKEY_NUTS, 5);
    }
}
