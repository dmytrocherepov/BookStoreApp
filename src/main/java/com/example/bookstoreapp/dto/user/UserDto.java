package com.example.bookstoreapp.dto.user;

import com.example.bookstoreapp.model.Role;
import java.util.Set;

public record UserDto(
        Long id,
        String email,
        String password,
        String firstName,
        String lastName,
        String shippingAddress,
        Set<Role> roles
) {
}
