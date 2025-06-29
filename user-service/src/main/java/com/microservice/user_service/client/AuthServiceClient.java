package com.microservice.user_service.client;


import com.microservice.user_service.dto.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {


    @DeleteMapping("/api/v1/auth/delete/{authId}")
    ResponseEntity<ResponseMessage> delete(@PathVariable Long authId);
}
