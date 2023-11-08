package io.ruin.model.content.bestiary.perks;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 2/21/2023
 */
public abstract class BestiaryPerk {

    protected static final int MAX_FILL = 460;

    protected abstract String getLabel(int killCount);
    public abstract double getMultiplier(int killCount);
    protected abstract int getNextBreakpoint(int killCount);
    public abstract int getFill(int killCount);

    public BestiaryPerk() {}

    protected int doubleToPercentage(double value) {
        return (int) ((value - 1f) * 100);
    }

    protected int inverseDoubleToPercentage(double value) {
        return (int) ((1f - value) * 100);
    }

    public String getString(int killCount) {
        return getLabel(killCount) + "|" + getNextBreakpoint(killCount) + "|" + getFill(killCount) + "|";
    }
}
