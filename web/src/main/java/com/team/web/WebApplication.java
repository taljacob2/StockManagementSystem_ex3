package com.team.web;

import generated.RseStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timestamp.TimeStamp;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@SpringBootApplication public class WebApplication
        implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(WebApplication.class);

    @Autowired private Jaxb2Marshaller marshaller;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override public void run(String... args) throws Exception {

        RseStock rseStock = new RseStock();
        rseStock.setRseCompanyName("Check_CompanyName");
        rseStock.setRsePrice(120);
        rseStock.setRseSymbol("Check_Symbol");

        // Create a writer:
        StringWriter writer = new StringWriter();

        marshaller.marshal(rseStock, new StreamResult(writer));

        String xml = writer.toString();
        log.info("XML: {}", xml);
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
