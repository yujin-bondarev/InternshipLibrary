package com.example.bookTrackerService.controllers;

import com.example.bookTrackerService.model.BookTracker;
import com.example.bookTrackerService.services.BookTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library/book-tracking")
public class BookTrackerControlller {

    @Autowired
    private BookTrackerService bookTrackerService;

    @PostMapping
    public BookTracker addBookTracker(@RequestBody BookTracker bookTracker) {
        return bookTrackerService.addBookTracker(bookTracker);
    }

    @GetMapping("/all")
    public List<BookTracker> getAllBookTrackers() {
        return bookTrackerService.getAllBookTrackers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookTracker> getBookTrackerById(@PathVariable Long id) {
        Optional<BookTracker> bookTracker = bookTrackerService.getBookTrackerById(id);
        return bookTracker.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBookTracker(@PathVariable Long id, @RequestBody BookTracker bookTrackerDetails) {
        bookTrackerService.updateBookTracker(id, bookTrackerDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookTracker(@PathVariable Long id) {
        bookTrackerService.deleteBookTracker(id);
        return ResponseEntity.ok().build();

    }
}
