package com.team.web.ui.controller.error;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller public class ErrorControllerImpl implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH) public ModelAndView error() {

        // Redirect to 'error 404':
        ModelAndView modelAndView = new ModelAndView();
        // modelAndView.setViewName("redirect:/error404");
        modelAndView.setViewName("mainweb/error");
        return modelAndView;
    }

    @GetMapping("error404") public ModelAndView error404() {

        // Show the 'error 404' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainweb/error");
        return modelAndView;
    }


    @Override public String getErrorPath() {
        return PATH;
    }
}