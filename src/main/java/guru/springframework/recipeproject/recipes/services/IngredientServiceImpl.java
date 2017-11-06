package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;
import guru.springframework.recipeproject.recipes.converters.IngredientToIngredientCommand;
import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;

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

}
