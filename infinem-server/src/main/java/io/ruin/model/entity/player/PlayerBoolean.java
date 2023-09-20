package io.ruin.model.entity.player;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 9/20/2023
 */
public enum PlayerBoolean {
    CERBERUS_METAMORPH,
    JAD_METAMORPH,
    SIRE_METAMORPH;

    /**
     * Checks the boolean value for given player.
     * @param player The player being checked.
     * @return True if boolean is stored, false if not.
     */
    public boolean has(Player player) {
        return player.hasAttribute(this.name());
    }

    /**
     * Toggles the boolean value for the given player.
     * @param player Player to toggle to boolean for.
     * @return True if new value is true, false if false.
     */
    public boolean toggle(Player player) {
        if (has(player)) {
            player.removeAttribute(this.name());
            return false;
        } else {
            player.putAttribute(this.name(), 0);
            return true;
        }
    }

    /**
     * Sets the boolean's value to true for the given player.
     * @param player The player to set the boolean true for.
     * @return True if boolean was false, false if boolean was true.
     */
    public boolean setTrue(Player player) {
        if (has(player))
            return false;
        player.putAttribute(this.name(), 0);
        return true;
    }

    /**
     * Sets the boolean's value to false for the given player.
     * @param player The player to set the boolean false for.
     * @return True if boolean was true, false if boolean was false.
     */
    public boolean setFalse(Player player) {
        if (!has(player))
            return false;
        player.removeAttribute(this.name());
        return true;
    }
}
