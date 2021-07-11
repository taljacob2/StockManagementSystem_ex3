package com.team.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timestamp.TimeStamp;

@Slf4j
@SpringBootApplication public class WebApplication{

    @Autowired private Jaxb2Marshaller marshaller;

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
