package com.kuzmins.library.repositories;

import com.kuzmins.library.models.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book getBookByBookName(@NotEmpty(message = "Name should not be empty") @Size(min = 1, max = 50, message = "name should be from 1 to 50") String bookName);

    List<Book> getBooksByPersonId(int id);

}
