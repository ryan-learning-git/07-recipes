package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.UnitOfMeasureCommand;

import java.util.Set;


public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();

}
