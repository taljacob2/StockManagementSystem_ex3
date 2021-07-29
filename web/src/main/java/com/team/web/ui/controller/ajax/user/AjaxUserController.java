package com.team.web.ui.controller.ajax.user;

import com.team.shared.engine.data.user.User;
import com.team.shared.engine.engine.Engine;
import com.team.shared.model.notification.Notification;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j @Controller @RequestMapping("ajax/user")
public class AjaxUserController {

    /**
     * Get {@link User} to {@code Attribute} "user".
     *
     * @param userName
     * @param model
     */
    @SneakyThrows
    @GetMapping(value = "{userName}", produces = "application/json")
    @ResponseBody public User getUser(@PathVariable("userName") String userName,
                                      Model model) {
        User user = Engine.findUserByNameForced(userName);

        // Additionally, set an attribute:
        model.addAttribute("user", user);
        return user;
    }

    /**
     * <i>Finds by name</i> the {@link User} that is <i>name</i> is
     * provided. Returns the {@link User#getRole()} as a {@code "text"}
     * response.
     *
     * @param userName the userName to find the {@link User} by.
     * @return produces {@code "text"} of {@link User#getRole()}.
     */
    @GetMapping("{userName}/role") @ResponseBody public String getUserRole(
            @PathVariable("userName") String userName, Model model) {
        User user = Engine.findUserByNameForced(userName);

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("userRole", user.getRole().toString());

        return user.getRole().toString();
    }

    /**
     * <i>Finds by name</i> the {@link User} that is <i>name</i> is
     * provided. Returns the {@link User}'s {@code lastNotification} as a {@code
     * "application/json"} response.
     *
     * @param userName the userName to find the {@link User} by.
     * @return produces {@code "application/json"} of {@link User}'s {@code
     * lastNotification}.
     */
    @GetMapping(value = "{userName}/lastNotification",
            produces = "application/json") @ResponseBody
    public Optional<Notification> getUserLastNotification(
            @PathVariable("userName") String userName, Model model) {
        User user = Engine.findUserByNameForced(userName);
        Optional<Notification> lastNotificationOptional =
                user.getNotifications().extractLastNotificationAndMarkAsShown();

        // Additionally, set an attribute:
        lastNotificationOptional.ifPresent(notification -> {
            model.addAttribute("lastNotification", notification);
        });

        // DEBUG
        log.warn("lastNotificationOptional {}" ,lastNotificationOptional);

        // Attention: can be 'null' here:
        return lastNotificationOptional;
    }

    @PostMapping(value = "logout", consumes = "text/plain") public @ResponseBody
    void logout(@RequestBody(required = true) String userName) {

        // Remove UserDTO from SignedInUsers List:
        Engine.getSignedInUsers().removeIf(
                userDTO -> userDTO.getName().equalsIgnoreCase(userName));
    }


}


