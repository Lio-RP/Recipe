package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.UnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converer;

    @BeforeEach
    void setUp() {
        converer = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convert() {
        //Given:
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(1L);
        unitOfMeasureCommand.setDescription("TableSpoon");

        //When:
        UnitOfMeasure unitOfMeasure = converer.convert(unitOfMeasureCommand);

        //then:
        assertEquals(1L, unitOfMeasure.getId());
        assertEquals("TableSpoon", unitOfMeasure.getDescription());
    }

    @Test
    void convertNullable() {
        assertNull(converer.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converer.convert(new UnitOfMeasureCommand()));
    }
}