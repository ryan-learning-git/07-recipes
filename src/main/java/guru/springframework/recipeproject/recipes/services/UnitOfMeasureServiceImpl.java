package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeproject.recipes.model.UnitOfMeasure;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Spliterator<UnitOfMeasure> streamMe = unitOfMeasureRepository.findAll().spliterator();
        return StreamSupport.stream(streamMe, false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }

}
