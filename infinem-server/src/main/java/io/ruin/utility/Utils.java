package io.ruin.utility;

import com.google.api.client.util.Strings;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;

import java.security.SecureRandom;
import java.util.ArrayList;
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

    /**
     * Takes a list of items and outputs a list of strings containing string elements representing those items.
     * 1000xCoins -> 1000 coins
     * @param items The items to be translated into strings.
     * @return The list of strings representing the items.
     */
    public static List<String> itemsToStringList(List<Item> items) {
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(
                    item.getAmount() > 1 ? (item.getAmount() + " " + ItemDef.get(item.getId()).name) : ItemDef.get(item.getId()).descriptiveName
            );
        }
        return names;
    }

    /**
     * Returns a comma separated, grammar correct list of items.
     * @param items The items to be converted to strings and joined together.
     * @return The completed string.
     */
    public static String grammarCorrectListForItems(List<Item> items) {
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(
                    item.getAmount() > 1 ? (item.getAmount() + " " + ItemDef.get(item.getId()).name + "s") : ItemDef.get(item.getId()).descriptiveName
            );
        }
        return grammarCorrectList(names);
    }

    /**
     * Returns a comma separated, grammar correct list of item ids.
     * @param items The itemIds to be converted to strings and joined together.
     * @return The completed string.
     */
    public static String grammarCorrectListForItemIds(List<Integer> items) {
        List<String> names = new ArrayList<>();
        for (int itemId : items) {
            names.add(ItemDef.get(itemId).descriptiveName);
        }
        return grammarCorrectList(names);
    }

    /**
     * Creates a comma separated, grammar correct list of strings.
     * @param strings The strings to be joined together.
     * @return The completed string.
     */
    public static String grammarCorrectList(List<String> strings) {
        int count = 0;
        StringBuilder list = new StringBuilder("");
        for (String string : strings) {
            if (count > 0) {
                if (count < strings.size() - 1) {
                    list.append(", ");
                } else {
                    list.append(" and ");
                }
            }
            list.append(string);
            ++count;
        }
        return list.toString();
    }
}
