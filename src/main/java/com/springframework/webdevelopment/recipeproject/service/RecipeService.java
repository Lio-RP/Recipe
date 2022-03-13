package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.RecipeCommand;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService{

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand findByCommandId(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand object);

    void deleteById(Long l);
}
