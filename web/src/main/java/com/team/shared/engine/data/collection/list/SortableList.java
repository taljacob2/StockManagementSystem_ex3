package com.team.shared.engine.data.collection.list;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>This <i>interface</i> {@code extends} the known {@link List}
 * <i>interface</i>, with the addition of automatic sort of its {@code
 * Elements}.
 * </p>
 *
 * @param <E> the Type of {@code Element} in the {@link List}.
 * @version 1.2
 */
public interface SortableList<E extends Comparable<? super E>> extends List<E> {

    default boolean sortedAddAll(Collection<? extends E> c) {
        boolean result = addAll(c);
        Collections.sort(this); // sort the list
        return result;
    }

    default boolean sortedAdd(E e) {
        boolean result = add(e);
        Collections.sort(this); // sort the list
        return result;
    }

}
