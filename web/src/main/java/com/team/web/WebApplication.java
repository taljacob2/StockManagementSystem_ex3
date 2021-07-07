package com.team.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import timestamp.TimeStamp;

@SpringBootApplication public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RestController class GreetingController {

        @RequestMapping("/hello/{name}") String hello(
                @PathVariable String name) {
            return "Hello to you " + name + ", the time is: " +
                    TimeStamp.getTimeStamp();
        }

        /**
         * Redirecting the <tt>"/"</tt> <i>url</i> to <tt> "/signin" </tt>
         * <i>url</i>.
         *
         * @return <tt>html</tt> page of <tt>"/signin"</tt>.
         */
        @GetMapping(value = {"/"}) public ModelAndView redirectToIndexPage() {
            return new ModelAndView("redirect:/signin");
        }
    }

}
