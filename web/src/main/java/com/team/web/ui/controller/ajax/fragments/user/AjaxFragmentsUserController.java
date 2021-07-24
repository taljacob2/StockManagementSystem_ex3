package com.team.web.ui.controller.ajax.fragments.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Responding a {@code fragment} to present in the system.
 */
@Controller @RequestMapping("ajax/fragments/user")
public class AjaxFragmentsUserController {

    @GetMapping public String getUserNameFragment() {
        return "fragments/user/user-name-fragment :: userNameFragment";
    }

}
