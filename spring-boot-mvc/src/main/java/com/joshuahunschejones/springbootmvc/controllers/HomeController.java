package com.joshuahunschejones.springbootmvc.controllers;

import com.joshuahunschejones.springbootmvc.beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // the name of the view we want to render using the view resolver
    }

    @GetMapping("/goToSearch")
    public String goToSearch() {
        return "search";
    }

    @GetMapping("/goToLogin")
    public String goToLogin() {
        return "login";
    }

    @GetMapping("/goToRegistration")
    public String goToRegistration() {
        return "register";
    }

    @ModelAttribute("newUser")
    public User getNewUser() {
        return new User();
    }

    @ModelAttribute("genderOptions")
    public List<String> getGenderOptions() {
        return Arrays.asList(new String[]{"Male", "Female", "Non-binary", "Prefer not to say"});
    }
}
