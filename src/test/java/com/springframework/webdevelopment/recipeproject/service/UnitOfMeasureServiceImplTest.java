package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.UnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.domain.UnitOfMeasure;
import com.springframework.webdevelopment.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand convertUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    UnitOfMeasureServiceImpl service;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    //@Mock
    //UnitOfMeasureToUnitOfMeasureCommand convertUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, convertUnitOfMeasureCommand);

    }

    @Test
    void testListAllUoms() {

        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}