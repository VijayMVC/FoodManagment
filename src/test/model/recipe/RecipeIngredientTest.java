package test.model.recipe;

import main.model.food.Ingredient;
import main.model.recipe.IllegalMeasureArgumentException;
import main.model.recipe.IllegalQuantityValueException;
import main.model.recipe.RecipeIngredient;
import main.model.units.MassMeasureUnit;
import main.model.units.NotConvertibleException;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeIngredientTest {
    @Test
    void equals() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Gram, 1000);
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Kilogram, 1);
        RecipeIngredient recipeIngredient3 = new RecipeIngredient(Ingredient.ChickenBreast , MassMeasureUnit.Gram, 1000);
        RecipeIngredient recipeIngredient4 = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Gram, 1000);
        RecipeIngredient recipeIngredient5 = new RecipeIngredient(Ingredient.MarsalaVine , VolumeMeasureUnit.Cup, 1000);
        assertFalse(recipeIngredient.equals(recipeIngredient3));
        assertFalse(recipeIngredient.equals(recipeIngredient5));
        assertTrue(recipeIngredient.equals(recipeIngredient2));
        assertTrue(recipeIngredient.equals(recipeIngredient4));
    }


    @Test
    void convertToMeasureUnit() throws IllegalQuantityValueException, IllegalMeasureArgumentException, NotConvertibleException {
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Gram, 1000);
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Gram, 1000);
        recipeIngredient2.convertToMeasureUnit(MassMeasureUnit.Kilogram);
        assertTrue(recipeIngredient.equals(recipeIngredient2));

        RecipeIngredient recipeIngredient3 = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Gram, 1000);
        RecipeIngredient recipeIngredient4 = new RecipeIngredient(Ingredient.Hazelnut , MassMeasureUnit.Gram, 1000);
        recipeIngredient4.convertToMeasureUnit(MassMeasureUnit.Pound);
        assertTrue(recipeIngredient3.equals(recipeIngredient4));
    }
}