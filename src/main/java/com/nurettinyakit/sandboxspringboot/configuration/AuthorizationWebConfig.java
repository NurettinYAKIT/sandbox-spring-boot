package com.nurettinyakit.sandboxspringboot.configuration;

import com.nurettinyakit.sandboxspringboot.configuration.http.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthorizationWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor()).excludePathPatterns(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**");
    }
}
