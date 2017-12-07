package main.model.recipe;
/*
     * Simple Interface providing flexibility to CookBook getRecipes private function
     * function, which now can receive lambda expression. In accordance to DRY method this interface spare
     * unnecessary code.
     */
public interface IRecipeChecker {
        /*
         * Method is used to check if Recipe has specific values
         */
        public boolean check(Recipe recipe);
}
