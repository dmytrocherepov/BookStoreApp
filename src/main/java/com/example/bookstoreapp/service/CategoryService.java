package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.category.CategoryRequestDto;
import com.example.bookstoreapp.dto.category.CategoryResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);
}
