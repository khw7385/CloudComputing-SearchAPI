package com.example.searchapi.configuration;

import com.example.searchapi.interceptor.TokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${auth.server-url}")
    private String AuthServerURL;

    @Value("${auth.api-url}")
    private String AuthApiURL;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(AuthServerURL, AuthApiURL))
                .order(1)
                .addPathPatterns("/api/search/**");
    }

}
