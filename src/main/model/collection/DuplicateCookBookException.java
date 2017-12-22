package main.model.collection;

/**
 * thrown when harassing rule: CookBooks should be unique and there should not be duplicates in collection.
 *
 */
public class DuplicateCookBookException extends Exception {
    public DuplicateCookBookException() {
        super("Provided CookBook is a duplicate of existing CookBook");
    }
}
