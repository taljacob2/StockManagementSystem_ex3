package com.team.web.service;

import com.team.shared.model.notification.Notification;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link Notification}s.
 *
 * @see com.team.web.service.impl.NotificationServiceImpl
 */
public interface NotificationService {

    /**
     * Adds the {@link Notification} to the {@link java.util.Collection} of
     * <i>pending</i> notifications.
     *
     * @param notification the {@link Notification} to show.
     */
    void addNotification(Notification notification);

    /**
     * Set last {@link Notification} as <i>shown</i>.
     */
    void markLastNotification();

    /**
     * Indicates if the last {@link Notification} was not shown already.
     */
    boolean isNeedToShowLastNotification();

    /**
     * Gets the last {@link Notification} and marks it as shown.
     *
     * @return last {@link Notification} in the {@link java.util.Collection}.
     */
    Notification getLastNotificationAndMarkAsShown();

}
