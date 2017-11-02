package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeListService {

    private RecipeRepository recipeRepository;

    public RecipeListService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Iterable<Recipe> findAllRecipes(){
        Iterable<Recipe> recipeList = recipeRepository.findAll();
        return recipeList;
    }

}
