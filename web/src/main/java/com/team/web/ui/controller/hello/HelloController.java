package com.team.web.ui.controller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import timestamp.TimeStamp;

// TODO: this is a test Controller.
@Controller @RequestMapping("hello") public class HelloController {

    @RequestMapping("{name}") String hello(@PathVariable String name,
                                           Model model) {
        model.addAttribute("name", name);
        model.addAttribute("time", TimeStamp.getTimeStamp());
        // return "Hello to you " + name + ", the time is: " +
        //         TimeStamp.getTimeStamp();
        return "hello";
    }


    //
    // @GetMapping("getTest") public ResponseEntity<Object> getUser() {
    //     ServiceResponse<String> response =
    //             new ServiceResponse<>("success", TimeStamp.getTimeStamp());
    //     return new ResponseEntity<Object>(response, HttpStatus.OK);
    // }


    // @RequestMapping(value = "getTime", method = RequestMethod.POST) @ResponseBody
    // public String getTime(@RequestParam("timeStamp") String timeStamp) {
    //
    //
    //     return TimeStamp.getTimeStamp();
    // }

}
