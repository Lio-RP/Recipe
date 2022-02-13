package com.springframework.webdevelopment.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "/home"})
    public String getIndexPage(){
        System.out.println("system restarted..45...123");
        return "index";
    }
}
