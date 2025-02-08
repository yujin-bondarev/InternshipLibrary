package com.example.bookTrackerService.services;

import com.example.bookTrackerService.model.BookTracker;
import com.example.bookTrackerService.repositories.BookTrackerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookTrackerServiceTest {

    @InjectMocks
    private BookTrackerService bookTrackerService;

    @Mock
    private BookTrackerRepository bookTrackerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddBookTracker() {
        BookTracker bookTracker = new BookTracker();
        when(bookTrackerRepository.save(any(BookTracker.class))).thenReturn(bookTracker);

        BookTracker createdBookTracker = bookTrackerService.addBookTracker(bookTracker);
        assertNotNull(createdBookTracker);
        verify(bookTrackerRepository, times(1)).save(bookTracker);
    }

    @Test
    public void testGetAllBookTrackers() {
        List<BookTracker> bookTrackers = new ArrayList<>();
        bookTrackers.add(new BookTracker());
        when(bookTrackerRepository.findAll()).thenReturn(bookTrackers);

        List<BookTracker> result = bookTrackerService.getAllBookTrackers();
        assertEquals(1, result.size());
        verify(bookTrackerRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookTrackerById() {
        BookTracker bookTracker = new BookTracker();
        bookTracker.setId(1L);
        when(bookTrackerRepository.findById(1L)).thenReturn(Optional.of(bookTracker));

        Optional<BookTracker> result = bookTrackerService.getBookTrackerById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(bookTrackerRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateBookTracker() {
        BookTracker existingBookTracker = new BookTracker();
        existingBookTracker.setId(1L);
        existingBookTracker.setStatus("Available");

        BookTracker updatedBookTracker = new BookTracker();
        updatedBookTracker.setStatus("Checked Out");

        when(bookTrackerRepository.findById(1L)).thenReturn(Optional.of(existingBookTracker));
        when(bookTrackerRepository.save(any(BookTracker.class))).thenReturn(existingBookTracker);

        bookTrackerService.updateBookTracker(1L, updatedBookTracker);
        assertEquals("Checked Out", existingBookTracker.getStatus());
        verify(bookTrackerRepository, times(1)).save(existingBookTracker);
    }

    @Test
    public void testDeleteBookTrackerById() {
        Long bookId = 1L;
        BookTracker bookTracker = new BookTracker();
        bookTracker.setId(bookId);

        when(bookTrackerRepository.findById(bookId)).thenReturn(Optional.of(bookTracker));
        doNothing().when(bookTrackerRepository).deleteById(bookId);

        bookTrackerService.deleteBookTracker(bookId);

        verify(bookTrackerRepository, times(1)).deleteById(bookId);
    }
}
