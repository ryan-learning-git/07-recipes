package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;


@Controller
@Slf4j
public class IndexController {

    private final RecipeService recipeListService;

    public IndexController(RecipeService recipeListService){
        this.recipeListService = recipeListService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
        log.debug("Start getIndexPage");
        Set<Recipe> recipeList = recipeListService.findAllRecipes();
        model.addAttribute("recipes", recipeList);
        log.debug("End getIndexPage");
        return "index";
    }

}
