package com.example.bookstoreapp.repository.category;

import com.example.bookstoreapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByName(String name);

    void deleteById(Long id);
}
