package com.team.web.ui.controller.signed;

import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import user.User;

@Slf4j @Controller @RequestMapping("signed") public class SignedController {

    @GetMapping public String returnToSignedGet(
            @ModelAttribute("userDTO") UserDTO userDTO) {
        User user = Engine.findUserByNameForced(userDTO.getName());

        String returnString = "redirect:/signed/user";
        if (user.getUserRole().toString().equalsIgnoreCase("ADMIN")) {
            returnString = "redirect:/signed/admin";
        }
        return returnString;
    }

    @PostMapping(consumes = "text/plain")
    public String returnToSignedPost(
            @RequestBody String userName) {
        User user = Engine.findUserByNameForced(userName);

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
