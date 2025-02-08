drop database if exists library_db;
create database library_db DEFAULT CHARACTER SET utf8;

use library_db;

drop table if exists books;
drop table if exists book_tracker;

CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    description TEXT,
    author VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS book_tracker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    status ENUM('AVAILABLE', 'TAKEN') DEFAULT 'AVAILABLE',
    taken_at TIMESTAMP NULL,
    return_by TIMESTAMP NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Индексы для ускорения поиска
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_book_tracker_book_id ON book_tracker(book_id);