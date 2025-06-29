package com.microservice.user_service.service;

import com.microservice.user_service.client.AuthServiceClient;
import com.microservice.user_service.client.ShareServiceClient;
import com.microservice.user_service.exception.CommonException;
import com.microservice.user_service.model.User;
import com.microservice.user_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthServiceClient authServiceClient;
    private final ShareServiceClient shareServiceClient;
    public void save(User user) {
        userRepository.save(user);
    }


    @Transactional(rollbackOn = CommonException.class)
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findByAuthenticatedUserId(id);
        if (user.isEmpty()) {
            throw new CommonException(404, "User not found");
        }
        userRepository.delete(user.get());
        try {
            authServiceClient.delete(id);
            shareServiceClient.delete(id);
        }catch (Exception e){
            throw new CommonException(503, "Feign Error");
        }
    }



    public User findByAuthenticatedUserId(Long id){
        return userRepository.findByAuthenticatedUserId(id).orElseThrow(
                () -> new CommonException(404, "User not found")
        );
    }

}
