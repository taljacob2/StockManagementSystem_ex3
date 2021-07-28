package com.team.web.ui.controller.hello;

import com.team.shared.engine.timestamp.TimeStamp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


// TODO: this is a test Controller.

/**
 * AJAX GUIDE: https://www.youtube.com/watch?v=Y_w9KjOrEXk&ab_channel=JavaTechie
 */
@Controller @RequestMapping("hello") public class HelloController {

    // @RequestMapping("{name}") String hello(@PathVariable String name,
    //                                        Model model) {
    //     model.addAttribute("name", name);
    //     model.addAttribute("time", TimeStamp.getTimeStamp());
    //     return "hello";
    // }

    // TODO: checking
    @GetMapping("ye") String ye(Model model) {
        model.addAttribute("time", TimeStamp.getTimeStamp());
        return "hello";
    }

    /**
     * Responding {@code dataType:"text"}.
     *
     * @return {@link TimeStamp#getTimeStamp()}.
     */
    @GetMapping("time") @ResponseBody String time() {
        return TimeStamp.getTimeStamp();
    }

}
