package main.model.food;
/*
 * Simple Interface providing flexibility to main.model.recipe.Recipe::contains private
 * function, which now can receive lambda expression. In accordance to DRY method this interface spare
 * unnecessary code.
 */
public interface IIngredientChecker {
    /*
     * Method is used to check if Ingredient is specific kind of food (nut, meat etc...)
     */
    public boolean checkIfIs(Ingredient ingredient);
}

