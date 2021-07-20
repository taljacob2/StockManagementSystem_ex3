package com.team.web.ui.controller.signed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("signed") public class SignedController {

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
