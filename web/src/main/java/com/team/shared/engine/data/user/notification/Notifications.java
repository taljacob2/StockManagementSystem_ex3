package com.team.shared.engine.data.user.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.shared.model.notification.Notification;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * {@link com.team.shared.model.notification.Notification}s {@code Collection},
 * wrapped in a special class. Has a {@code Collection} field of all the {@link
 * com.team.shared.model.notification.Notification}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
public class Notifications implements Serializable {

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
    public void markLastNotification() {
        collection.getLast().setValue(true);
    }

    /**
     * Indicates if the last {@link Notification} was not shown already.
     */
    @JsonIgnore public boolean isNeedToShowLastNotification() {
        return !collection.getLast().getValue();
    }

    /**
     * Gets the last {@link Notification} and marks it as shown.
     *
     * @return last {@link Notification} in the {@link java.util.Collection}.
     */
    private Notification extractLastNotificationAndMarkAsShownUnsecured() {
        Map.Entry<Notification, Boolean> lastEntry = collection.getLast();
        lastEntry.setValue(true);
        return lastEntry.getKey();
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
    public Notification extractLastNotificationAndMarkAsShown() {
        Notification returnValue = null;
        if (isNeedToShowLastNotification()) {
            returnValue = extractLastNotificationAndMarkAsShownUnsecured();
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
