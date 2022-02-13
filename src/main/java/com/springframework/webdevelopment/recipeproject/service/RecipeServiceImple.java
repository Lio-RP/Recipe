package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImple implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImple(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        /*
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> {
            recipeSet.add(recipe);
        });*/
        return recipeSet;
    }
}
