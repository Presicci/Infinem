package io.ruin.utility;

import java.security.SecureRandom;
import java.util.List;

/**
 * Basic utility class for anything extra we need since the other one is in a separate dependency...
 */
public final class Utils {

    private Utils() {
    }

    public static <T> T randomTypeOfList(List<T> list) {
        if(list == null || list.size() == 0)
            return null;
        return list.get(new SecureRandom().nextInt(list.size()));
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static int largest(int[] arr)
    {
        int i;

        // Initialize maximum element
        int max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];

        return max;
    }

    /**
     * Iterates over a String array to identify if the provided string contains any
     * strings from the array.
     * @param string The string to be parsed for matching strings from the array.
     * @param array The array to be iterated over for strings that may be in the provided String.
     * @return True if a match is found, false if not.
     */
    public static boolean doesStringContainAny(String string, String[] array) {
        for (String element : array) {
            if (string.contains(element))
                return true;
        }
        return false;
    }
}
