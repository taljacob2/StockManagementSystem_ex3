package com.team.web.service.impl;

import com.team.web.service.JaxbService;
import engine.Engine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import user.User;
import xjc.generated.RizpaStockExchangeDescriptor;
import xjc.generated.RseStock;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;

@Slf4j @Service public class JaxbServiceImpl implements JaxbService {

    @Autowired private Jaxb2Marshaller marshaller;


    @Override public void marshal() {

        // Marshalling:
        RseStock rseStock = new RseStock();
        rseStock.setRseCompanyName("Check_CompanyName");
        rseStock.setRsePrice(120);
        rseStock.setRseSymbol("Check_Symbol");


        // Create a String writer:
        StringWriter writer = new StringWriter();

        // Transfer the marshal result content into the writer.
        marshaller.marshal(rseStock, new StreamResult(writer));

        String xml = writer.toString();
        log.info("XML: {}", xml);
    }

    @Override public void unmarshal(User user, String pathToXMLFile) {
        RizpaStockExchangeDescriptor descriptor =
                (RizpaStockExchangeDescriptor) marshaller
                        .unmarshal(new StreamSource(new File(pathToXMLFile)));

        unmarshalStocks(descriptor);
        unmarshalHoldings(user, descriptor);
    }

    private void unmarshalStocks(RizpaStockExchangeDescriptor descriptor) {
        Engine.addStocksForced(descriptor.getRseStocks());
    }

    private void unmarshalHoldings(User user,
                                   RizpaStockExchangeDescriptor descriptor) {
        Engine.setUserHoldings(user, descriptor.getRseHoldings());
    }
}
