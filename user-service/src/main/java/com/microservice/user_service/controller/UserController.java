package com.microservice.user_service.controller;


import com.microservice.user_service.dto.ResponseMessage;
import com.microservice.user_service.dto.UserCreateRequest;
import com.microservice.user_service.dto.UserResponse;
import com.microservice.user_service.model.User;
import com.microservice.user_service.repository.UserRepository;
import com.microservice.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseMessage createUser(@RequestBody @Valid UserCreateRequest user) {
        userService.save(User.builder()
                .fullName(user.getFullName())
                .authenticatedUserId(user.getAuthId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build());
        return new ResponseMessage("User Created Successfully");
    }


    @DeleteMapping("/delete/{authId}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable("authId") Long authId) {
        userService.deleteById(authId);
        return new ResponseEntity<>(new ResponseMessage("User Deleted Successfully"),
                HttpStatus.OK);
    }


    @GetMapping("/{authId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long authId) {
        User user = userService.findByAuthenticatedUserId(authId);
        System.out.println(user.getFullName());
        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
