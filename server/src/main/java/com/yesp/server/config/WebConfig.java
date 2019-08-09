package com.yesp.server.config;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath/static/**").resourceChain(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //  通过“/"  可以直接访问到index.html   是resources 下的static目录下的
        registry.addViewController("/").setViewName("forward:/index.html");
    }

}
