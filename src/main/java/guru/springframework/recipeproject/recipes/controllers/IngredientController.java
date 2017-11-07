package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;
import guru.springframework.recipeproject.recipes.commands.RecipeCommand;
import guru.springframework.recipeproject.recipes.commands.UnitOfMeasureCommand;
import guru.springframework.recipeproject.recipes.services.IngredientService;
import guru.springframework.recipeproject.recipes.services.RecipeService;
import guru.springframework.recipeproject.recipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        log.debug("Setting up ingredientController bean.");
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model){
        log.debug("Get ingredient list for recipe " + id);

        //use command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeID}/ingredient/{ingredientID}/show")
    public String showIngredient(@PathVariable String recipeID, @PathVariable String ingredientID, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeID), Long.valueOf(ingredientID)));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeID}/ingredient/new")
    public String newRecipe(@PathVariable String recipeID, Model model){
        //make sure we have a good ID value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeID));
        //TODO: Raise an exception if it's null

        //need to return back parent ID for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeID));
        model.addAttribute("ingredient", ingredientCommand);
        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }


    @GetMapping("recipe/{recipeID}/ingredient/{ingredientID}/update")
    public String updateIngredient(@PathVariable String recipeID, @PathVariable String ingredientID, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeID), Long.valueOf(ingredientID)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeID}/ingredient/{ingredientID}/delete")
    public String deleteIngredient(@PathVariable String recipeID, @PathVariable String ingredientID){

        ingredientService.deleteById(Long.valueOf(recipeID), Long.valueOf(ingredientID));

        return "redirect:/recipe/" + recipeID + "/ingredients";
    }

    @PostMapping("recipe/{recipeID}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        log.debug("saveOrUpdate: Save Or Update where command is " + command);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("Saved recipe ID: " + savedCommand.getRecipeId());
        log.debug("Saved ingredient ID: " + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }


}
