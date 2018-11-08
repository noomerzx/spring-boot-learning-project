package com.project.homework.configs;

import java.util.ArrayList;
import java.util.List;

import com.project.homework.interceptors.AuthInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BaseConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
      List<String> excludePaths = new ArrayList<>();
      // excludePaths.add("/users");
      excludePaths.add("/error");
      excludePaths.add("/error/*");
      // excludePaths.add("/users/login");
      // excludePaths.add("/users/logout");
      registry.addInterceptor(authInterceptor).addPathPatterns("/**/*").excludePathPatterns(excludePaths);
    }
}