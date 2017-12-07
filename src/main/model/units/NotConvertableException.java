package main.model.units;

public class NotConvertableException extends Exception {
    // Parameterless Constructor
    public NotConvertableException() {}

    // Constructor that accepts a message
    public NotConvertableException(String message)
    {
        super(message);
    }
}
