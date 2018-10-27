package com.michal.config;


import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class AppConfiguration {

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpFilter(){
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public BCryptPasswordEncoder endcoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }
}
