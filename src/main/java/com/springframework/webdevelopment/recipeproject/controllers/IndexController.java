package com.springframework.webdevelopment.recipeproject.controllers;

import com.springframework.webdevelopment.recipeproject.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/home", "/recipes"})
    public String getIndexPage(Model model){
        log.debug("I'M in the Controller Class!");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
