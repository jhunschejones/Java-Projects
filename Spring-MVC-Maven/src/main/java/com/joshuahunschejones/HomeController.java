package com.joshuahunschejones;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String index() {
        System.out.println("In home controller");
        return "index"; // the name of the view we want to render using the view resolver
    }
}
