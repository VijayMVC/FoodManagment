package main.model.recipe;

import main.model.food.Ingredient;
import main.model.units.IMeasurable;

/**
 *  Exception should be thrown when provided measure unit is no acceptable
 *  e.g. when trying to apply Volume Measure to chicken breast, what is logically not acceptable
 */
public class IllegalMeasureArgumentException extends  Exception {

    public IllegalMeasureArgumentException() {
        super("Measure is not proper for that type of Ingredient");
    }

    public IllegalMeasureArgumentException(IMeasurable measureable, Ingredient ingredient) {
        super("Measure: " + measureable.toString() +" is not proper measure of: "
                + ingredient.toString());
    }
}
