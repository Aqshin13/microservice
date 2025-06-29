package com.microservice.auth_service.client;


import com.microservice.auth_service.client.dto.UserClientDto;
import com.microservice.auth_service.dto.response.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/v1/users/create")
    public ResponseMessage createUser(@RequestBody UserClientDto user);
}
