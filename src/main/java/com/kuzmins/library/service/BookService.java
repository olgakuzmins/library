package com.kuzmins.library.service;

import com.kuzmins.library.models.Book;
import com.kuzmins.library.models.Person;
import com.kuzmins.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> found = bookRepository.findById(id);
        return found.orElse(null);
    }

    public Book getPersonByFullname(String bookName) {
        return bookRepository.getBookByBookName(bookName);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void possessBook(int bookId, Person person) {
        Optional<Book> possessedBook = bookRepository.findById(bookId);
        possessedBook.ifPresent(book -> {
            book.setPerson(person);
            bookRepository.save(book);
        });
    }

    @Transactional
    public void deletePossessor(int bookId) {
        Optional<Book> possessedBook = bookRepository.findById(bookId);
        possessedBook.ifPresent(book -> {
            book.setPerson(null);
            bookRepository.save(book);
        });
    }

    @Transactional(readOnly = true)
    public List<Book> findPossessedBooks(int id) {
        return bookRepository.getBooksByPersonId(id);
    }
}
