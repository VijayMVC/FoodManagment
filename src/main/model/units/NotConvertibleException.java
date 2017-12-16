package main.model.units;

/**
 * Thrown when harassing rule: Ingredients should have logically appropriate measure units.
 */

public class NotConvertibleException extends Exception {
    // Parameterless Constructor
    public NotConvertibleException() {}

    // Constructor that accepts a message
    public NotConvertibleException(String message)
    {
        super(message);
    }
}
