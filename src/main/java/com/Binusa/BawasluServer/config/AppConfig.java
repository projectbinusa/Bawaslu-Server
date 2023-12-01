package com.Binusa.BawasluServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// membuat configurasi cors origin mapping controller
@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void  addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://bawaslu.excellentsistem.com")
                .allowedMethods("*")
                .allowedHeaders("X-Requested-With", "Content-Type", "Origin", "Authorization", "Accept", "Client-Security-Token", "Accept-Encoding")
                .maxAge(3600);
    }
}
