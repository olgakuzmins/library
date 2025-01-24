package com.kuzmins.library.dao;

import com.kuzmins.library.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kuzmins.library.models.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> allPeople() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Person> getPerson(String fullName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p where p.fullName=?1", Person.class)
                .setParameter(1, fullName)
                .getResultList().stream().findAny();
        //return Optional.ofNullable(session.get(Person.class, fullName));
    }

    @Transactional(readOnly = true)
    public Person getPerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person newPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personForUpdating = session.get(Person.class, id);
        personForUpdating.setFullName(newPerson.getFullName());
        personForUpdating.setYearOfBirth(newPerson.getYearOfBirth());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

}

