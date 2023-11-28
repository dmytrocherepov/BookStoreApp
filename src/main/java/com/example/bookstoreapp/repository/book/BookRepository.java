package com.example.bookstoreapp.repository.book;

import com.example.bookstoreapp.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = "categories")
    List<Book> findBooksByCategoriesId(Long categoryId);

    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);
}
