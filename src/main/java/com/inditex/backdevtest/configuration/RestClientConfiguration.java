package com.inditex.backdevtest.configuration;

import com.inditex.backdevtest.handler.MockErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    /**
     * REST Template BEAN for Mocks service
     * @return bean of RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new MockErrorHandler());
        return restTemplate;
    }

}
