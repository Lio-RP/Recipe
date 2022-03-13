package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.UnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.springframework.webdevelopment.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand convertUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand convertUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.convertUnitOfMeasureCommand = convertUnitOfMeasureCommand;
    }

/*   @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Set<UnitOfMeasure> uoms = (Set<UnitOfMeasure>) unitOfMeasureRepository.findAll();
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        UnitOfMeasureCommand command;
        for(UnitOfMeasure uom : uoms){
            command = convertUnitOfMeasureCommand.convert(uom);
            unitOfMeasureCommands.add(command);
        }
        return unitOfMeasureCommands;
    }
    Got an error saying ca not be cast class java.util.ArrayList to java.util.Set*/

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                        .spliterator(), false)
                .map(convertUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
