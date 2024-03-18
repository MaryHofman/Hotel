package com.example.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.AuthInterceptor;
import com.example.demo.interceptor.CreateInterceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CreateInterceptor createInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(createInterceptor).addPathPatterns("/create","/delete/{hotelId}","/listHotels/User/{userId}","/addComments/{hotelId}");
       registry.addInterceptor(authInterceptor).addPathPatterns("/profile","/calculateRating", "/deleteComments/{id}","/listHotels/addFavourite","/listHotels/Favourite/{userId}");

    }

}