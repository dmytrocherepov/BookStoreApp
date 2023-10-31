package com.example.bookstoreapp.dto.user;

import com.example.bookstoreapp.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(message = "Password don`t match",
        field = "password",
        fieldMatch = "repeatPassword")
public record UserRegistrationRequestDto(
        @NotBlank
        @Size(min = 4, max = 50)
        String email,
        String firstName,
        String lastName,
        String shippingAddress,
        @NotBlank
        @Size(min = 4, max = 50)
        String password,
        @NotBlank
        @Size(min = 4, max = 50)
        String repeatPassword
) {

}
