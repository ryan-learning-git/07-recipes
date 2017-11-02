package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.services.RecipeListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    private RecipeListService recipeListService;

    public IndexController(RecipeListService recipeListService){
        this.recipeListService = recipeListService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){

        Iterable<Recipe> recipeList = recipeListService.findAllRecipes();
        model.addAttribute("recipes", recipeList);

        return "index";
    }

}
