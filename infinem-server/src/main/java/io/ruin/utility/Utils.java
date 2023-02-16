package io.ruin.utility;

import com.google.api.client.util.Strings;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.item.Item;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.NonNull;
import lombok.val;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

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

    @NonNull
    public static <E> E getRandomCollectionElement(final Collection<E> e) {
        val size = e.size();
        if (size == 0) {
            throw new RuntimeException("Collection cannot be empty.");
        }
        val random = Random.get(e.size() - 1);
        int i = 0;
        for (final E value : e) {
            if (i == random) {
                return value;
            }
            i++;
        }
        throw new RuntimeException("Concurrent modification performed on the collection.");
    }

    public static <T, V> Map<V, T> populateMap(final T[] array, final Map<V, T> map, final Function<T, V> func) {
        for (val t : array) {
            map.put(func.apply(t), t);
        }
        return map;
    }

    /**
     * Finds the first value in the array that matches the predicate. If none is found, returns null.
     *
     * @param array     the array to loop.
     * @param predicate the predicate to test against each value.
     * @return a matching value.
     */
    public static <T> T findMatching(final T[] array, final Predicate<T> predicate) {
        return findMatching(array, predicate, null);
    }

    /**
     * Finds the first value in the array that matches the predicate. If none is found, returns the default value.
     *
     * @param array        the array to loop.
     * @param predicate    the predicate to test against each value.
     * @param defaultValue the default value to return if no value matches the predicate.
     * @return a matching value.
     */
    public static <T> T findMatching(final T[] array, final Predicate<T> predicate, final T defaultValue) {
        for (int i = 0; i < array.length; i++) {
            val object = array[i];
            if (predicate.test(object)) {
                return object;
            }
        }
        return defaultValue;
    }

    public static <T> T findMatching(final Collection<T> list, final Predicate<T> predicate) {
        return findMatching(list, predicate, null);
    }

    public static <T> T findMatching(final Collection<T> list, final Predicate<T> predicate, final T defaultValue) {
        for (final T object : list) {
            if (predicate.test(object)) {
                return object;
            }
        }
        return defaultValue;
    }

    public static <T> T findMatching(final Set<T> list, final Predicate<T> predicate) {
        return findMatching(list, predicate, null);
    }

    public static <T> T findMatching(final Set<T> list, final Predicate<T> predicate, final T defaultValue) {
        for (val object : list) {
            if (predicate.test(object)) {
                return object;
            }
        }
        return defaultValue;
    }
}
