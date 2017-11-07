package guru.springframework.recipeproject.recipes.services;

import guru.springframework.recipeproject.recipes.model.Recipe;
import guru.springframework.recipeproject.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeID, MultipartFile file) {
        log.debug("Received a file.");

        try {
            Recipe recipe = recipeRepository.findById(recipeID).get(); //should probably have some error check here
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i=0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        } catch (IOException e){
            log.error("File Upload - Error occurred.");

            //ToDo: Handle better.
            e.printStackTrace();
        }

    }

}
