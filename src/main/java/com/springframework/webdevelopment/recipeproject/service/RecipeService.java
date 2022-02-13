package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService{

    Set<Recipe> getRecipes();
}
