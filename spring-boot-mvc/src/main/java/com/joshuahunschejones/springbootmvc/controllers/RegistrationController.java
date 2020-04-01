package com.joshuahunschejones.springbootmvc.controllers;

import com.joshuahunschejones.springbootmvc.beans.User;
import com.joshuahunschejones.springbootmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registerUser")
    public String registerUser(@Valid @ModelAttribute("newUser") User user, BindingResult result, Model model) {
        // @Valid will run the validations defined in the User class
        // BindingResult has to come exactly after the @ModelAttribute parameter and will contain any
        // errors returned during validation

        // this will evaluate to true if there are validation errors
        if (result.hasErrors()) {
            return "register";
        }
        userRepository.save(user);
        model.addAttribute("dataSaved", "User registered successfully");
        return "login";
    }
}
