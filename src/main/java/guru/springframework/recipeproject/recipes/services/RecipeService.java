package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAllRecipes();

}
