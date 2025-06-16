package com.rathin.pollify.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Allow cross-origin requests to your APIs
                .allowedOrigins("http://localhost:8080")  // Your frontend URL (Tomcat default)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowCredentials(true)  // Allow cookies (like JSESSIONID)
                .allowedHeaders("*")  // Allow all headers
                .maxAge(3600);  // Cache pre-flight requests for 1 hour
    }
}