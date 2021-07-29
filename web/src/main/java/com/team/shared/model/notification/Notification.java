package com.team.shared.model.notification;

import com.team.shared.model.notification.type.NotificationType;
import lombok.Data;

import java.io.Serializable;

@Data public class Notification implements Serializable {

    private static final long serialVersionUID = 7922611968289409036L;

    private String type;
    private String title;
    private String content;

    public Notification(NotificationType type, String title, String content) {
        this.type = type.toString().toLowerCase(); // MUST be LowerCase
        this.title = title;
        this.content = content;
    }
}
