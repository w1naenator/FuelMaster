package lv.ami.fuelmaster.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lv.ami.fuelmaster.models.AppUser;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute AppUser user) {
        return "redirect:home";
    }
}
