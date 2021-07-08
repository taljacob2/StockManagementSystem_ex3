package com.team.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timestamp.TimeStamp;

@SpringBootApplication public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    // TODO: remove this test controller
    @RestController class GreetingController {

        @RequestMapping("/hello/{name}") String hello(
                @PathVariable String name) {
            return "Hello to you " + name + ", the time is: " +
                    TimeStamp.getTimeStamp();
        }

    }

}
