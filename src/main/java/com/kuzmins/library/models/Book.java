package com.kuzmins.library.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {

        private int id;

        @NotEmpty(message = "Name should not be empty")
        @Size(min = 1, max = 50, message = "name should be from 1 to 50")
        private String bookName;

        @NotEmpty(message = "Name should not be empty")
        @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters")
        private String author;

        @Min(value = 1000, message = "Year of publication should have this format: 1985")
        private int yearOfPublication;

        private int personId;

        public Book() {}
        public Book(int id, String bookName, String author, int yearOfPublication, int personId) {
            this.id = id;
            this.bookName = bookName;
            this.author = author;
            this.yearOfPublication = yearOfPublication;
            this.personId = personId;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
