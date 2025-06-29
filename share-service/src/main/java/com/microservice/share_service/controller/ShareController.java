package com.microservice.share_service.controller;

import com.microservice.share_service.dto.ShareResponse;
import com.microservice.share_service.dto.ShareSaveRequest;
import com.microservice.share_service.model.Share;
import com.microservice.share_service.repository.ShareRepository;
import com.microservice.share_service.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ShareSaveRequest share
            , @RequestHeader("X-User-Id") String userId
    ) {

        System.out.println(userId);

        Share share1 = Share.builder()
                .authenticatedUserId(Long.parseLong(userId))
                .text(share.getText())
                .build();
        shareService.save(share1);
        return ResponseEntity.ok().body("Share created successfully");
    }


    @DeleteMapping("/delete/{authId}")
    public ResponseEntity<String> delete(@PathVariable("authId") Long authId) {
        shareService.delete(authId);
        return ResponseEntity.ok().body("Shares deleted successfully");
    }


    @GetMapping
    public ResponseEntity<List<ShareResponse>> getAll() {
        return ResponseEntity.ok(shareService.getShares());
    }

}
