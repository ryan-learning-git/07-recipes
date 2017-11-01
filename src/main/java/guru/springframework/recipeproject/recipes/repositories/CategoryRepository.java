package guru.springframework.recipeproject.recipes.repositories;

import guru.springframework.recipeproject.recipes.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

}
