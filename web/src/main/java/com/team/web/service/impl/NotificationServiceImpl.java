package com.team.web.service.impl;

import com.team.shared.engine.data.user.User;
import com.team.shared.model.notification.Notification;
import com.team.web.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Map;

/**
 * A {@code Service} which serves for handling {@link Notification} <i>shows
 * </i>.
 */
@Service public class NotificationServiceImpl implements NotificationService {

    @Override
    public void addNotification(Notification notification, User user) {
        user.getNotificationsList()
                .addLast(new AbstractMap.SimpleEntry<>(notification, false));
    }

    @Override public void markLastNotification(User user) {
        user.getNotificationsList().getLast().setValue(true);
    }

    @Override public boolean isNeedToShowLastNotification(User user) {
        return !user.getNotificationsList().getLast().getValue();
    }

    @Override public Notification getLastNotificationAndMarkAsShown(User user) {
        Map.Entry<Notification, Boolean> lastEntry =
                user.getNotificationsList().getLast();
        lastEntry.setValue(true);
        return lastEntry.getKey();
    }

}
