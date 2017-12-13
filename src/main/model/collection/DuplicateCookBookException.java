package main.model.collection;

public class DuplicateCookBookException extends Throwable {
    public DuplicateCookBookException() {
        super("Provided CookBook is a duplicate of existing CookBook");
    }
}
