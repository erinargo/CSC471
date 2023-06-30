package com.csc471.prj5;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Temporary Routing file for Project Demo
@Controller
@RequestMapping("/")

public class Router {
    @GetMapping("")
    public String home() {
        return "home";
    }
}