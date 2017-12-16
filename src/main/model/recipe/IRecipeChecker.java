package main.model.recipe;

import main.model.food.Ingredient;

import java.util.List;

    /**
     * Simple Interface providing flexibility to CookBook getRecipes private function
     * function, which now can receive lambda expression. In accordance to DRY method this interface spare
     * unnecessary code.
     */
public interface IRecipeChecker {

        /**
         * Method is used to check if Recipe has specific values
         *
         * @param recipe Recipe to search in
         * @return
         */
         boolean check(Recipe recipe);

}
