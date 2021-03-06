package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.converters.RecipeCommandToRecipe;
import com.springframework.webdevelopment.recipeproject.converters.RecipeToRecipeCommand;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.exceptions.NotFoundException;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImpleTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe convertToRecipe;

    @Mock
    RecipeToRecipeCommand convertToRecipeCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImple(recipeRepository, convertToRecipe, convertToRecipeCommand);
    }

    @Test
    public void getRecipes() {

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        Recipe recipe3 = new Recipe();
        recipe3.setId(3L);
        Recipe recipe4 = new Recipe();
        recipe4.setId(4L);
        Set<Recipe> recipesSet = new HashSet();
        recipesSet.add(recipe1);
        recipesSet.add(recipe2);
        recipesSet.add(recipe3);
        recipesSet.add(recipe4);

        when(recipeRepository.findAll()).thenReturn(recipesSet);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 4);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipesById(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe foundedRecipe = recipeService.findById(1L);

        assertNotNull(foundedRecipe);
        assertEquals(recipe.getId(), foundedRecipe.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getRecipesByIdNotFound() throws Exception{
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe foundedRecipe = recipeService.findById(1L);

    }

    @Test
    public void testDeleteById(){

        //Given:
        Recipe recipe = new Recipe();
        recipe.setId(2L);

        //When:
        recipeService.deleteById(2L);

        //NO 'when' since method does not return any type, has void type.;

        //Then
        verify(recipeRepository, times(1)).deleteById(2L);


    }
}