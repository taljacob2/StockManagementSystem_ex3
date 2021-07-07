package com.team.web.config.upload;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Configures the <i>file upload</i> procedure.
 */
public class UploadConfig {

    /**
     * Set the file upload max-size.
     *
     * @return the multipartResolver.
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }
}
