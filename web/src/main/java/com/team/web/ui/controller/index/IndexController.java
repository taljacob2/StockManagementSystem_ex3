package com.team.web.ui.controller.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController @RequestMapping(value = {"/", "index", "home"})
public class IndexController {

    @GetMapping public ModelAndView index() {

        // Show the 'index' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainweb/home");
        return modelAndView;
    }

}
