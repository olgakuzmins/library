package com.kuzmins.library.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters")
    private String fullName;

    @Min(value = 1900, message = "Year of birth should have this format: 1985")
    private int yearOfBirth;


    public Person(){}

    public Person(int id, String name, int yearOfBirth) {
       this.id = id;
       this.fullName = name;
       this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
       return yearOfBirth;
    }
    public void setYearOfBirth(int yearOfBirth) {
       this.yearOfBirth = yearOfBirth;
    }
}
