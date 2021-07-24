package com.team.web.ui.controller.ajax.fragments.user.list;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Responding a {@code fragment} to present in the system.
 */
@Controller @RequestMapping("ajax/fragments/usersList")
public class AjaxFragmentsUsersListsController {

    @GetMapping public String getUserNameFragment() {
        return "fragments/users/users-list-fragment :: usersListFragment";
    }

}
