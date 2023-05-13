package com.inscription.devoir;


import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class DevoirApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DevoirApplication.class, args);
    }



    @Bean
    public RestTemplate restTemplate (RestTemplateBuilder builder){
        return builder.build();
    }

    @Configuration
    @EnableJpaRepositories(basePackages = "com.inscription.devoir.repositories")
    public class AppConfig {
        // Autres configurations de votre application
    }

}
