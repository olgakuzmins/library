package com.kuzmins.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kuzmins.library.dao.BookDAO;
import com.kuzmins.library.dao.PersonDAO;
import com.kuzmins.library.models.Person;
import com.kuzmins.library.util.PersonValidator;



@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String allPeople(Model model) {
        model.addAttribute("people", personDAO.allPeople());
        return "person/allPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("list", bookDAO.findPossessedBooks(id));
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
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.getPerson(id));
        return "person/update_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute ("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        System.out.println(bindingResult);
        if (bindingResult.hasErrors()){
            return "person/update_person";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }



}
