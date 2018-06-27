package com.child.common.config;

import com.child.common.interceptor.AppSecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigureration extends WebMvcConfigurerAdapter {

    @Value("${web.upload-path}")
    private String filePath;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AppSecurityInterceptor())
                .addPathPatterns("/hrt/**");
        super.addInterceptors(registry);
    }

}
