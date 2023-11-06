package com.example.bookstoreapp.dto.user;

import com.example.bookstoreapp.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(field = {"password", "repeatPassword"}, message = "Passwords do not match")
public record UserRegistrationRequestDto(
        @NotBlank
        @Size(min = 4, max = 50)
        @Email
        String email,
        @NotBlank
        @Size(max = 50)
        String firstName,
        @NotBlank
        @Size(max = 50)
        String lastName,
        @NotBlank
        @Size(max = 50)
        String shippingAddress,
        @NotBlank
        @Size(min = 4, max = 60)
        String password,
        @NotBlank
        @Size(min = 4, max = 50)
        String repeatPassword
) {
}
