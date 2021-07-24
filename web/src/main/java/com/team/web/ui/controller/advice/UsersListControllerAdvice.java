package com.team.web.ui.controller.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * This {@code class} represents a <b>global</b> configurator across all {@link
 * org.springframework.stereotype.Controller}s.
 */
@ControllerAdvice public class UsersListControllerAdvice {

    /**
     * Defines global {@link ModelAttribute} for all {@link
     * org.springframework.stereotype.Controller}s.
     *
     * @param model the next <tt>HTML</tt> model.
     */
    @ModelAttribute public void handleRequest(Model model) {

        // Populating "usersList" in the model
        // model.addAttribute("usersList",
        //         Engine.getUsersForced().getCollection());
    }
}
