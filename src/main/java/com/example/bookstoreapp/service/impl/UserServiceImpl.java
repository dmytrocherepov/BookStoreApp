package com.example.bookstoreapp.service.impl;

import com.example.bookstoreapp.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapp.dto.user.UserResponseDto;
import com.example.bookstoreapp.exception.RegistrationException;
import com.example.bookstoreapp.mapper.UserMapper;
import com.example.bookstoreapp.model.RoleName;
import com.example.bookstoreapp.model.User;
import com.example.bookstoreapp.repository.book.role.RoleRepository;
import com.example.bookstoreapp.repository.book.user.UserRepository;
import com.example.bookstoreapp.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(
            UserRegistrationRequestDto request
    ) throws RegistrationException {
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("Email is already used");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(new HashSet<>(Arrays.asList(
                roleRepository.findRoleByName(RoleName.ROLE_USER)
        )));
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }
}
