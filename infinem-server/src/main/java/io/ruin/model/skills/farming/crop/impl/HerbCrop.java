package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.stat.StatType;


public enum HerbCrop implements Crop {
    GUAM(5291, 199, 9, 11.0, 12.5, 4, PlayerCounter.HARVESTED_GUAM, 10.2, 31.6),
    MARRENTILL(5292, 201, 14, 13.5, 15, 4, PlayerCounter.HARVESTED_MARRENTILL, 11.3, 31.6),
    TARROMIN(5293, 203, 19, 16, 18, 4, PlayerCounter.HARVESTED_TARROMIN, 12.5, 31.6),
    HARRALANDER(5294, 205, 26, 21.5, 24, 4, PlayerCounter.HARVESTED_HARRALANDER, 14.5, 31.6),
    RANARR(5295, 207, 32, 27, 30.5, 4, PlayerCounter.HARVESTED_RANARR, 15.6, 31.6),
    TOADFLAX(5296, 3049, 38, 34, 38.5, 4, PlayerCounter.HARVESTED_TOADFLAX, 17.2, 31.6),
    IRIT(5297, 209, 44, 43, 48.5, 4, PlayerCounter.HARVESTED_IRIT, 18.4, 31.6),
    AVANTOE(5298, 211, 50, 54.5, 61.5, 4, PlayerCounter.HARVESTED_AVANTOE, 19.9, 31.6),
    KWUARM(5299, 213, 56, 69, 78, 4, PlayerCounter.HARVESTED_KWUARM, 21.5, 31.6),
    SNAPDRAGON(5300, 3051, 62, 87.5, 98.5, 4, PlayerCounter.HARVESTED_SNAPDRAGON, 22.7, 31.6),
    CADANTINE(5301, 215, 67, 106.5, 120.0, 4, PlayerCounter.HARVESTED_CADANTINE, 23.8, 31.6),
    LANTADYME(5302, 2485, 73, 134.5, 151.5, 4, PlayerCounter.HARVESTED_LANTADYME, 25.4, 31.6),
    DWARF_WEED(5303, 217, 79, 170.5, 192, 4, PlayerCounter.HARVESTED_DWARF_WEED, 26.6, 31.6),
    TORSTOL(5304, 219, 85, 199.5, 224.5, 4, PlayerCounter.HARVESTED_TORSTOL, 28.1, 31.6);

    HerbCrop(int seed, int herbId, int levelReq, double plantXP, double pickXP, int containerIndex, PlayerCounter counter, double baseChance, double maxChance) {
        this.seed = seed;
        this.levelReq = levelReq;
        this.containerIndex = containerIndex;
        this.herbId = herbId;
        this.plantXP = plantXP;
        this.pickXP = pickXP;
        this.counter = counter;
        this.baseChance = baseChance;
        this.maxChance = maxChance;
    }

    final int seed, levelReq, containerIndex, herbId;
    final double plantXP, pickXP, baseChance, maxChance;
	final PlayerCounter counter;

    @Override
    public int getPetOdds() {
        return 98364;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

    @Override
    public int getHesporiSeedChance() {
        return 196;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(20);
    }

    @Override
    public int getTotalStages() {
        return 4;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 3:
                return 3.0 / 128.0;
            case 2:
                return 6.0 / 128.0;
            case 1:
                return 14.0 / 128.0;
            case 0:
            default:
                return 27.0 / 128.0;
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
        return herbId;
    }

    @Override
    public double getHarvestXP() {
        return pickXP;
    }

    public double getSaveProductChance(Player player) {
        int lvl = player.getStats().get(StatType.Farming).currentLevel;
        double perLevel = (maxChance - baseChance) / 99;
        return baseChance + (lvl * perLevel);
    }
}
