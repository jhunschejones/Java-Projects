package com.joshuahunschejones.springbootmvc.controllers;

import com.joshuahunschejones.springbootmvc.beans.Product;
import com.joshuahunschejones.springbootmvc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired // dependency injection
    private ProductRepository productRepository;

    @GetMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        List<Product> products = productRepository.searchByName(search);
        model.addAttribute("products", products);
        return "search";
    }
}
