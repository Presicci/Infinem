package io.ruin.model.skills.mining;

public enum Rock {

    BLURITE(668, "blurite", 10, 45, 5, 17.5, 25, 1000000, 1, 10),
    COPPER(436, "copper", 1, 60, 5, 17.5, 5, 741600, 1.0 / 8, 10),
    TIN(438, "tin", 1, 60, 5, 17.5, 5, 741600, 1.0 / 8, 10),
    IRON(440, "iron", 15, 43, 0.95, 25.0, 10, 741600, 1.0 / 4, 15),
    SILVER(442, "silver", 20, 23.5, 0.7, 30.0, 15, 741600, 1.0 / 4, 15),
    COAL(453, "coal", 30, 16.4, 0.33, 35.0, 20, 290640, 1.0 / 9, 15),
    GOLD(444, "gold", 40, 13.67, 0.27, 65.0, 20, 296640, 1.0 / 5, 20),
    SANDSTONE(new int[]{6971, 6973, 6975, 6977}, "sandstone", 35, 45, 0.65, new int[]{30, 40, 50, 60}, 5, 741600, 1),
    GRANITE(new int[]{6979, 6981, 6983}, "granite", 45, 40, 0.45, new int[]{50, 60, 75}, 5, 741600, 1),
    MITHRIL(447, "mithril", 55, 12, 0.19, 75.0, 20, 148320, 1.0 / 5, 25),
    CLAY(434, "clay", 1, 50, 1.08, 5.0, 3, 741600, 1.0 / 5, 30),
    ADAMANT(449, "adamant", 70, 7.5, 0.11, 85.0, 25, 59328, 1.0 / 4, 30),
    LOVAKITE(13356, "lovakite", 65, 8, 0.14, 60.0, 40, 245562, 1),
    AMETHYST(21347, "amethyst", 92, 3.52, 0.156, 246.0, 120, 46350, 1),
    RUNE(451, "rune", 85, 6.64, 0.11, 100.0, 30, 42377, 2.0 / 5, 75),
    GEM_ROCK(-1, "gem", 40, 18, 0.165, 65.0, 35, 211886, 1.0 / 3, 50),
    TE_SALT(22593, "te salt", 72, 70, 1, 5.0, 9, 1000000, 1),
    EFH_SALT(22595, "efh salt", 72, 70, 1, 5.0, 9, 1000000, 1),
    URT_SALT(22597, "urt salt", 72, 70, 1, 5.0, 9, 1000000, 1),
    BASALT(22603, "basalt", 72, 70, 1, 5.0, 9, 1000000, 1),
    LIMESTONE(3211, "limestone", 10, 46.5, 1.02, 26.5, 20, 1000000, 1);

    public final int ore, levelReq, respawnTime, petOdds;
    public final String rockName;
    public final double experience;
    public final int[] multiOre;
    public final int[] multiExp;
    public final double depleteChance, baseChance, levelSlope;
    public int depleteTime;

    Rock(int ore, String rockName, int levelReq, double baseChance, double levelSlope, double experience, int respawnTime, int petOdds, double depleteChance) {
        this.ore = ore;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.multiOre = null;
        this.multiExp = null;
        this.depleteChance = depleteChance;
    }

    Rock(int ore, String rockName, int levelReq, double baseChance, double levelSlope, double experience, int respawnTime, int petOdds, double depleteChance, int depleteTime) {
        this.ore = ore;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.multiOre = null;
        this.multiExp = null;
        this.depleteChance = depleteChance;
        this.depleteTime = depleteTime;
    }

    Rock(int[] multiOre, String rockName, int levelReq, double baseChance, double levelSlope, int[] multiExp, int respawnTime, int petOdds, double depleteChance) {
        this.multiOre = multiOre;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.multiExp = multiExp;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.ore = -1;
        this.experience = -1;
        this.depleteChance = depleteChance;
    }

    Rock(int[] multiOre, String rockName, int levelReq, double baseChance, double levelSlope, double experience, int respawnTime, int petOdds, double depleteChance) {
        this.multiOre = multiOre;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.ore = -1;
        multiExp = null;
        this.depleteChance = depleteChance;
    }

    Rock(int[] multiOre, String rockName, int levelReq, double baseChance, double levelSlope, double experience, int respawnTime, int petOdds, double depleteChance, int depleteTime) {
        this.multiOre = multiOre;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.ore = -1;
        multiExp = null;
        this.depleteChance = depleteChance;
        this.depleteTime = depleteTime;
    }
}
