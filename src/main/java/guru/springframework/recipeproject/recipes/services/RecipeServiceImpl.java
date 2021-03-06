package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.RecipeCommand;
import guru.springframework.recipeproject.recipes.converters.RecipeCommandToRecipe;
import guru.springframework.recipeproject.recipes.converters.RecipeToRecipeCommand;
import guru.springframework.recipeproject.recipes.exceptions.NotFoundException;
import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand){
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> findAllRecipes(){
        log.debug("Hello, this is a log message from within the service.");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id){
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()){
            throw new NotFoundException("Recipe " + id + " not found!");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        System.out.println("saveRecipeCommand: command.id = " + command.getId());
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        System.out.println("saveRecipeCommand: detachedRecipe.id = " + detachedRecipe.getId());

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        System.out.println("saveRecipeCommand: savedRecipe.id = " + savedRecipe.getId());
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id){
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id){
        recipeRepository.deleteById(id);
    }

}
