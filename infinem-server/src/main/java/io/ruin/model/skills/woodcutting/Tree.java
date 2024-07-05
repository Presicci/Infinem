package io.ruin.model.skills.woodcutting;

import io.ruin.model.entity.player.PlayerCounter;

public enum Tree {

    CRYSTAL(23962, "crystal shards", 1, 1.6, 0.04, 0, -1, 0, true, 317647, PlayerCounter.CHOPPED_REGULAR),
    CELASTRUS(22935, "bark", 1, 16.8, 0.27, 0, -1, 60, true, 317647, PlayerCounter.CHOPPED_REGULAR),
    REGULAR(1511, "logs", 1, 25, 0.54, 25.0, -1, 60, true, 317647, PlayerCounter.CHOPPED_REGULAR),
    SAPLING(20799, "kindling", 1, 16.8, 0.27, 45.0, 0.0, 60, false, 317647, PlayerCounter.CHOPPED_SAPLING),
    ACHEY(2862, "achey logs", 1, 25, 0.54, 25.0, -1, 60, true, 317647, PlayerCounter.CHOPPED_ACHEY),
    OAK(1521, "oak logs", 15, 16.8, 0.27, 37.5, 45, 14, false, 361146, PlayerCounter.CHOPPED_OAK),
    WILLOW(1519, "willow logs", 30, 10.55, 0.135, 67.5, 50, 14, false, 289286, PlayerCounter.CHOPPED_WILLOW),
    TEAK(6333, "teak logs", 35, 10.55, 0.12, 85.0, 50, 15, false, 264336, PlayerCounter.CHOPPED_TEAK),
    JUNIPER(13355, "juniper logs", 42, 5, 0.077, 35.0, 0.0625, 14, false, 360000, PlayerCounter.CHOPPED_JUNIPER),
    MAPLE(1517, "maple logs", 45, 3.5, 0.067, 100.0, 100, 59, false, 221918, PlayerCounter.CHOPPED_MAPLE),
    MAHOGANY(6332, "mahogany logs", 50, 7, 0.064, 125.0, 100, 14, false, 220623, PlayerCounter.CHOPPED_MAHOGANY),
    ARCTIC_PINE(10810, "arctic pine logs", 54, 7.8, 0.096, 40.0, 140, 14, false, 144408, PlayerCounter.CHOPPED_REGULAR),
    YEW(1515, "yew logs", 60, 1.6, 0.04, 175.0, 190, 100, false, 145013, PlayerCounter.CHOPPED_YEW),
    SULLIUSCEP(6004, "", 65, 16.02, 0.178, 127.0, 0.0625, 100, false, 343000, PlayerCounter.CHOPPED_SULLIUSCEP),
    MAGIC(1513, "magic logs", 75, 2.34, 0.033, 250.0, 390, 100, false, 72321, PlayerCounter.CHOPPED_MAGIC),
    REDWOOD(19669, "redwood logs", 90, 2.73, 0.026, 380.0, 440, 200, false, 72321, PlayerCounter.CHOPPED_REDWOOD),
    ENTTRUNK(-1, "ent trunk", -1, 16.8, 0.27, 25, -1, 0, true, 317647, PlayerCounter.CHOPPED_ENTS), // Used for algo only;
    HOLLOW_TREE(3239, "bark", 45, 9, 0.033, 82.5, 60, 45, false, 214367, PlayerCounter.CHOPPED_REGULAR),
    DRAMEN_TREE(771, "dramen branch", 36, 16.8, 0.27, 0, 0.0, 0, false, 317647, PlayerCounter.CHOPPED_REGULAR);

    public final int log, levelReq, respawnTime, petOdds;
    public final double experience, baseChance, levelSlope;
    public final String treeName;
    public final boolean single;
    public final PlayerCounter counter;

    public int fellTime = -1;
    public double fellChance = -1;

    Tree(int log, String treeName, int levelReq, double baseChance, double levelSlope, double experience, double fellChance, int respawnTime, boolean single, int petOdds, PlayerCounter counter) {
        this.log = log;
        this.treeName = treeName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.experience = experience;
        this.fellChance = fellChance;
        this.respawnTime = respawnTime;
        this.single = single;
        this.petOdds = petOdds;
        this.counter = counter;
    }

    Tree(int log, String treeName, int levelReq, double baseChance, double levelSlope, double experience, int fellTime, int respawnTime, boolean single, int petOdds, PlayerCounter counter) {
        this.log = log;
        this.treeName = treeName;
        this.levelReq = levelReq;
        this.baseChance = baseChance;
        this.levelSlope = levelSlope;
        this.experience = experience;
        this.fellTime = fellTime;
        this.respawnTime = respawnTime;
        this.single = single;
        this.petOdds = petOdds;
        this.counter = counter;
    }
}
