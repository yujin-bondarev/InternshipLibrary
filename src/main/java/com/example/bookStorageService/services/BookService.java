package com.example.bookStorageService.services;

import com.example.bookStorageService.model.Book;
import com.example.bookStorageService.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

    private static final String TOPIC_BOOK_CREATION = "book-creation";
    private static final String TOPIC_BOOK_DELETION = "book-deletion";

    public Book addBook(Book book) {
        book.setIsbn(generateUniqueIsbn());
        Book savedBook = bookRepository.save(book);
        try {
            kafkaTemplate.send(TOPIC_BOOK_CREATION, savedBook.getId(), savedBook.getId() + ",available");
            logger.info("Asynchronous request sent for book creation with ID: {}", savedBook.getId());
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka for book creation: {}", e.getMessage());
        }
        return savedBook;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(null);
        book.setTitle(bookDetails.getTitle());
        book.setGenre(bookDetails.getGenre());
        book.setDescription(bookDetails.getDescription());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    //Тут проблема с асинхронным удалением
    public void deleteBook(Long id) {
        // Send a message to Kafka indicating the book ID for deletion
        kafkaTemplate.send(TOPIC_BOOK_DELETION, id, ""); // Sending the book ID as the key
        // Delete the book from the repository
        bookRepository.deleteById(id);
    }

    private String generateUniqueIsbn() {
        String isbn;
        do {
            isbn = generateIsbn();
        } while (isIsbnExists(isbn));
        return isbn;
    }

    private boolean isIsbnExists(String isbn) {
        return bookRepository.findAll().stream().anyMatch(book -> book.getIsbn().equals(isbn));
    }

    private String generateIsbn() {
        Random random = new Random();
        StringBuilder isbn = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            isbn.append(random.nextInt(10)); // Append a random digit
        }
        return isbn.toString();
    }
}
