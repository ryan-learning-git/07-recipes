package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeID, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recipeID, Long ingredientID);
}
