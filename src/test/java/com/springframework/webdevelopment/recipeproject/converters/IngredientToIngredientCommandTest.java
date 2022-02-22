package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.IngredientCommand;
import com.springframework.webdevelopment.recipeproject.domain.Ingredient;
import com.springframework.webdevelopment.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class IngredientToIngredientCommandTest {


    IngredientToIngredientCommand converter;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        converter = new IngredientToIngredientCommand(unitOfMeasureCommand);
    }

    @Test
    void testConvertWithEmptyObject(){
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void testConvertWithNullable(){
        assertNull(converter.convert(null));
    }

    @Test
    void testConvertWithUom() {

        //Given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setDescription("descript.");
        ingredient.setAmount(new BigDecimal(12345));

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(2L);

        ingredient.setUnitOfMeasure(unitOfMeasure);

        //When:
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //Then:
        assertEquals(1L, ingredientCommand.getId());
        assertEquals("descript.", ingredientCommand.getDescription());
    }

    @Test
    void testConvertNullOfUom() {

        //Given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setDescription("descript.");
        ingredient.setAmount(new BigDecimal(12345));
        ingredient.setUnitOfMeasure(null);

        //When:
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //Then:
        assertEquals(1L, ingredientCommand.getId());
        assertEquals("descript.", ingredientCommand.getDescription());
    }
}