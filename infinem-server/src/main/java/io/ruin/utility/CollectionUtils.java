package io.ruin.utility;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/28/2024
 */
public class CollectionUtils {

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
}
