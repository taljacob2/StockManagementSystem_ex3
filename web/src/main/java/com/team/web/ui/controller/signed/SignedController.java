package com.team.web.ui.controller.signed;

import shared.dto.UserDTO;
import engine.Engine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import user.User;

@Slf4j @Controller @RequestMapping("signed") public class SignedController {

    @GetMapping
    public String returnToSigned(@ModelAttribute("userDTO") UserDTO userDTO) {
        User user = Engine.findUserByNameForced(userDTO.getName());

        String returnString = "redirect:/signed/user";
        if (user.getUserRole().toString().equalsIgnoreCase("ADMIN")) {
            returnString = "redirect:/signed/admin";
        }
        return returnString;
    }

    @Controller @RequestMapping("signed/user")
    public static class SignedUserController {

        @GetMapping public String signed() {
            return "mainweb/signed";
        }

    }

    @Controller @RequestMapping("signed/admin")
    public static class SignedAdminController {

        @GetMapping public String signed() {
            return "mainweb/signed-admin";
        }

    }

}
