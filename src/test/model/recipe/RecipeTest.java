package test.model.recipe;

import main.model.food.Ingredient;
import main.model.recipe.IllegalMeasureArgumentException;
import main.model.recipe.IllegalQuantityValueException;
import main.model.recipe.Recipe;
import main.model.recipe.RecipeIngredient;
import main.model.units.MassMeasureUnit;
import main.model.units.VolumeMeasureUnit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    @Test
    void addRecipeIngredient() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Gram, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        assertEquals(recipeIngredient, recipe.getRecipeIngredientList().get(0));
    }

    @Test
    void removeIngredient() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Gram, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        assertEquals(recipeIngredient, recipe.getRecipeIngredientList().get(0));
        recipe.removeIngredient(0);
        assertTrue(recipe.getRecipeIngredientList().isEmpty());
    }

    @Test
    void addDirection() {
        Recipe recipe = new Recipe("testRecipe");
        recipe.addDirection("ala123");
        assertFalse(recipe.getDirections().isEmpty());
        assertEquals("ala123", recipe.getDirections().get(0));
    }

    @Test
    void removeDirection() {
        Recipe recipe = new Recipe("testRecipe");
        recipe.addDirection("ala123");
        recipe.removeDirection(0);
        assertTrue(recipe.getDirections().isEmpty());
    }

    @Test
    void isVegetarian() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Gram, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        assertTrue(recipe.isVegetarian());
        Recipe recipe2 = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Gram, 10);
        recipe2.addRecipeIngredient(recipeIngredient2);
        assertFalse(recipe2.isVegetarian());
    }

    @Test
    void isNutFree() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Gram, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        assertFalse(recipe.isNutFree());
        Recipe recipe2 = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Gram, 10);
        recipe2.addRecipeIngredient(recipeIngredient2);
        assertTrue(recipe2.isNutFree());
    }

    @Test
    void isLactoseFree() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Cup, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        assertFalse(recipe.isLactoseFree());
        Recipe recipe2 = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Gram, 10);
        recipe2.addRecipeIngredient(recipeIngredient2);
        assertTrue(recipe2.isLactoseFree());
    }

    @Test
    void canBeMadeWith() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Cup, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Gram, 10);
        recipe.addRecipeIngredient(recipeIngredient2);
        List<Ingredient> list = new ArrayList<>();
        list.add(Ingredient.Hazelnut);
        assertFalse(recipe.canBeMadeWith(list));
        list.add(Ingredient.Butter);
        assertFalse(recipe.canBeMadeWith(list));
        list.add(Ingredient.ChickenBreast);
        assertTrue(recipe.canBeMadeWith(list));
        list.add(Ingredient.Flour);
        assertTrue(recipe.canBeMadeWith(list));
        list.add(Ingredient.ChickenBreast);
        assertTrue(recipe.canBeMadeWith(list));
    }

    @Test
    void equals() throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        Recipe recipe = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient = new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Cup, 10);
        recipe.addRecipeIngredient(recipeIngredient);
        Recipe recipe2 = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient2 = new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Gram, 10);
        recipe2.addRecipeIngredient(recipeIngredient2);
        Recipe recipe3 = new Recipe("testRecipe");
        RecipeIngredient recipeIngredient3 = new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Cup, 10);
        recipe3.addRecipeIngredient(recipeIngredient3);
        assertFalse(recipe3.equals(recipe2));
        assertTrue(recipe.equals(recipe3));
        assertTrue(recipe.equals(recipe));
    }

}