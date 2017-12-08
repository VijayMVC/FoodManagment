package main.controler.execution;

import main.controler.Director;

public class RecipeIngredientExecution extends UnfocusedExecution implements ExecutionStrategy  {
    public RecipeIngredientExecution(Director director) {
        super(director);
    }
}
