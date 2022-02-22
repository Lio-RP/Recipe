package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.NotesCommand;
import com.springframework.webdevelopment.recipeproject.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    private final Long ID_VALUE = 1L;
    private final String RECIPE_NOTE = "Recipes.";

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void convert() {
        //Given:
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTE);

        //When:
        NotesCommand notesCommand = converter.convert(notes);

        //Then:
        assertEquals(ID_VALUE, notesCommand.getId());
        System.out.println(notesCommand.getRecipeNotes());
        //assertEquals(RECIPE_NOTE, notesCommand.getRecipeNotes());

    }

    @Test
    void convertNullable() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new Notes()));
    }
}