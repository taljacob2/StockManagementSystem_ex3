package com.team.web.ui.controller.ajax.user;

import engine.Engine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import user.User;

@Slf4j @Controller @RequestMapping("user") public class AjaxUserController {

    /**
     * <i>Finds by name</i> the {@link User} that is <i>name</i> is
     * provided. Returns the {@link User#getUserRole()} as a {@code "text"}
     * response.
     *
     * @param userName the userName to find the {@link User} by.
     * @return produces {@code "text"} of {@link User#getUserRole()}.
     */
    @GetMapping(value = "{userName}/role") @ResponseBody
    public String getUserRole(@PathVariable("userName") String userName,
                              Model model) {
        User user = Engine.findUserByNameForced(userName);
        // model.addAttribute("user", user);
        return user.getUserRole().toString();
    }

}


