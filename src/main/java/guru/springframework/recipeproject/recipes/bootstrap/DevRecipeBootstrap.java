package guru.springframework.recipeproject.recipes.bootstrap;

import guru.springframework.recipeproject.recipes.enums.Difficulty;
import guru.springframework.recipeproject.recipes.model.*;
import guru.springframework.recipeproject.recipes.repositories.CategoryRepository;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Profile("dev")
@Slf4j
public class DevRecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DevRecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<Recipe> recipes = getRecipes();
        recipeRepository.saveAll(recipes);
        log.debug("Bootstrap: All recipes saved.");
    }

    public List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>(2);

        //getUOMs
        UnitOfMeasure eachUom = getUom("Each");
        UnitOfMeasure tableSpoonUom = getUom("Tablespoon");
        UnitOfMeasure teaSpoonUom = getUom("Teaspoon");
        UnitOfMeasure dashUom = getUom("Dash");
        UnitOfMeasure pintUom = getUom("Pint");
        UnitOfMeasure cupUom = getUom("Cup");

        //get categories
        Category americanCategory = getCategory("American");
        Category mexicanCategory = getCategory("Mexican");

        //guacamole
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("I'm not typing all that up.");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Here are some notes.");
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(.5), teaSpoonUom));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        //more go in here

        guacRecipe.addCategory(americanCategory);
        guacRecipe.addCategory(mexicanCategory);

        recipes.add(guacRecipe);

        //tacos
        Recipe tacoRecipe = new Recipe();
        tacoRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacoRecipe.setCookTime(9);
        tacoRecipe.setPrepTime(20);
        tacoRecipe.setDifficulty(Difficulty.MODERATE);
        tacoRecipe.setDirections("Nope, still not typing that up.");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("Huge amount of text, no thanks.");
        tacoRecipe.setNotes(tacoNotes);

        tacoRecipe.addIngredient(new Ingredient("Ancho Chilli Powder", new BigDecimal(2), tableSpoonUom));
        tacoRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUom));
        tacoRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUom));
        tacoRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUom));

        tacoRecipe.addCategory(mexicanCategory);

        recipes.add(tacoRecipe);

        return recipes;
    }

    private UnitOfMeasure getUom(String description){
        Optional<UnitOfMeasure> optional = unitOfMeasureRepository.findByDescription(description);
        if (!optional.isPresent()){
            throw new RuntimeException("Expected UOM "+description+" not found");
        }
        return optional.get();
    }

    private Category getCategory(String description){
        Optional<Category> optional = categoryRepository.findByDescription(description);
        if (!optional.isPresent()){
            throw new RuntimeException("Expected Category "+description+" not found");
        }
        return optional.get();
    }

}
