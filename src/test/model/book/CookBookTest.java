package test.model.book;

import main.model.book.CookBook;
import main.model.book.DuplicateRecipeException;
import main.model.book.MergeSameNamedCookBooks;
import main.model.book.RecipeNotFoundException;
import main.model.collection.MergeSameNamedCollectionsException;
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

class CookBookTest {
    @Test
    void addRecipe() throws DuplicateRecipeException {
        CookBook testCookBook = new CookBook("testCookBook");
        Recipe recipe = new Recipe("rissoto");
        testCookBook.addRecipe(recipe);
        assertEquals(testCookBook.getRecipe("rissoto"),recipe);
        assertThrows(DuplicateRecipeException.class, ()->testCookBook.addRecipe(recipe) );
    }

    @Test
    void removeRecipe() throws DuplicateRecipeException {
        CookBook testCookBook = new CookBook("testCookBook");
        testCookBook.removeRecipe("");
        Recipe recipe = new Recipe("rissoto");
        testCookBook.addRecipe(recipe);
        assertFalse(testCookBook.getRecipe("rissoto") == null );
        testCookBook.removeRecipe("rissoto");
        assertTrue(testCookBook.getRecipe("rissoto") == null );
    }

    //TODO
    @Test
    void getRecipes() throws DuplicateRecipeException, IllegalQuantityValueException, IllegalMeasureArgumentException {
        CookBook testCookBook = new CookBook("testCookBook");
        Recipe recipe = new Recipe("rissoto");
        recipe.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook.addRecipe(recipe);
        Recipe recipe2 = new Recipe("rissoto2");
        recipe2.addRecipeIngredient(new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Kilogram, 1));
        testCookBook.addRecipe(recipe2);
        Recipe recipe3 = new Recipe("rissoto3");
        recipe.addRecipeIngredient(new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Cup, 1));
        testCookBook.addRecipe(recipe3);

        assertEquals(testCookBook.getRecipes(Recipe::isVegetarian).size(),2);
        assertEquals(testCookBook.getRecipes(Recipe::isNutFree).size(),2);
        assertEquals(testCookBook.getRecipes(Recipe::isLactoseFree).size(),2);
        assertEquals(testCookBook.getRecipes((s) -> !(s.isNutFree())).values().toArray()[0], recipe );
    }

    @Test
    void getRecipes1() throws IllegalQuantityValueException, IllegalMeasureArgumentException, DuplicateRecipeException {
        CookBook testCookBook = new CookBook("testCookBook");
        Recipe recipe = new Recipe("rissoto");
        recipe.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook.addRecipe(recipe);
        Recipe recipe2 = new Recipe("rissoto2");
        recipe2.addRecipeIngredient(new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Kilogram, 1));
        testCookBook.addRecipe(recipe2);
        Recipe recipe3 = new Recipe("rissoto3");
        recipe3.addRecipeIngredient(new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Cup, 1));
        testCookBook.addRecipe(recipe3);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(Ingredient.Hazelnut);
        assertEquals(testCookBook.getRecipes(ingredientList).size(),1);
        assertEquals(testCookBook.getRecipes(ingredientList).values().toArray()[0], recipe);
        ingredientList.add(Ingredient.ChickenBreast);
        assertEquals(testCookBook.getRecipes(ingredientList).size(),2);
        ingredientList.add(Ingredient.Flour);
        assertEquals(testCookBook.getRecipes(ingredientList).size(),2);

        recipe2.addRecipeIngredient(new RecipeIngredient(Ingredient.OliveOil, VolumeMeasureUnit.TeaSpoon, 1));
        assertEquals(testCookBook.getRecipes(ingredientList).size(),1);

    }

    @Test
    void merge() throws IllegalQuantityValueException, IllegalMeasureArgumentException, DuplicateRecipeException, MergeSameNamedCookBooks {
        CookBook testCookBook = new CookBook("testCookBook");
        CookBook testCookBook2 = new CookBook("testCookBook3");
        CookBook testCookBook3 = new CookBook("testCookBook2");
        CookBook testCookBook4 = new CookBook("testCookBook");
        Recipe recipe = new Recipe("rissoto");
        recipe.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook.addRecipe(recipe);
        Recipe recipe2 = new Recipe("rissoto2");
        recipe2.addRecipeIngredient(new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Kilogram, 1));
        testCookBook2.addRecipe(recipe2);
        Recipe recipe3 = new Recipe("rissoto");
        recipe3.addRecipeIngredient(new RecipeIngredient(Ingredient.Butter, VolumeMeasureUnit.Gallon, 1));
        testCookBook3.addRecipe(recipe3);
        Recipe recipe4 = new Recipe("rissoto");
        recipe3.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook4.addRecipe(recipe4);

        testCookBook.merge(testCookBook2);
        testCookBook4.merge(testCookBook2);
        assertEquals(testCookBook,testCookBook4);
        assertEquals(testCookBook.getRecipe("rissoto"), recipe);
        assertEquals(testCookBook.getRecipe("rissoto2"), recipe2);
        assertThrows(MergeSameNamedCookBooks.class , ()-> testCookBook.merge(testCookBook4));
    }

    @Test
    void equals() throws DuplicateRecipeException, IllegalQuantityValueException, IllegalMeasureArgumentException {
        CookBook testCookBook = new CookBook("testCookBook");
        CookBook testCookBook2 = new CookBook("testCookBook");
        CookBook testCookBook3 = new CookBook("testCookBook2");
        CookBook testCookBook4 = new CookBook("testCookBook");
        Recipe recipe = new Recipe("rissoto");
        recipe.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook.addRecipe(recipe);
        Recipe recipe2 = new Recipe("rissoto2");
        recipe2.addRecipeIngredient(new RecipeIngredient(Ingredient.ChickenBreast, MassMeasureUnit.Kilogram, 1));
        testCookBook2.addRecipe(recipe2);
        Recipe recipe3 = new Recipe("rissoto");
        recipe3.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook3.addRecipe(recipe3);
        Recipe recipe4 = new Recipe("rissoto");
        recipe3.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 1));
        testCookBook4.addRecipe(recipe4);


        assertTrue(testCookBook.equals(testCookBook4));
        assertFalse(testCookBook.equals(testCookBook2));
        assertFalse(testCookBook.equals(testCookBook3));
        assertTrue(testCookBook.equals(testCookBook));
        assertFalse(testCookBook.equals(new Object()));
        assertFalse(testCookBook.equals(new CookBook("testCookBook")));
    }

    @Test
    void renameRecipe() throws IllegalQuantityValueException, IllegalMeasureArgumentException, DuplicateRecipeException, RecipeNotFoundException {
        CookBook testCookBook = new CookBook("testCookBook");
        Recipe recipe = new Recipe("rissoto");
        testCookBook.addRecipe(recipe);
        Recipe recipe2 = new Recipe("potatoes");
        testCookBook.addRecipe(recipe2);
        testCookBook.renameRecipe("rissoto", "superrissoto");
        assertEquals(testCookBook.getRecipe("superrissoto"), recipe);
        assertThrows(DuplicateRecipeException.class, ()-> testCookBook.renameRecipe("superrissoto", "potatoes"));
    }

}