package com.springframework.webdevelopment.recipeproject.controllers;

import com.springframework.webdevelopment.recipeproject.commands.RecipeCommand;
import com.springframework.webdevelopment.recipeproject.converters.RecipeToRecipeCommand;
import com.springframework.webdevelopment.recipeproject.exceptions.NotFoundException;
import com.springframework.webdevelopment.recipeproject.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    public static final String RECIPE_RECIPE_FORM = "recipe/recipeForm";

    private final RecipeService recipeService;
    private final RecipeToRecipeCommand convertToRecipeCommand;

    public RecipeController(RecipeService recipeService, RecipeToRecipeCommand convertToRecipeCommand) {
        this.recipeService = recipeService;
        this.convertToRecipeCommand = convertToRecipeCommand;
    }

    @GetMapping("/recipe/{id}/show")
    public String showRecipeById(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newForm(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPE_FORM;
    }

    @GetMapping("/recipe/{id}/update")
    public String updateForm(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findByCommandId(id));

        return RECIPE_RECIPE_FORM;
    }

    //@RequestMapping(name = "recipe", method=RequestMethod.POST) OLD WAY

    @PostMapping("recipe")
    public String createOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return RECIPE_RECIPE_FORM;
        }
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable Long id){

        log.debug("Deleting Id : " + id);

        recipeService.deleteById(id);

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handNotFound(Exception exception){

        log.debug("Handling 404 Nof Found...");
        log.debug(exception.getMessage());

        ModelAndView mav = new ModelAndView();

        mav.setViewName("404Error");
        mav.addObject("exception", exception);

        return mav;
    }
}
