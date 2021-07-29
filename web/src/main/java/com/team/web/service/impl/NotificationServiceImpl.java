package com.team.web.service.impl;

import com.team.shared.model.notification.Notification;
import com.team.web.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A {@code Service} which serves for handling {@link Notification} <i>shows
 * </i>.
 */
@Service public class NotificationServiceImpl implements NotificationService {

    /**
     * <ul>
     *     <li> {@code Key} = <i>{@link Notification}</i>.</li>
     *     <li> {@code Value} = <i>{@link Boolean} is shown already</i>.</li>
     * </ul>
     */
    LinkedList<Map.Entry<Notification, Boolean>> collection =
            new LinkedList<>();

    @Override public void addNotification(Notification notification) {
        collection.addLast(new AbstractMap.SimpleEntry<>(notification, false));
    }

    @Override public void markLastNotification() {
        collection.getLast().setValue(true);
    }

    @Override public boolean isNeedToShowLastNotification() {
        return !collection.getLast().getValue();
    }

    @Override public Notification getLastNotificationAndMarkAsShown() {
        Map.Entry<Notification, Boolean> lastEntry = collection.getLast();
        lastEntry.setValue(true);
        return lastEntry.getKey();
    }

}
