package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class IndexControllerTest {

    IndexController controller;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService);
    }


    @Test
    public void getIndexPage() {
        Set<Recipe> recipeSet = new HashSet<>();
        String expectedViewName = "index";
        String viewName = controller.getIndexPage(model);
        assertEquals(expectedViewName, viewName);
        verify(recipeService, times(1)).findAllRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }//getIndexPage

}//class