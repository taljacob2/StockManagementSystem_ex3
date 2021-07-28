package com.team.shared.model.notification;

import com.team.shared.model.notification.type.NotificationType;
import lombok.Data;

import java.io.Serializable;

@Data public class Notification implements Serializable {

    private static final long serialVersionUID = 7922611968289409036L;

    private NotificationType message;
    private String title;
    private String content;

}
