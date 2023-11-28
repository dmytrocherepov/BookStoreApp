package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.user.UserLoginRequestDto;
import com.example.bookstoreapp.dto.user.UserLoginResponseDto;
import com.example.bookstoreapp.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapp.dto.user.UserResponseDto;
import com.example.bookstoreapp.security.AuthenticationService;
import com.example.bookstoreapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "User authorization/registration",
        description = "Endpoint for user registration and authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(
            summary = "Login to the system",
            description = "Login using email and password, if correct response JWN Token"
    )
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with email , last name , "
                    + "second name , password , repeat password , shipping address"
    )
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request) {
        return userService.register(request);
    }
}
