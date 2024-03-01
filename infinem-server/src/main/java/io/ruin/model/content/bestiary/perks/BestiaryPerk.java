package io.ruin.model.content.bestiary.perks;

import java.text.DecimalFormat;

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

    protected boolean getInvertedPercentage() {
        return false;
    }

    protected String getLabelString(int killCount) {
        return (getInvertedPercentage() ? inverseDoubleToPercentage(getMultiplier(killCount)) : new DecimalFormat("#").format(getMultiplier(killCount) * 100f)) + getLabel(killCount);
    }

    protected String getBreakpointString(int killCount) {
        if (getNextBreakpoint(killCount) <= 0) return "";
        return getNextBreakpoint(killCount) + "";
    }

    protected String getFillString(int killCount) {
        return getFill(killCount) + "";
    }

    public String getString(int killCount) {
        return getLabelString(killCount) + "|" + getBreakpointString(killCount) + "|" + getFillString(killCount) + "|";
    }
}
