package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.woodcutting.Tree;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/6/2021
 */
@AllArgsConstructor
public enum HardWoodTreeCrop implements TreeCrop {
    TEAK(21486, 21477, 21469, 21473, Tree.TEAK, 35, 35, 7290, 7, TimeUtils.getMinutesToMillis(640), new Item(995,  4000), 8, PlayerCounter.GROWN_TEAK),
    MAHOGANY(21488, 21480, 21471, 21475, Tree.MAHOGANY, 55, 68, 15720, 8, TimeUtils.getMinutesToMillis(730), new Item(995,  8000), 30, PlayerCounter.GROWN_MAHOGANY);

    private final int seedId, sapling, seedling, wateredSeedling;
    private final Tree treeType;
    private final int levelReq;
    private final double plantXP, checkHealthXP;
    private final int totalStages;
    private final long stagetime;
    private Item payment;
    private final int containerIndex;
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
        return stagetime;
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
        return treeType.log;
    }

    @Override
    public double getHarvestXP() {
        return checkHealthXP;
    }

    public Tree getTreeType() {
        return treeType;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

    @Override
    public Item getPayment() {
        return payment;
    }

    @Override
    public int getSapling() {
        return sapling;
    }

    @Override
    public int getSeedling() {
        return seedling;
    }

    @Override
    public int getWateredSeedling() {
        return wateredSeedling;
    }
}
