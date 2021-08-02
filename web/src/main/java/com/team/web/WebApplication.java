package com.team.web;

import com.team.test.Test;
import com.team.web.service.ExecuteService;
import com.team.web.service.JaxbService;
import com.team.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication public class WebApplication
        implements CommandLineRunner {

    @Autowired UserService userService; // DEBUG : for test
    @Autowired JaxbService jaxbService; // DEBUG : for test
    @Autowired ExecuteService executeService; // DEBUG : for test

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override public void run(String... args) throws Exception {
        Test test = new Test(userService, jaxbService, executeService);
        test.testFOK();
    }

}
