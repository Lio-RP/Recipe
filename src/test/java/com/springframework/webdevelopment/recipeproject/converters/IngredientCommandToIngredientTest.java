package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.IngredientCommand;
import com.springframework.webdevelopment.recipeproject.commands.UnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {



    IngredientCommandToIngredient converter;

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureCommand = new UnitOfMeasureCommandToUnitOfMeasure();
        converter = new IngredientCommandToIngredient(unitOfMeasureCommand);
    }

    @Test
    void testConvertWithEmptyObject(){
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void testConvertWithNullable(){
        assertNull(converter.convert(null));
    }

    @Test
    void testConvertWithUom() {

        //Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setDescription("descript.");
        ingredientCommand.setAmount(new BigDecimal(12345));

        UnitOfMeasureCommand unitOfMeasure = new UnitOfMeasureCommand();
        unitOfMeasure.setId(2L);

        ingredientCommand.setUom(unitOfMeasure);

        //When:
        Ingredient ingredient = converter.convert(ingredientCommand);

        //Then:
        assertEquals(1L, ingredientCommand.getId());
        assertEquals("descript.", ingredientCommand.getDescription());
        assertEquals(new BigDecimal(12345), ingredientCommand.getAmount());
        assertEquals(2L, ingredientCommand.getUom().getId());
    }

    @Test
    void testConvertNullOfUom() {

        //Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setDescription("descript.");
        ingredientCommand.setAmount(new BigDecimal(12345));
        ingredientCommand.setUom(null);

        //When:
        Ingredient ingredient = converter.convert(ingredientCommand);

        //Then:
        assertEquals(1L, ingredientCommand.getId());
        assertEquals("descript.", ingredientCommand.getDescription());
        assertEquals(new BigDecimal(12345), ingredientCommand.getAmount());
        assertEquals(null, ingredientCommand.getUom());
        assertNull(ingredientCommand.getUom());
    }
}