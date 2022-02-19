package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImple implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImple(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'M in the services!");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        /*
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> {
            recipeSet.add(recipe);
        });*/
        return recipeSet;
    }
}
