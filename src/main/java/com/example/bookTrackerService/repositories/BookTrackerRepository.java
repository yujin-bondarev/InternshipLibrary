package com.example.bookTrackerService.repositories;

import com.example.bookTrackerService.model.BookTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTrackerRepository extends JpaRepository<BookTracker, Long> {
    // Методы findById и deleteById уже определены в JpaRepository
}
