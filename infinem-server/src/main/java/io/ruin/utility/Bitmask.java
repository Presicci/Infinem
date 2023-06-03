package io.ruin.utility;

import com.google.gson.annotations.Expose;

/**
 * 32 bit bitmask storage and operations.
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/11/2022
 */
public class Bitmask {
    @Expose private int mask;

    /**
     * Adds a bit to the mask.
     * @param bit Bit to add, cannot exceed 30.
     * @return False if bit is already set, True if success
     */
    public boolean add(int bit) {
        if (bit > 30)
            return false;
        if (checkFlag(bit))
            return false;
        mask = mask & bit;
        return true;
    }

    /**
     * Remove a bit from the mask.
     * @param bit Bit to remove, cannot exceed 30.
     * @return False if bit is not set, True if success
     */
    public boolean remove(int bit) {
        if (bit > 30)
            return false;
        if (!checkFlag(bit))
            return false;
        mask = mask | bit;
        return true;
    }

    /**
     * Checks if a bit is set.
     * @param bit Bit the check, cannot exceed 30.
     * @return True if bit is set, False if not.
     */
    public boolean checkFlag(int bit) {
        if (bit > 30)
            return false;
        return (mask & bit) == bit;
    }
}
