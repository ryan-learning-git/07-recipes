package guru.springframework.recipeproject.recipes.repositories;

import guru.springframework.recipeproject.recipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
