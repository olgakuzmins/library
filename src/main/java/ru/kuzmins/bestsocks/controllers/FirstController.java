package ru.kuzmins.bestsocks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FirstController {

    @GetMapping("/main")
    public String helloPage(@RequestParam("name") String name,
                            @RequestParam("surname") String surname,
                            Model model) {
        model.addAttribute("message", "Hello, " + name + " " + surname);
        return "main_page";
    }

    @GetMapping("/promo")
    public String promotions() {
        return "promotion_page";
    }
}
