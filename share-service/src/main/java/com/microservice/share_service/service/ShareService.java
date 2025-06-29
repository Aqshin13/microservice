package com.microservice.share_service.service;

import com.microservice.share_service.client.UserServiceClient;
import com.microservice.share_service.client.dto.UserResponse;
import com.microservice.share_service.dto.ShareResponse;
import com.microservice.share_service.model.Share;
import com.microservice.share_service.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepository shareRepository;
    private final UserServiceClient userServiceClient;

    public void save(Share share) {
        shareRepository.save(share);
    }

    public void delete(long id){
        shareRepository.deleteAllByAuthenticatedUserId(id);
    }


    public List<ShareResponse> getShares() {
        return shareRepository
                .findAll()
                .stream()
                .map(share -> {
                    UserResponse userResponse = null;
                    try {
                        userResponse = userServiceClient
                                .getUser(share.getAuthenticatedUserId());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                    return ShareResponse.builder()
                            .user(userResponse)
                            .text(share.getText())
                            .build();
                }).toList();
    }

}
