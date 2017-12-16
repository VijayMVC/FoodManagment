package main.model.book;

/**
 * thrown when harassing rule: Recipe should be unique and there should not be duplicates in cook book.
 */
public class DuplicateRecipeException extends Exception {
    public DuplicateRecipeException() {
        super("Provided recipe is a duplicate of existing recipe or have the same name");
    }
}
