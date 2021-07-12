package com.team.web.ui.controller.hello;

import com.team.web.ui.model.response.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * GUIDE : https://www.youtube.com/watch?v=HCgLRKeQnGY
     *
     * @return response of {@code timeStamp}.
     */
    // @RequestMapping(value = "getTime", method = RequestMethod.GET)
    // public @ResponseBody String getTime() throws JSONException {
    //     System.out.println("responding..."); // DE-BUG - checking here
    //
    //     JSONObject resObj = new JSONObject();
    //     resObj.put("time", TimeStamp.getTimeStamp());
    //     return resObj.toString();
    // }
    @GetMapping("getTest") public ResponseEntity<Object> getUser() {
        ServiceResponse<String> response =
                new ServiceResponse<>("success", TimeStamp.getTimeStamp());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    // @RequestMapping(value = "getTime", method = RequestMethod.POST) @ResponseBody
    // public String getTime(@RequestParam("timeStamp") String timeStamp) {
    //
    //
    //     return TimeStamp.getTimeStamp();
    // }

}
