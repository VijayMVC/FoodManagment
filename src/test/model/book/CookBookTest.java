package test.model.book;

import main.model.book.CookBook;
import main.model.book.DuplicateRecipeException;
import main.model.recipe.Recipe;
import org.junit.jupiter.api.Test;

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
    void getRecipes() {
    }

    @Test
    void getRecipes1() {
    }

    @Test
    void merge() {
    }

    @Test
    void equals() {
    }

    @Test
    void renameRecipe() {
    }

}