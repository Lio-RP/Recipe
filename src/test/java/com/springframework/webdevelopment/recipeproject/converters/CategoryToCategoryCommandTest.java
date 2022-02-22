package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.CategoryCommand;
import com.springframework.webdevelopment.recipeproject.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "descript";

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void convert() {
        //Given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //When
        CategoryCommand command = converter.convert(category);

        //then
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }

    @Test
    void convertNullable() {

        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {

        assertNotNull(converter.convert(new Category()));
    }
}