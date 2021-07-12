package com.team.web.ui.controller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import timestamp.TimeStamp;

// TODO: this is a test Controller.

/**
 * AJAX GUIDE: https://www.youtube.com/watch?v=Y_w9KjOrEXk&ab_channel=JavaTechie
 */
@Controller @RequestMapping("hello") public class HelloController {

    @RequestMapping("{name}") String hello(@PathVariable String name,
                                           Model model) {
        model.addAttribute("name", name);
        model.addAttribute("time", TimeStamp.getTimeStamp());
        return "hello";
    }

}
