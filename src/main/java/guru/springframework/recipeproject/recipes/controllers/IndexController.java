package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;


@Controller
public class IndexController {

    private final RecipeService recipeListService;

    public IndexController(RecipeService recipeListService){
        this.recipeListService = recipeListService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){

        Set<Recipe> recipeList = recipeListService.findAllRecipes();
        model.addAttribute("recipes", recipeList);

        return "index";
    }

}
