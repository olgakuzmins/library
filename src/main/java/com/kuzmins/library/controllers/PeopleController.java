package com.kuzmins.library.controllers;

import com.kuzmins.library.service.BookService;
import com.kuzmins.library.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kuzmins.library.models.Person;
import com.kuzmins.library.util.PersonValidator;



@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final BookService bookService;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator, BookService bookService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.bookService = bookService;
    }

    @GetMapping()
    public String allPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "person/allPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("list", bookService.findPossessedBooks(id));
        return "person/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "person/new_person";
    }

    @PostMapping
    public String createPerson(@ModelAttribute ("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "person/new_person";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "person/update_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute ("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()){
            return "person/update_person";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
