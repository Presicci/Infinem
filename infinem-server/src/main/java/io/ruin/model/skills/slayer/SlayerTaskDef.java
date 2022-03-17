package io.ruin.model.skills.slayer;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 1/25/2022
 */
public class SlayerTaskDef {
    private final int weighing;
    private final int min;
    private final int max;
    private int minExtended;
    private int maxExtended;
    private final int creatureUid;

    public SlayerTaskDef(int weighing, int min, int max, int creatureUid) {
        this.weighing = weighing;
        this.min = min;
        this.max = max;
        this.creatureUid = creatureUid;
    }

    public int getWeighing() {
        return weighing;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getMinExtended() {
        return minExtended;
    }

    public int getMaxExtended() {
        return maxExtended;
    }

    public int getCreatureUid() {
        return creatureUid;
    }
}
