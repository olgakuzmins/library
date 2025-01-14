package com.kuzmins.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kuzmins.library.dao.BookDAO;
import com.kuzmins.library.dao.PersonDAO;
import com.kuzmins.library.models.Book;
import com.kuzmins.library.models.Person;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

        @GetMapping()
        public String allBooks(Model model) {
            model.addAttribute("books", bookDAO.allBooks());
            return "book/allBooks";
        }

        @GetMapping("/{id}")
        public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
            Book book = bookDAO.getBook(id);
            model.addAttribute("book", book);
            int personId = book.getPersonId();
            model.addAttribute("man", personDAO.getPerson(personId));
            model.addAttribute("people", personDAO.allPeople());
            return "book/book";
        }

        @GetMapping("/new")
        public String newBook(@ModelAttribute("book") Book book) {
            return "book/new_book";
        }

        @PostMapping
        public String createBook(@ModelAttribute ("book") @Valid Book book, BindingResult bindingResult){
            bookDAO.saveBook(book);
            return "redirect:/books";
        }

        @GetMapping("/{id}/edit")
        public String editBook(Model model, @PathVariable("id") int id) {
            model.addAttribute("book", bookDAO.getBook(id));
            return "book/update_book";
        }

        @PatchMapping("/{id}")
        public String updateBook(@ModelAttribute ("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
            bookDAO.updateBook(id, book);
            return "redirect:/books";
        }

        @DeleteMapping("/{id}")
        public String deleteBook(@PathVariable("id") int id) {
            bookDAO.deleteBook(id);
            return "redirect:/books";
        }

        @PatchMapping("/{bookId}/admin/add")
        public String assignPossessor(@ModelAttribute("person") Person person,
                                      BindingResult bindingResult,
                                      @PathVariable("bookId") int bookId) {
            bookDAO.possessBook(bookId, person.getId());
            return "redirect:/books";
        }

        @PatchMapping("/{bookId}/admin")
        public String deletePossessor(@ModelAttribute("book") Book book, BindingResult bindingResult,
                                      @PathVariable("bookId") int bookId) {
            bookDAO.deletePossessor(bookId);
            return "redirect:/books/{bookId}";
        }



    }
