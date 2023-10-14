package com.example.bookstoreapp.repository.impl;

import com.example.bookstoreapp.exception.DataProcessingException;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.repository.BookRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        try {
            sessionFactory.inTransaction(s -> s.persist(book));
            return book;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save a book" + book , e);
        }
    }

    @Override
    public List<Book> findAll() {
        try {
          return   sessionFactory.fromSession(
                  s -> s.createQuery("FROM Book" , Book.class ).getResultList()
          );
        } catch (Exception e) {
            throw new DataProcessingException("Can't get books" , e);
        }
    }
}
