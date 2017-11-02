package guru.springframework.recipeproject.recipes.bootstrap;

import guru.springframework.recipeproject.recipes.model.*;
import guru.springframework.recipeproject.recipes.repositories.CategoryRepository;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Profile("dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;

    public DevBootstrap(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        Optional<UnitOfMeasure> ounces = unitOfMeasureRepository.findByDescription("Ounce");
        Optional<Category> american = categoryRepository.findByDescription("American");
        Optional<Category> italian = categoryRepository.findByDescription("Italian");


        Recipe recipe1 = new Recipe();
        recipe1.setDescription("My Recipe");
        recipe1.setPrepTime(2);
        recipe1.setCookTime(3);
        recipe1.setServings(4);
        recipe1.setSource("RecipeWeb");
        recipe1.setUrl("http://www.recipeweb.com");
        recipe1.setDirections("Some instructions go here.");
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(new BigDecimal(3.5));
        ingredient.setDescription("flour");
        ingredient.setUom(ounces.get());
        ingredient.setRecipe(recipe1);
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        recipe1.setIngredients(ingredients);
        Notes notes = new Notes();
        notes.setRecipe(recipe1);
        notes.setRecipeNotes("Here are some recipe notes.");
        recipe1.setNotes(notes);
        Set<Category> categories = new HashSet<>();
        categories.add(american.get());
        categories.add(italian.get());
        recipe1.setCategories(categories);
        recipeRepository.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("My Other Recipe");
        recipe2.setPrepTime(2);
        recipe2.setCookTime(3);
        recipe2.setServings(4);
        recipe2.setSource("DeliaOnline");
        recipe2.setUrl("http://www.delia.com");
        recipe2.setDirections("Some instructions go here.");
        categories = new HashSet<>();
        categories.add(italian.get());
        recipe2.setCategories(categories);
        notes = new Notes();
        notes.setRecipe(recipe2);
        notes.setRecipeNotes("Here are some more recipe notes.");
        recipe2.setNotes(notes);
        recipeRepository.save(recipe2);

    }


}
