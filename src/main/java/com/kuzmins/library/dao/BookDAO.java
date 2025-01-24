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

    @Transactional(readOnly = true)
    public List<Book> allBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBook(String bookName) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Book.class, bookName));
    }

    @Transactional(readOnly = true)
    public Book getBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void saveBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void updateBook(int id, Book newBook) {
        Session session = sessionFactory.getCurrentSession();

        Book bookForUpdating = session.get(Book.class, id);

        bookForUpdating.setBookName(newBook.getBookName());
        bookForUpdating.setAuthor(newBook.getAuthor());
        bookForUpdating.setYearOfPublication(newBook.getYearOfPublication());
        bookForUpdating.setPerson(newBook.getPerson());
    }

    @Transactional
    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }

    @Transactional
    public void possessBook(int bookId, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book possessedBook = session.get(Book.class, bookId);
        possessedBook.setPerson(person);
    }

    @Transactional
    public void deletePossessor(int bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book possessedBook = session.get(Book.class, bookId);
        possessedBook.setPerson(null);
    }

    @Transactional(readOnly = true)
    public List<Book> findPossessedBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b where person.id=?1", Book.class)
                .setParameter(1, id)
                .getResultList();
    }

}

