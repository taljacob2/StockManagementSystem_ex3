package com.team.web;

import engine.Engine;
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
import xjc.generated.RizpaStockExchangeDescriptor;

import javax.xml.transform.stream.StreamSource;
import java.io.File;

@SpringBootApplication public class WebApplication
        implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(WebApplication.class);

    @Autowired private Jaxb2Marshaller marshaller;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override public void run(String... args) throws Exception {

        // Marshalling:
        // RseStock rseStock = new RseStock();
        // rseStock.setRseCompanyName("Check_CompanyName");
        // rseStock.setRsePrice(120);
        // rseStock.setRseSymbol("Check_Symbol");


        // Create a String writer:
        // StringWriter writer = new StringWriter();
        //
        // // Transfer the marshal result content into the writer.
        // marshaller.marshal(rseStock, new StreamResult(writer));
        //
        // String xml = writer.toString();
        // log.info("XML: {}", xml);

        // Unmarshalling:
        // RseStock rseStock1 =
        //         (RseStock) marshaller.unmarshal(new StreamSource(new StringReader(xml)));

        System.out.println("exists = " + new File(
                "C:/Users/Tal/C_Code/java/rolling_ex_3/XMLresources/heaver" +
                        "-user.xml").exists()); // DE-BUG

        RizpaStockExchangeDescriptor descriptor =
                (RizpaStockExchangeDescriptor) marshaller.unmarshal(
                        new StreamSource(new File(
                                "C:/Users/Tal/C_Code/java/rolling_ex_3/XMLresources/heaver-user.xml")));

        // Extracting Descriptor:
        Engine.setStocks(descriptor.getRseStocks());
        System.out.println(Engine.getStocks()); // de-BUG - test print stocks

        log.info("descriptor: {}", descriptor);

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
