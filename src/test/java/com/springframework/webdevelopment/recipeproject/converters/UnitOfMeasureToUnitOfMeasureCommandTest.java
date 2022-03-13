package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.UnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convert() {

        //Given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasure.setDescription("Teaspoon");

        //When:
        UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(unitOfMeasure);

        //THen:
        assertEquals(1L, unitOfMeasureCommand.getId());
        assertEquals("Teaspoon", unitOfMeasureCommand.getDescription());

    }

    @Test
    void convertNullable() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }
}