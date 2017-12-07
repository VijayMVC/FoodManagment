package main.model.recipe;

public class IllegalQuantityValueException extends  Exception {
    public IllegalQuantityValueException() {
        super("Quantity is negative or zero, while it should be positive");
    }
}
