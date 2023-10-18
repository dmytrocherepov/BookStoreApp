package com.example.bookstoreapp.repository.book;

import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.repository.SpecificationProvider;
import com.example.bookstoreapp.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find correct identification provider with key" + key)
                );
    }
}
