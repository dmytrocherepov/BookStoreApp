package com.example.bookstoreapp.repository.book;

import com.example.bookstoreapp.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findBooksByCategoriesId(Long categoryId);
}
