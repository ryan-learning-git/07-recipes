package guru.springframework.recipeproject.recipes.bootstrap;

import guru.springframework.recipeproject.recipes.enums.Difficulty;
import guru.springframework.recipeproject.recipes.model.*;
import guru.springframework.recipeproject.recipes.repositories.CategoryRepository;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
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
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        guacRecipe.getIngredients().add(new Ingredient("Ripe avocados", new BigDecimal(2), eachUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(.5), teaSpoonUom, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacRecipe));
        //more go in here

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

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
        tacoNotes.setRecipe(tacoRecipe);
        tacoRecipe.setNotes(tacoNotes);

        tacoRecipe.getIngredients().add(new Ingredient("Ancho Chilli Powder", new BigDecimal(2), tableSpoonUom, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUom, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUom, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUom, tacoRecipe));

        tacoRecipe.getCategories().add(mexicanCategory);

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
