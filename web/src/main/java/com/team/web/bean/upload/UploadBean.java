package com.team.web.bean.upload;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

public class UploadBean {

    @Bean public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
