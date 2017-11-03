package guru.springframework.recipeproject.recipes.converters;

import guru.springframework.recipeproject.recipes.commands.IngredientCommand;
import guru.springframework.recipeproject.recipes.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null){
            return null;
        }

        final IngredientCommand ingredient = new IngredientCommand();
        ingredient.setAmount(source.getAmount());
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }//convert
}
