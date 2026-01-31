package com.microservice.api_gateway.security;


import com.microservice.api_gateway.dto.TokenVerifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class SecurityFilter implements GatewayFilter {

    private final WebClient.Builder webClientBuilder;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }
        String token = authHeader.substring(7);
        return webClientBuilder.build()
                .post()
                .uri("http://auth-service:8081/api/v1/auth/verify?token=" + token)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                        return clientResponse.bodyToMono(TokenVerifyResponse.class).flatMap(response -> {
                            if (!response.isActive()) {
                                return unauthorized(exchange);
                            }
                            ServerHttpRequest modifiedRequest = request.mutate()
                                    .header("X-User-Id", String.valueOf(response.getUserAuthId()))
                                    .build();
                            return chain.filter(exchange.mutate().request(modifiedRequest).build());
                        });
                    } else {
                        return clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            exchange.getResponse().setStatusCode(clientResponse.statusCode());
                            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorBody.getBytes(StandardCharsets.UTF_8));
                            return exchange.getResponse().writeWith(Mono.just(buffer));
                        });
                    }
                });
//                .retrieve()
//                .bodyToMono(TokenVerifyResponse.class)
//                .flatMap(response -> {
//                    if (!response.isActive()) {
//                        System.out.println("No is ac");
//                        return unauthorized(exchange);
//                    }
//                    ServerHttpRequest modifiedRequest = request.mutate()
//                            .header("X-User-Id",
//                                    String.valueOf(response.getAuthUserId()))
//                            .build();
//                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
//                })
//                .onErrorResume(e -> unauthorized(exchange));
    }


    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String message = "{\"error\": \"Unauthorized\", \"message\": \"Token is missing or invalid\"}";
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
//        return exchange.getResponse().setComplete();
    }
}
