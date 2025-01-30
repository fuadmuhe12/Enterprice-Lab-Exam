package com.kortex.messaging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for your REST endpoints
        registry.addMapping("/api/**")
                .allowedOrigins("*") // or specify "http://localhost:3000", etc.
                .allowedMethods("*");
    }
}
