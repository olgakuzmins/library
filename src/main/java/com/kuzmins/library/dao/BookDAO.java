package com.kuzmins.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.kuzmins.library.models.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Optional<Book> getBook(String bookName) {
        return jdbcTemplate.query("SELECT * FROM book WHERE bookname=?",
                new Object[]{bookName}, new BookMapper()).stream().findAny();
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(bookname, author, yearofpublication) VALUES ( ?, ?, ?)",
                book.getBookName(), book.getAuthor(), book.getYearOfPublication());
    }

    public void updateBook(int id, Book newBook) {
        jdbcTemplate.update("UPDATE book SET bookname=?, author=?, yearofpublication=? WHERE id=?",
                newBook.getBookName(), newBook.getAuthor(), newBook.getYearOfPublication(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void possessBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE book SET personId=? WHERE id=?", personId, bookId);
    }

    public void deletePossessor(int bookId) {
        jdbcTemplate.update("UPDATE book SET personid=null WHERE id=?", bookId);
    }

    public List<Book> findPossessedBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE personid=?", new Object[]{id}, new BookMapper());
    }

}

