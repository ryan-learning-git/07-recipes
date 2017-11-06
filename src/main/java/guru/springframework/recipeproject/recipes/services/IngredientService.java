package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeID, Long id);

}
