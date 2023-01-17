package io.ruin.model.skills.fishing;

public enum FishingCatch {
    /**
     * Regular
     */
    SHRIMPS(317, 1, 10.0, 0.2, 435165),
    SARDINE(327, 5, 20.0, 0.15, 528000),
    HERRING(345, 10, 30.0, 0.13, 528000),
    ANCHOVIES(321, 15, 40.0, 0.15, 435165),
    MACKEREL(353, 16, 20.0, 0.05, 382609),
    TROUT(335, 20, 50.0, 0.24, 461808),
    COD(341, 23, 45.0, 0.06, 382609),
    PIKE(349, 25, 60.0, 0.14, 305792),
    SLIMY_EEL(3379, 28, 65.0, 0.18, 461808),    // Slightly higher rate than osrs but it's cool
    SALMON(331, 30, 70.0, 0.15, 461808),
    TUNA(359, 35, 80.0, 0.11, 128885),
    LOBSTER(377, 40, 90.0, 0.16, 116129),
    BASS(363, 46, 100.0, 0.08, 382609),
    SWORDFISH(371, 50, 100.0, 0.11, 128885),
    MONKFISH(7944, 62, 120.0, 0.3, 138583),
    SHARK(383, 76, 110.0, 0.12, 82243),
    ANGLERFISH(13439, 82, 120.0, 0.07, 78649),
    DARK_CRAB(11934, 85, 130.0, 0.2, 149434),
    MINNOWS(21356, 82, 26.0, 1.0, 977778),
    INFERNAL_EEL(21293, 80, 95.0, 0.31, 160000),
    SACRED_EEL(13339, 87, 105.0, 0.2, 99000),
    /**
     * Barbarian
     */
    LEAPING_TROUT(11328, 48, 15, 15, 50.0, 5.0, 0.42, 426954),
    LEAPING_SALMON(11330, 58, 30, 30, 70.0, 6.0, 0.24, 426954),
    LEAPING_STURGEON(11332, 70, 45, 45, 80.0, 7.0, 0.18, 426954),
    /**
     * Barehand
     */
    BARBARIAN_TUNA(359, 55, 15, 35, 80.0, 8.0, 0.17, 128885),
    BARBARIAN_SWORDFISH(371, 70, 15, 50, 100.0, 10.0, 0.17, 128885),
    BARBARIAN_SHARK(383, 96, 15, 76, 110.0, 11.0, 0.18, 82243),
    KARAMBWANJI(3150, 5, 5.0, 0.3, 528000),
    KARAMBWAN(3142, 65, 50.0, 0.41, 170874),
    MOLTEN_EEL(30086, 65, 50.0, 0.02, 82243);

    public final int id;

    public final int levelReq;

    public final int agilityReq;

    public final int strengthReq;

    public final double xp;

    public final double barbarianXp;

    public final double baseChance;

    public final int petOdds;

    FishingCatch(int id, int levelReq, double xp, double baseChance, int petOdds) {
        this(id, levelReq, 0, 0, xp, 0.0, baseChance, petOdds);
    }

    FishingCatch(int id, int levelReq, int agilityReq, int strengthReq, double xp, double barbarianXp, double baseChance, int petOdds) {
        this.id = id;
        this.levelReq = levelReq;
        this.agilityReq = agilityReq;
        this.strengthReq = strengthReq;
        this.xp = xp;
        this.barbarianXp = barbarianXp;
        this.baseChance = baseChance;
        this.petOdds = petOdds;
    }

}
