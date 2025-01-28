package com.kuzmins.library.repositories;

import com.kuzmins.library.models.Person;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Person getPersonByFullName(@NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters") String fullName);

}
