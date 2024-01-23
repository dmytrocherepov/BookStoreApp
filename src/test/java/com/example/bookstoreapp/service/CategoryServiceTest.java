package com.example.bookstoreapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstoreapp.dto.category.CategoryRequestDto;
import com.example.bookstoreapp.dto.category.CategoryResponseDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.CategoryMapper;
import com.example.bookstoreapp.mapper.impl.CategoryMapperImpl;
import com.example.bookstoreapp.model.Category;
import com.example.bookstoreapp.repository.category.CategoryRepository;
import com.example.bookstoreapp.service.impl.CategoryServiceImpl;
import java.util.Optional;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @Description("Saves a category")
    void save_validCategoryRequestDto_returnsValidCategoryDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test",
                "test"
        );
        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());

        when(categoryRepository.save(any())).thenReturn(category);

        CategoryResponseDto dto = categoryService.save(requestDto);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("description", "test");
    }

    @Test
    @Description("Get a category with existing id")
    void getById_validId_ShouldReturnDto() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("test");
        category.setDescription("test");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryResponseDto dto = categoryService.getById(id);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("description", "test");

    }

    @Test
    @Description("Get a category with non existing id")
    void getById_invalidId_ShouldThrowException() {
        Long id = -1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No such category");
    }

    @Test
    @Description("Update a category with existing id and valid RequestDto")
    void update_validRequestDto_ShouldReturnDto() {
        Long id = 1L;
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test",
                "test"
        );
        Category category = new Category();
        category.setDescription(requestDto.description());
        category.setName(requestDto.name());
        category.setId(id);

        when(categoryRepository.save(any())).thenReturn(category);

        CategoryResponseDto dto = categoryService.update(id, requestDto);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @Description("Delete a category with existing id")
    void deleteById_validId_ShouldDoNothing() {
        Long id = 1L;

        doNothing().when(categoryRepository).deleteById(id);
        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }
}
