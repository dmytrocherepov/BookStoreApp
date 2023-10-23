package com.example.bookstoreapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.Length;

public record CreateBookRequestDto(
        Long id,
        @NotBlank @Length(max = 50)
        String title,
        @NotBlank @Length(max = 50)
        String author,
        @NotBlank
        @Pattern(regexp = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+"
                + "[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+"
                + "[- ]?[0-9X]$",
        message = "length must be 10 digits")
        String isbn,
        @PositiveOrZero
        BigDecimal price,
        String description,
        String coverImage
) {
}

