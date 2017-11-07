package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.commands.RecipeCommand;
import guru.springframework.recipeproject.recipes.services.ImageService;
import guru.springframework.recipeproject.recipes.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService){
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeID}/image")
    public String showUploadForm(@PathVariable String recipeID, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeID)));
        return "recipe/imageuploadform";
    }


    @PostMapping("recipe/{recipeID}/image")
    public String handleImagePost(@PathVariable String recipeID, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(recipeID), file);
        return "redirect:/recipe/" + recipeID + "/show";
    }

    @GetMapping("recipe/{recipeID}/recipeimage")
    public void renderImageFromDB(@PathVariable String recipeID, HttpServletResponse response) throws IOException{
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeID));

        byte[] byteArray = new byte[recipeCommand != null && recipeCommand.getImage() !=null ? recipeCommand.getImage().length : 0];

        int i = 0;

        if (recipeCommand != null && recipeCommand.getImage() != null){
            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto-unboxing
            }
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }

}
