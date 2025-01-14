package com.kuzmins.library.dao;

import org.springframework.jdbc.core.RowMapper;
import com.kuzmins.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setBookName(resultSet.getString("bookName"));
        book.setAuthor(resultSet.getString("author"));
        book.setYearOfPublication(resultSet.getInt("yearOfPublication"));
        book.setPersonId(resultSet.getInt("personId"));
        return book;
    }
}
