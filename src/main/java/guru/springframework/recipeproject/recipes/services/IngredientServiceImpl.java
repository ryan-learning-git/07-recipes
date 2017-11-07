package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;
import guru.springframework.recipeproject.recipes.converters.IngredientCommandToIngredient;
import guru.springframework.recipeproject.recipes.converters.IngredientToIngredientCommand;
import guru.springframework.recipeproject.recipes.model.Ingredient;
import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeID, Long ingredientID) {
        log.debug("findByRecipeIdAndIngredientId where recipeID is " + recipeID + " and ingredientID is " + ingredientID);
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);

        if (!recipeOptional.isPresent()){
            //TODO: implement error handling
            log.error("Recipe id not found: " + recipeID);
            //throw new RuntimeException("Sorry, no recipe found.");
        }

        Recipe recipe = recipeOptional.get();

        log.debug("Recipe is " + recipe);

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientID))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()){
            //TODO: Implement error handling
            log.error("Ingredient " + ingredientID + " not found");
            //throw new RuntimeException("Sorry, no ingredient found.");
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Long recipeID = command.getRecipeId();
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        log.debug("saveIngredientCommand: recipeOptional is " + recipeOptional);

        if (!recipeOptional.isPresent()) {
            //todo throw an error if not found
            log.error("Recipe not found for id " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) { //edit
                //this really ought to be running through a converter of some kind.
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //TODO: Make this all a bit more elegant
            } else { //add new ingredient to recipe
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //need to check for fail
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

}
