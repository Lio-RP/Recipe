package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.IngredientCommand;
import com.springframework.webdevelopment.recipeproject.commands.UnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.converters.IngredientCommandToIngredient;
import com.springframework.webdevelopment.recipeproject.converters.IngredientToIngredientCommand;
import com.springframework.webdevelopment.recipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.springframework.webdevelopment.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.domain.Ingredient;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.domain.UnitOfMeasure;
import com.springframework.webdevelopment.recipeproject.repositories.IngredientRepository;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import com.springframework.webdevelopment.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand convertToIngredientCommand;
    private final IngredientCommandToIngredient convertToIngredient;

    public IngredientServiceImplTest() {
        this.convertToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.convertToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository, convertToIngredientCommand,
                convertToIngredient, unitOfMeasureRepository, ingredientRepository);
    }

    @Test
    void findByIngredientIdAndRecipeId() {

        //Given:
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);
        recipe.getIngredients().add(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //When
        IngredientCommand command = ingredientService.findByIngredientIdAndRecipeId(1L, 3L);

        //Then
        assertNotNull(command);
        assertEquals(3L, command.getId());
    }

    @Test
    void testSaveCommandObjectwithIngredientId() throws Exception{

        //Given;
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //When:
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then:
        assertEquals(3L, savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    void testSaveCommandObjectWithNoIngredientId() throws Exception{

        //Given;
        IngredientCommand ingredientCommand = new IngredientCommand();;
        ingredientCommand.setDescription("Some Description.");
        ingredientCommand.setAmount(new BigDecimal(12345));
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(1L);
        ingredientCommand.setUom(uomCommand);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();

        Ingredient ingredient = new Ingredient();
        ingredient.setDescription("Some Description.");
        ingredient.setAmount(new BigDecimal(12345));
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        ingredient.setUom(uom);

        savedRecipe.addIngredient(ingredient);
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //When:
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then:
        assertNotNull(savedCommand);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    void testDeleteById(){

        //Given;
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient recipeIngredient = new Ingredient();
        recipeIngredient.setId(1L);
        recipe.getIngredients().add(recipeIngredient);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(3L);

        //when:
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        ingredientService.deleteById(recipe.getId(), ingredient.getId());
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //then
        assertEquals(0, recipe.getIngredients().size());
        verify(recipeRepository, times(1)).findById(anyLong());
        assertEquals(0, savedRecipe.getIngredients().size());
    }
}