package com.team.web.ui.controller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import timestamp.TimeStamp;

// TODO: this is a test Controller.
@Controller public class HelloController {

    @RequestMapping("/hello/{name}") String hello(@PathVariable String name,
                                                  Model model) {
        model.addAttribute("name", name);
        model.addAttribute("time", TimeStamp.getTimeStamp());
        // return "Hello to you " + name + ", the time is: " +
        //         TimeStamp.getTimeStamp();
        return "hello";
    }

}
