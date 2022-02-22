package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.RecipeCommand;
import com.springframework.webdevelopment.recipeproject.converters.RecipeToRecipeCommand;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "NEW_Description.";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand convertToRecipeCommand;

    @BeforeEach
    void setUp() {
    }

    @Transactional
    @Test
    void testSaveOfDescription() {

        //Given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = convertToRecipeCommand.convert(testRecipe);

        //When:
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //Then:
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());


    }
}