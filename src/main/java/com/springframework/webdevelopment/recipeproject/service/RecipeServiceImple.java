package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.RecipeCommand;
import com.springframework.webdevelopment.recipeproject.converters.RecipeCommandToRecipe;
import com.springframework.webdevelopment.recipeproject.converters.RecipeToRecipeCommand;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImple implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe convertToRecipe;
    private final RecipeToRecipeCommand convertToRecipeCommand;

    public RecipeServiceImple(RecipeRepository recipeRepository,
                              RecipeCommandToRecipe convertToRecipe,
                              RecipeToRecipeCommand convertToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.convertToRecipe = convertToRecipe;
        this.convertToRecipeCommand = convertToRecipeCommand;
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

    public Recipe findById(Long l){
        Optional<Recipe> recipe = recipeRepository.findById(l);
        if(!recipe.isPresent()){
            throw new RuntimeException("Recipe Not Found!.");
        }
        return recipe.get();
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand object) {
        Recipe detachedRecipe = convertToRecipe.convert(object);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        log.debug("saved Recipe Id : " + savedRecipe.getId());
        return convertToRecipeCommand.convert(savedRecipe);
    }
}
