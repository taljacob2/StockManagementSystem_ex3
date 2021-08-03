package com.team.test;

import com.team.retention.IgnoreDuringScan;
import com.team.web.service.ExecuteService;
import com.team.web.service.JaxbService;
import com.team.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com.team",
        excludeFilters = @ComponentScan.Filter(IgnoreDuringScan.class))
@SpringBootApplication public class WebApplicationTest
        implements CommandLineRunner {

    @Autowired UserService userService; // TEST
    @Autowired JaxbService jaxbService; // TEST
    @Autowired ExecuteService executeService; // TEST

    public static void main(String[] args) {
        SpringApplication.run(WebApplicationTest.class, args);
    }

    /* FOR TEST PURPOSES */
    @Override public void run(String... args) throws Exception {
        Test test = new Test(userService, jaxbService, executeService);
        test.testLMT();
    }

}
