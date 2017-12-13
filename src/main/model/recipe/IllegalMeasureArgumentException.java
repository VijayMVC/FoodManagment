package main.model.recipe;

import main.model.food.Ingredient;
import main.model.units.IMeasurable;

public class IllegalMeasureArgumentException extends  Exception {

    public IllegalMeasureArgumentException() {
        super("Measure is not proper for that type of Ingredient");
    }

    public IllegalMeasureArgumentException(IMeasurable measureable, Ingredient ingredient) {
        super("Measure: " + measureable.toString() +" is not proper measure of: "
                + ingredient.toString());
    }
}
