package com.team.web.ui.controller.ajax.notification;

import com.team.web.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j @Controller @RequestMapping("notification")
public class AjaxNotificationController {

    @Autowired NotificationService notificationService;

    @GetMapping @ResponseBody
    public void getNotifications(Model model) {

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("notification", stock);
    }

}
