package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.CategoryCommand;
import com.springframework.webdevelopment.recipeproject.commands.IngredientCommand;
import com.springframework.webdevelopment.recipeproject.commands.NotesCommand;
import com.springframework.webdevelopment.recipeproject.commands.RecipeCommand;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.domain.enums.Difficult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficult DIFFICULTY = Difficult.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure())
                );
    }

    @Test
    void testNullableObject(){
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject(){
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {

        //Given:
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);

        recipeCommand.setNotes(notesCommand);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CAT_ID_1);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);

        recipeCommand.getCategories().add(categoryCommand);
        recipeCommand.getCategories().add(categoryCommand2);


        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGRED_ID_1);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredientCommand);
        recipeCommand.getIngredients().add(ingredientCommand2);

        //When:
        Recipe recipe = converter.convert(recipeCommand);

        //then:
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());


    }
}