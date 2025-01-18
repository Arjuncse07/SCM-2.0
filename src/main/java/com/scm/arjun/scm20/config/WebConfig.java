package com.scm.arjun.scm20.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /***
     author: arjun

     This class will be created to handle the content type of the css/js/images file
     Because, the files are enclosed in the specified folder to improve code readability.
     ****/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")            /* classpath: typically refers to the 'src/main/resources' directory */
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());


        resourceHandlerRegistry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }


}
