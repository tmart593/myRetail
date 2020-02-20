package com.target.myretail.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean("restTemplateService")
    RestTemplate getRestTemplateService(){
        return new RestTemplate();
    }

}
