package com.example.bookstoreapp.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record CreateBookRequestDto(
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
        @NotNull
        BigDecimal price,
        String description,
        String coverImage,
        @NotNull
        List<Long> categoriesIds
) {
}

