package com.revature.yield;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("v1/**")
                        .allowedOrigins(
                                "http://yield-app-p1.s3-website.us-east-2.amazonaws.com",
                                "http://localhost:4200")
                        .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}