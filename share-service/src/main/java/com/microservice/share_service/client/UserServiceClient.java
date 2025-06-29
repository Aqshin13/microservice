package com.microservice.share_service.client;

import com.microservice.share_service.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserServiceClient {


    @GetMapping("/api/v1/users/{authId}")
    UserResponse getUser(@PathVariable Long authId);
}
