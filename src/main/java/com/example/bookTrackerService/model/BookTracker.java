package com.example.bookTrackerService.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_tracer")
@Data
public class BookTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="book_id")
    private Long book_id;

    @Column(name="status")
    private String status;

    @CreationTimestamp
    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @UpdateTimestamp
    @Column(name = "return_by")
    private LocalDateTime return_by;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    public LocalDateTime getReturn_by() {
        return return_by;
    }

    public void setReturn_by(LocalDateTime return_by) {
        this.return_by = return_by;
    }
}
