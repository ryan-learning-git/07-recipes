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

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeListService){
        this.recipeService = recipeListService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){
        log.debug("Start getIndexPage");
        System.out.println("Start indexpage");
        Set<Recipe> recipeList = recipeService.findAllRecipes();
        model.addAttribute("recipes", recipeList);
        log.debug("End getIndexPage");
        System.out.println("Model is " + model);
        System.out.println("RecipeService is " + recipeService);
        System.out.println("End indexpage with recipeList " + recipeList);
        return "index";
    }

}
