package com.lehrerkalender.lehrerkalender.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = "com.lehrerkalender")
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");

        //https://spring.io/guides/gs/securing-web/
    }

    /* wenn EnableWebMvc benötigt wird:
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**).addResourceLocations("classpath:/static/js/");
        etc.
    }
     */

}
