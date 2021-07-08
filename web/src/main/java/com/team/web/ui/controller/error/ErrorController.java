package com.team.web.ui.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller ("/error") public class ErrorController {

    public ModelAndView error() {

        // Show the 'error 404' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainweb/Page-3");
        return modelAndView;
    }

}
