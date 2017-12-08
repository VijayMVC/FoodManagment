package main.controler.execution;

import main.controler.Director;

public class CookBookExecution extends UnfocusedExecution implements ExecutionStrategy {
    public CookBookExecution(Director director) {
        super(director);
    }
}
