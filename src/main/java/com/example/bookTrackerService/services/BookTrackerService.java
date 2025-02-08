package com.example.bookTrackerService.services;

import com.example.bookTrackerService.model.BookTracker;
import com.example.bookTrackerService.repositories.BookTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class BookTrackerService {

    private static final Logger logger = LoggerFactory.getLogger(BookTrackerService.class);

    @Autowired
    private BookTrackerRepository bookTrackerRepository;

    public BookTracker addBookTracker(BookTracker bookTracker) {
        return bookTrackerRepository.save(bookTracker);
    }

    public List<BookTracker> getAllBookTrackers() {
        return bookTrackerRepository.findAll();
    }

    public Optional<BookTracker> getBookTrackerById(Long id) {
        return bookTrackerRepository.findById(id);
    }

    public void updateBookTracker(Long id, BookTracker bookTrackerDetails) {
        BookTracker bookTracker = bookTrackerRepository.findById(id).orElseThrow(null);
        bookTracker.setStatus(bookTrackerDetails.getStatus());
        bookTracker.setTakenAt(bookTrackerDetails.getTakenAt());
        bookTracker.setReturn_by(bookTrackerDetails.getReturn_by());
        bookTrackerRepository.save(bookTracker);
    }

    // New method for direct deletion
    public void deleteBookTracker(Long bookId) {
        logger.info("Deleting book tracker with ID: {}", bookId);
        bookTrackerRepository.deleteById(bookId);

    }

//Получение запроса от kafka для удаления книги (не работает)
    @KafkaListener(topics = "book-deletion", groupId = "book-tracker")
    public void listenBookDelete(String message) {
        logger.info("Received deletion request message: {}", message);
        try {
            Long bookId = Long.valueOf(message.trim());
            bookTrackerRepository.deleteById(bookId);
            logger.info("Book tracker with ID {} has been deleted", bookId);
        } catch (NumberFormatException e) {
            logger.error("Invalid book ID format in deletion request: {}", message);
        } catch (Exception e) {
            logger.error("Failed to process deletion request: {}", e.getMessage());
        }
    }


    @KafkaListener(topics = "book-creation", groupId = "book-tracker")
    public void listenBookCreation(String message) {
        logger.info("Received message: {}", message); 
        logger.info("Processing message: {}", message);
        String[] parts = message.split(",");
        
        if (parts.length < 2) {
            logger.error("Message format is incorrect. Expected format: 'bookId,status'");
            return;
        }

        try {
            Long bookId = Long.valueOf(parts[0].trim()); 
            String status = parts[1].trim();

            BookTracker bookTracker = new BookTracker();
            bookTracker.setBook_id(bookId);
            bookTracker.setStatus(status);
            addBookTracker(bookTracker);
            logger.info("Book has been created");
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Message format is incorrect: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Failed to process book creation message: {}", e.getMessage());
            logger.error("Message content: {}", message);
        }
    }
}
