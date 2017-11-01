package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.model.Category;
import guru.springframework.recipeproject.recipes.model.UnitOfMeasure;
import guru.springframework.recipeproject.recipes.repositories.CategoryRepository;
import guru.springframework.recipeproject.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository){
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ounce");
        System.out.println("CategoryOptional is " + categoryOptional.get().getId());
        System.out.println("UnitOfMeasureOptional is " + unitOfMeasureOptional.get().getId());
        return "index";
    }

}
