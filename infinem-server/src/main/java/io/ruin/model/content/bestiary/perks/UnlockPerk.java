package io.ruin.model.content.bestiary.perks;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 6/11/2024
 */
public abstract class UnlockPerk extends BestiaryPerk {

    protected abstract int getRequiredKills();

    @Override
    protected int getNextBreakpoint(int killCount) {
        return killCount > getRequiredKills() ? 0 : getRequiredKills();
    }

    @Override
    protected String getBreakpointString(int killCount) {
        if (killCount > getRequiredKills()) return "";
        return "Unlocked at " + getRequiredKills();
    }

    public double getMultiplier(int killCount) {
        return killCount > getRequiredKills() ? 1.0 : 0;
    }

    public int getFill(int killCount) {
        if (killCount > getRequiredKills()) return MAX_FILL;
        return (int) (MAX_FILL * ((float) (killCount) / (float) (getRequiredKills())));
    }

    @Override
    public boolean hasUnlocked(int killCount) {
        return killCount > getRequiredKills();
    }
}