package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.services.IngredientService;
import guru.springframework.recipeproject.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        log.debug("Setting up ingredientController bean.");
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model){
        log.debug("Get ingredient list for recipe " + id);

        //use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeID}/ingredient/{ingredientID}/show")
    public String showIngredient(@PathVariable String recipeID, @PathVariable String ingredientID, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeID), Long.valueOf(ingredientID)));
        return "recipe/ingredient/show";
    }

}
