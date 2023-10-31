package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapp.dto.user.UserResponseDto;
import com.example.bookstoreapp.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
