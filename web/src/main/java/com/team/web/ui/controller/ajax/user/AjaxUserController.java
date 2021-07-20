package com.team.web.ui.controller.ajax.user;

import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import user.User;

@Slf4j @Controller @RequestMapping("ajax/user") public class AjaxUserController {

    /**
     * <i>Finds by name</i> the {@link User} that is <i>name</i> is
     * provided. Returns the {@link User#getUserRole()} as a {@code "text"}
     * response.
     *
     * @param userName the userName to find the {@link User} by.
     * @return produces {@code "text"} of {@link User#getUserRole()}.
     */
    @GetMapping("{userName}/role") @ResponseBody
    public String getUserRole(@PathVariable("userName") String userName,
                              Model model) {
        User user = Engine.findUserByNameForced(userName);

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("userRole", user.getUserRole().toString());

        return user.getUserRole().toString();
    }

    /**
     * Get {@link User} to {@code Attribute} "user".
     *
     * @param userName
     * @param model
     */
    @SneakyThrows @GetMapping("{userName}") @ResponseBody
    public void getUser (@PathVariable("userName") String userName,
                        Model model) {
        User user = Engine.findUserByNameForced(userName);

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("user", user);

        log.info("userName {}", userName);
        log.info("user {}", user);
        log.info("users {}", Engine.getUsersForced());
    }


    @PostMapping(value = "logout", consumes = "text/plain") public @ResponseBody
    void logout(@RequestBody(required = true) String userName) {

        // Remove UserDTO from SignedInUsers List:
        Engine.getSignedInUsers().removeIf(
                userDTO -> userDTO.getName().equalsIgnoreCase(userName));
    }


}


