package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;
import guru.springframework.recipeproject.recipes.converters.IngredientCommandToIngredient;
import guru.springframework.recipeproject.recipes.converters.IngredientToIngredientCommand;
import guru.springframework.recipeproject.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipeproject.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipeproject.recipes.model.Ingredient;
import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;


    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        UnitOfMeasureToUnitOfMeasureCommand uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        UnitOfMeasureCommandToUnitOfMeasure uomConverterBack = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(uomConverter);
        ingredientCommandToIngredient = new IngredientCommandToIngredient(uomConverterBack);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
    }


    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }


}