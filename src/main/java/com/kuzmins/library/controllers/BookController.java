package com.kuzmins.library.controllers;

import com.kuzmins.library.service.BookService;
import com.kuzmins.library.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.kuzmins.library.models.Book;
import com.kuzmins.library.models.Person;

@Controller
@RequestMapping("/books")
public class BookController {

    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

        @GetMapping()
        public String allBooks(Model model) {
            model.addAttribute("books", bookService.findAll());
            return "book/allBooks";
        }

        @GetMapping("/{id}")
        public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
            Book book = bookService.findOne(id);
            model.addAttribute("book", book);
            if (book.getPerson() != null) {
                int personId = book.getPerson().getId();
                model.addAttribute("man", peopleService.findOne(personId));
            }
                model.addAttribute("people", peopleService.findAll());
            return "book/book";
        }

        @GetMapping("/new")
        public String newBook(@ModelAttribute("book") Book book) {
            return "book/new_book";
        }

        @PostMapping
        public String createBook(@ModelAttribute ("book") @Valid Book book){
            bookService.save(book);
            return "redirect:/books";
        }

        @GetMapping("/{id}/edit")
        public String editBook(Model model, @PathVariable("id") int id) {
            model.addAttribute("book", bookService.findOne(id));
            return "book/update_book";
        }

        @PatchMapping("/{id}")
        public String updateBook(@ModelAttribute ("book") @Valid Book book,
                             @PathVariable("id") int id) {
            bookService.update(id, book);
            return "redirect:/books";
        }

        @DeleteMapping("/{id}")
        public String deleteBook(@PathVariable("id") int id) {
            bookService.delete(id);
            return "redirect:/books";
        }

        @PatchMapping("/{bookId}/admin/add")
        public String assignPossessor(@ModelAttribute("person") Person person,
                                      @PathVariable("bookId") int bookId) {
            bookService.possessBook(bookId, person);
            return "redirect:/books";
        }

        @PatchMapping("/{bookId}/admin")
        public String deletePossessor(@ModelAttribute("book") Book book,
                                      @PathVariable("bookId") int bookId) {
            bookService.deletePossessor(bookId);
            return "redirect:/books";
        }

    }
