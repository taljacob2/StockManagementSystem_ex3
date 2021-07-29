package com.team.web.ui.controller.signed;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.engine.Engine;
import com.team.shared.model.notification.Notification;
import com.team.web.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j @Controller @RequestMapping("signed") public class SignedController {

    @Autowired NotificationService notificationService;

    @GetMapping
    public String returnToSigned(@ModelAttribute("userDTO") UserDTO userDTO) {
        User user = Engine.findUserByNameForced(userDTO.getName());

        String returnString = "redirect:/signed/user";
        if (user.getRole().toString().equalsIgnoreCase("ADMIN")) {
            returnString = "redirect:/signed/admin";
        }
        return returnString;
    }

    @Controller @RequestMapping("signed/user")
    public static class SignedUserController {

        @GetMapping public String signed(
                @ModelAttribute("notification") Notification notification) {
            return "mainweb/signed";
        }

    }

    @Controller @RequestMapping("signed/admin")
    public static class SignedAdminController {

        @GetMapping public String signed(
                @ModelAttribute("notification") Notification notification) {
            return "mainweb/signed-admin";
        }

    }

}
