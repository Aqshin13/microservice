package com.microservice.api_gateway.routing;


import com.microservice.api_gateway.security.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Router {

    private final SecurityFilter securityFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/v1/auth/**")
                        .uri("lb://auth-service"))
                .route(p -> p.path("/api/v1/users/**")
                        .filters(f->f.filter(securityFilter))
                        .uri("lb://user-service"))
                .route(p -> p.path("/api/v1/shares/**")
                        .filters(f->f.filter(securityFilter))
                        .uri("lb://share-service"))
                .build();
    }
}
