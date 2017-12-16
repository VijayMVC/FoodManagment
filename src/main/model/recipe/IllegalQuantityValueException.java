package main.model.recipe;

/**
 *  Exception should be thrown when provided quality is negative value
 */

public class IllegalQuantityValueException extends  Exception {
    public IllegalQuantityValueException() {
        super("Quantity is negative or zero, while it should be positive");
    }
}
