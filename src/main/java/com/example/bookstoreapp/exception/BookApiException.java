package com.example.bookstoreapp.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record BookApiException(
        String message,
        HttpStatus httpStatus,
        LocalDateTime localDateTime
) {
}
