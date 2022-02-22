package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.CategoryCommand;
import com.springframework.webdevelopment.recipeproject.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private final Long ID_VALUE = new Long(1);
    private final String DESCRIPTION = "Desription.";
    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void convert() {
        //Given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //When:
        Category category = converter.convert(categoryCommand);

        //Then:
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    void convertNullable() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }
}