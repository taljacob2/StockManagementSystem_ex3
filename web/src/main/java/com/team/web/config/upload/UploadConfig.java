package com.team.web.config.upload;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Configures the <i>file upload</i> procedure.
 */
public class UploadConfig {

    /**
     * Configuration for the {@link CommonsMultipartResolver} {@code class}, in
     * order to use the {@link org.springframework.web.multipart.MultipartFile}
     * in {@link com.team.web.ui.controller.upload.UploadController}.
     * <p>
     * Also, configs file upload max-size.
     * </p>
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
