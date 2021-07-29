package com.team.web.ui.controller.advice;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.engine.Engine;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This {@code class} represents a <b>global</b> configurator across all {@link
 * org.springframework.stereotype.Controller}s.
 */
@ControllerAdvice public class SignedInUsersListControllerAdvice {

    /**
     * Defines global {@link ModelAttribute} for all {@link
     * org.springframework.stereotype.Controller}s.
     *
     * @param model the next <tt>HTML</tt> model.
     */
    @ModelAttribute public void handleRequest(Model model) {

        // Populating "signedInUsersList" in the model
        model.addAttribute("signedInUsersList", Engine.getSignedInUsers());

        // Populating "signedInUsersNameList" in the model
        List<String> signedInUsersNameList =
                Engine.getSignedInUsers().stream().map(UserDTO::getName)
                        .collect(Collectors.toList());
        model.addAttribute("signedInUsersNameList", signedInUsersNameList);
    }
}
