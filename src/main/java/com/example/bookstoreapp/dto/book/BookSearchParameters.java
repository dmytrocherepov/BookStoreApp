package com.example.bookstoreapp.dto.book;

public record BookSearchParameters(
        String[] authors,
        String[] titles
) {
}
