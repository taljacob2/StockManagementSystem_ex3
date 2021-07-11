package com.team.web.service.impl;

import com.team.web.service.JaxbService;
import engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import xjc.generated.RizpaStockExchangeDescriptor;

import javax.xml.transform.stream.StreamSource;
import java.io.File;

@Service public class JaxbServiceImpl implements JaxbService {

    @Autowired private Jaxb2Marshaller marshaller;

    @Override public void marshal() {
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
    }

    @Override public void unmarshal(String pathToXMLFile) {
        RizpaStockExchangeDescriptor descriptor =
                (RizpaStockExchangeDescriptor) marshaller
                        .unmarshal(new StreamSource(new File(pathToXMLFile)));

        unmarshalStocks(descriptor);
    }

    private void unmarshalStocks(RizpaStockExchangeDescriptor descriptor) {
        Engine.setStocks(descriptor.getRseStocks());
    }

    private void unmarshalHoldings(RizpaStockExchangeDescriptor descriptor) {
        // TODO: need to implement
        // Engine.setStocks(descriptor.getRseStocks());
    }
}
