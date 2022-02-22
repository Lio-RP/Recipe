package com.springframework.webdevelopment.recipeproject.converters;

import com.springframework.webdevelopment.recipeproject.commands.NotesCommand;
import com.springframework.webdevelopment.recipeproject.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    private final Long ID_VALUE = 1L;
    private final String RECIPE_NOTE = "Recipes.";

    NotesCommandToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void convert() {
        //Given:
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTE);

        //When:
        Notes notes = converter.convert(notesCommand);

        //Then:
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTE, notes.getRecipeNotes());

    }

    @Test
    void convertNullable() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new NotesCommand()));
    }
}