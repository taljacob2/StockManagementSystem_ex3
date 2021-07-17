package com.team.web.ui.controller.ajax;

import engine.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import user.User;

@Controller @RequestMapping("user") public class AjaxUserController {

    /**
     * <i>Finds by name</i> the {@link User} that is <i>name</i> is
     * provided. Returns the {@link User} as a {@code "application /json"}
     * response.
     *
     * @param userName the userName to find the {@link User} by.
     * @param model    the next HTML model.
     * @return produces {@code "application/json"} of {@link User}.
     */
    @GetMapping(value = "{userName}", produces = "application/json")
    public User getUser(@PathVariable String userName, Model model) {

        User user = Engine.findUserByNameForced(userName);
        model.addAttribute("user", user);

        return user;
    }

}


