package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.converters.RecipeCommandToRecipe;
import guru.springframework.recipeproject.recipes.converters.RecipeToRecipeCommand;
import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    public void findAllRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesSet = new HashSet<>();
        recipesSet.add(recipe);

        when(recipeService.findAllRecipes()).thenReturn(recipesSet);

        Set<Recipe> recipes = recipeService.findAllRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll(); //confirm that findAll was called only once
    }

    @Test
    public void testDeleteById(){
        Long idToDelete = Long.valueOf(2L);
        recipeService.deleteById(idToDelete);

        //no when since the method has a void return type

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}