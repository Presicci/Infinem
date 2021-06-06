package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.woodcutting.Tree;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 6/6/2021
 */
public enum HardWoodTreeCrop implements TreeCrop {
    TEAK(21486, 21477, 21469, 21473, Tree.TEAK, 35, 35, 7290, 7, TimeUtils.getMinutesToMillis(640), new Item(995,  4000), 8, PlayerCounter.GROWN_TEAK),
    MAHOGANY(21488, 21480, 21471, 21475, Tree.MAHOGANY, 55, 68, 15720, 8, TimeUtils.getMinutesToMillis(730), new Item(995,  8000), 30, PlayerCounter.GROWN_MAHOGANY);


    HardWoodTreeCrop(int seedId, int sapling, int seedling, int wateredSeedling, Tree treeType, int levelReq, double plantXP, double harvestXP, int totalStages, long stageTime, Item payment, int containerIndex, PlayerCounter counter) {
        this.plantXP = plantXP;
        this.checkHealthXP = harvestXP;
        this.seedId = seedId;
        this.treeType = treeType;
        this.levelReq = levelReq;
        this.containerIndex = containerIndex;
        this.totalStages = totalStages;
        this.counter = counter;
        this.payment = payment;
        this.sapling = sapling;
        this.seedling = seedling;
        this.wateredSeedling = wateredSeedling;
        this.stagetime = stageTime;
    }

    private final double plantXP, checkHealthXP;
    private final Tree treeType;
    private final int seedId, levelReq, containerIndex, sapling, seedling, wateredSeedling;
    private final int totalStages;
    private final PlayerCounter counter;
    private final long stagetime;

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
                return 0.1 / getTotalStages();
            case 2:
                return 0.2 / getTotalStages();
            case 1:
                return 0.3 / getTotalStages();
            case 0:
            default:
                return 0.4 / getTotalStages();
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

    private Item payment;

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
