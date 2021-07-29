package com.team.web.service.impl;

import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.xjc.generated.RizpaStockExchangeDescriptor;
import com.team.shared.engine.engine.Engine;
import com.team.shared.engine.load.Descriptor;
import com.team.shared.engine.load.GenerateSchema;
import com.team.shared.engine.message.Message;
import com.team.shared.engine.message.print.MessagePrint;
import com.team.web.service.JaxbService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j @Service public class JaxbServiceImpl implements JaxbService {

    @Autowired private Jaxb2Marshaller marshaller;

    @SneakyThrows @Override public void marshal(String stringPathOfXML) {

        // Marshalling:
        // RseStock rseStock = new RseStock();
        // rseStock.setRseCompanyName("Check_CompanyName");
        // rseStock.setRsePrice(120);
        // rseStock.setRseSymbol("Check_Symbol");


        JAXBContext jaxbContext = JAXBContext.newInstance(Descriptor.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // set in format
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // generate the Schema '.xsd' file:
        Path pathOfXML = Paths.get(stringPathOfXML);
        GenerateSchema.generate(pathOfXML);

        // set Schema '.xsd' file:
        jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
                GenerateSchema.file.getName());

        // Marshal in file
        jaxbMarshaller
                .marshal(Engine.createDescriptor(), new File(stringPathOfXML));

        // print Success message:
        MessagePrint.println(MessagePrint.Stream.OUT,
                Message.Out.XML.Save.success(stringPathOfXML));

        log.info("users {}", Engine.getUsers()); // DEBUG


        // // Create a String writer:
        // StringWriter writer = new StringWriter();
        //
        // // Transfer the marshal result content into the writer.
        // marshaller.marshal(Engine.createDescriptor(), new StreamResult(writer));
        //
        // String xml = writer.toString();
        // log.info("XML: {}", xml);
    }

    @Override public void unmarshal(User user, String pathToXMLFile) {
        Engine.backup();

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
