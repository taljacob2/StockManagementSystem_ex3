package com.team.shared.engine.data.user.notification;

import com.team.shared.engine.data.collection.EngineCollection;
import com.team.shared.model.notification.Notification;

import javax.xml.bind.annotation.*;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
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
@XmlTransient @XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rse-notifications") public class Notifications extends
        EngineCollection<LinkedList<Map.Entry<Notification, Boolean>>, Map.Entry<Notification, Boolean>> {

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * List}.
     */
    public Notifications() {
        setCollection(new LinkedList<>());
    }

    @Override
    public LinkedList<Map.Entry<Notification, Boolean>> getCollection() {
        return super.getCollection();
    }

    @XmlTransient @XmlElement(name = "rse-notification")
    public void setCollection(
            LinkedList<Map.Entry<Notification, Boolean>> collection) {
        super.setCollection(collection);
    }

    /**
     * Adds the {@link Notification} to the {@link java.util.Collection} of
     * <i>pending</i> notifications.
     *
     * @param notification the {@link Notification} to show.
     */
    public void addNotification(Notification notification) {
        getCollection()
                .addLast(new AbstractMap.SimpleEntry<>(notification, false));
    }

    /**
     * Set last {@link Notification} as <i>shown</i>.
     */
    public void markLastNotification() {
        getCollection().getLast().setValue(true);
    }

    /**
     * Indicates if the last {@link Notification} was not shown already.
     */
    public boolean isNeedToShowLastNotification() {
        return !getCollection().getLast().getValue();
    }

    /**
     * Gets the last {@link Notification} and marks it as shown.
     *
     * @return last {@link Notification} in the {@link java.util.Collection}.
     */
    private Notification getLastNotificationAndMarkAsShownUnsecured() {
        Map.Entry<Notification, Boolean> lastEntry = getCollection().getLast();
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
    public Notification getLastNotificationAndMarkAsShown() {
        Notification returnValue = null;
        if (isNeedToShowLastNotification()) {
            returnValue = getLastNotificationAndMarkAsShownUnsecured();
        }
        return returnValue;
    }

}
