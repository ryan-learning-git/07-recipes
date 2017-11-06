package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.RecipeCommand;
import guru.springframework.recipeproject.recipes.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAllRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findByIdCommand(Long id);

    void deleteById(Long id);
}
