package com.joshuahunschejones.springbootmvc.controllers;

import com.joshuahunschejones.springbootmvc.beans.Login;
import com.joshuahunschejones.springbootmvc.beans.User;
import com.joshuahunschejones.springbootmvc.exceptions.ApplicationException;
import com.joshuahunschejones.springbootmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@ModelAttribute("login") Login login) {
        // For now, just go to the search page
        User user = userRepository.searchByUsername(login.getUsername());
        if (user == null) {
            throw new ApplicationException("user not found");
        }
        return "search";
    }

    @ExceptionHandler(ApplicationException.class)
    public String handleException() {
        return "error";
    }
}
