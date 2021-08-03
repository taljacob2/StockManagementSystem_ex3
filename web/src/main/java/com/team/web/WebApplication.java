package com.team.web;

import com.team.retention.IgnoreDuringScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@IgnoreDuringScan @SpringBootApplication public class WebApplication{

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
