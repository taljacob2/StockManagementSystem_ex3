package com.team.web.config.jaxb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

/**
 * GUIDE FOR JAXB2: https://www.youtube.com/watch?v=0D-P2LzLJYQ&ab_channel=PhilipStarritt
 */
@Configuration public class JaxbConfig {

    @Bean public Jaxb2Marshaller createJaxb2Marshaller(
            @Value("${context.path}") final String contextPath,
            @Value("${schema.location}") final Resource schemaResource) {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(contextPath);
        marshaller.setSchema(schemaResource);

        Map<String, Object> properties = new HashMap<>();
        properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.setMarshallerProperties(properties);
        return marshaller;
    }
}
