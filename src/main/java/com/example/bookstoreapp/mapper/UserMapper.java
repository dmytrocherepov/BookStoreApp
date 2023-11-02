package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.config.MapperConfig;
import com.example.bookstoreapp.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapp.dto.user.UserResponseDto;
import com.example.bookstoreapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toUser(UserRegistrationRequestDto user);

    UserResponseDto toUserResponseDto(User user);
}
