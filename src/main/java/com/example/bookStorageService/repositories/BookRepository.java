package com.example.bookStorageService.repositories;

import com.example.bookStorageService.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Методы findById и deleteById уже определены в JpaRepository
}
