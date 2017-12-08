package main.controler.execution;

import main.controler.Director;

public class RecipeExecution extends UnfocusedExecution implements ExecutionStrategy {
    public RecipeExecution(Director director) {
        super(director);
    }
}
