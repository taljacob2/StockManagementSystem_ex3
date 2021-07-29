package com.team.shared.engine.data.user.notification;

import com.team.shared.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;

/**
 * {@link com.team.shared.model.notification.Notification}s {@code Collection},
 * wrapped in a special class. Has a {@code Collection} field of all the {@link
 * com.team.shared.model.notification.Notification}s together.
 * <p>
 * <p>
 * {@link Collection} of all {@link Notification}s that this {@code User} has
 * received.
 * <ul>
 *     <li> {@code Key} = <i>{@link Notification}</i>.</li>
 *     <li> {@code Value} = <i>{@link Boolean} is shown already</i>.</li>
 * </ul>
 * </p>
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@Slf4j public class Notifications implements Serializable {

    private static final long serialVersionUID = 4863566590333159535L;

    private LinkedList<Map.Entry<Notification, Boolean>> collection =
            new LinkedList<>();

    /**
     * Adds the {@link Notification} to the {@link java.util.Collection} of
     * <i>pending</i> notifications.
     *
     * @param notification the {@link Notification} to show.
     */
    public void addNotification(Notification notification) {
        collection.addLast(new AbstractMap.SimpleEntry<>(notification, false));
    }

    /**
     * Set last {@link Notification} as <i>shown</i>.
     */
    @Deprecated private void markLastNotification() {
        if (collection.size() != 0) {
            Map.Entry<Notification, Boolean> lastEntry = collection.getLast();
            lastEntry.setValue(true);
        }
    }

    /**
     * Indicates if the last {@link Notification} was not shown already.
     * <p>
     * Note: if there is no {@code lastEntry} in the {@link Collection} then,
     * <i>return</i> {@code false} by default;
     * </p>
     *
     * @return isNeedToShowLastNotification
     */
    private boolean isNeedToShowLastNotification() {
        if (collection.size() != 0) {
            Map.Entry<Notification, Boolean> lastEntry = collection.getLast();
            return !lastEntry.getValue();
        } else { return false; }
    }

    /**
     * Gets the last {@link Notification} and marks it as shown.
     *
     * @return last {@link Notification} in the {@link java.util.Collection}.
     */
    private Notification extractLastNotificationAndMarkAsShownUnsecured() {
        if (collection.size() != 0) {
            Map.Entry<Notification, Boolean> lastEntry = collection.getLast();
            lastEntry.setValue(true);
            return lastEntry.getKey();
        } else { return null; }
    }

    /**
     * <blockquote>
     * <b>Warning:</b> there may be more un-showed {@link Notification}s than
     * shown {@link Notification}s, because the {@code Interval-Time} <tt>
     * JavaScript </tt> to show a {@link Notification} is limited by a definite
     * number.
     * </blockquote>
     *
     * @return last {@link Notification} or {@code null}.
     */
    public Optional<Notification> extractLastNotificationAndMarkAsShown() {
        Optional<Notification> returnValue = Optional.empty();
        if (isNeedToShowLastNotification()) {
            returnValue = Optional.ofNullable(
                    extractLastNotificationAndMarkAsShownUnsecured());
        }
        return returnValue;
    }

    public LinkedList<Map.Entry<Notification, Boolean>> getCollection() {
        return collection;
    }

    public void setCollection(
            LinkedList<Map.Entry<Notification, Boolean>> collection) {
        this.collection = collection;
    }
}
