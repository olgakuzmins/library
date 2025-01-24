package com.kuzmins.library.dao;

import com.kuzmins.library.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import com.kuzmins.library.models.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //works
    @Transactional(readOnly = true)
    public List<Book> allBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();

        //return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBook(String bookName) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Book.class, bookName));
//        return jdbcTemplate.query("SELECT * FROM book WHERE bookname=?",
//                new Object[]{bookName}, new BookMapper()).stream().findAny();
    }


    @Transactional(readOnly = true)
    public Book getBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
//        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BookMapper())
//                .stream().findAny().orElse(null);
    }

    //works
    @Transactional
    public void saveBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
//        jdbcTemplate.update("INSERT INTO book(bookname, author, yearofpublication) VALUES ( ?, ?, ?)",
//                book.getBookName(), book.getAuthor(), book.getYearOfPublication());
    }

    @Transactional
    public void updateBook(int id, Book newBook) {
        Session session = sessionFactory.getCurrentSession();

        Book bookForUpdating = session.get(Book.class, id);

        bookForUpdating.setBookName(newBook.getBookName());
        bookForUpdating.setAuthor(newBook.getAuthor());
        bookForUpdating.setYearOfPublication(newBook.getYearOfPublication());
        bookForUpdating.setPerson(newBook.getPerson());

//        jdbcTemplate.update("UPDATE book SET bookname=?, author=?, yearofpublication=? WHERE id=?",
//                newBook.getBookName(), newBook.getAuthor(), newBook.getYearOfPublication(), id);
    }

    @Transactional
    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
        //jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    @Transactional
    public void possessBook(int bookId, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book possessedBook = session.get(Book.class, bookId);
        possessedBook.setPerson(person);
        //jdbcTemplate.update("UPDATE book SET personId=? WHERE id=?", personId, bookId);
    }

    @Transactional
    public void deletePossessor(int bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book possessedBook = session.get(Book.class, bookId);
        possessedBook.setPerson(null);
        //jdbcTemplate.update("UPDATE book SET personid=null WHERE id=?", bookId);
    }

    @Transactional(readOnly = true)
    public List<Book> findPossessedBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b where person.id=?1", Book.class)
                .setParameter(1, id)
                .getResultList();

        //return jdbcTemplate.query("SELECT * FROM book WHERE personid=?", new Object[]{id}, new BookMapper());
    }

}

