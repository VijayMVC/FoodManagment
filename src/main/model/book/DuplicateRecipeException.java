package main.model.book;

public class DuplicateRecipeException extends Exception {
    public DuplicateRecipeException() {
        super("Provided recipe is a duplicate of existing recipe or have the same name");
    }
}
