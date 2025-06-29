package com.microservice.user_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "share-service")
public interface ShareServiceClient {

    @DeleteMapping("/api/v1/shares/delete/{authId}")
    public ResponseEntity<String> delete(@PathVariable("authId") Long authId) ;
}
