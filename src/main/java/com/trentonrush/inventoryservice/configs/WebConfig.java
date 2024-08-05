package com.trentonrush.inventoryservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Config
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.data.origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedHeaders(
                        HttpHeaders.ACCEPT,
                        HttpHeaders.ACCEPT_ENCODING,
                        HttpHeaders.ACCEPT_LANGUAGE,
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ORIGIN,
                        HttpHeaders.REFERER,
                        HttpHeaders.USER_AGENT)
                .allowedMethods(
                        HttpMethod.POST.name(),
                        HttpMethod.GET.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name())
                .exposedHeaders(
                        HttpHeaders.AUTHORIZATION
                )
                .allowCredentials(true)
                .maxAge(3600);
    }
}
