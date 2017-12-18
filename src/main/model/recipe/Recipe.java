package main.model.recipe;
import main.model.book.CookBook;
import main.model.food.IIngredientChecker;
import main.model.food.Ingredient;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents recipe, containing ingredients together with directions and preparation time
 */
public class Recipe implements Serializable {
    private String recipeName;
    private List<RecipeIngredient> recipeIngredientList;
    private List<String> directions;
    private Duration preparationTime;
    private Duration cookingTime;
    private String tip;

    /**
     *
     * @param recipeName name of Recipe
     * @param recipeIngredientList ordered ingredient list
     * @param directions ordered list of directions
     * @param preparationTime
     * @param CookingTime
     * @param Tip short note about preparation
     * @throws IllegalArgumentException thrown when null values are provided
     */
    public Recipe(String recipeName, List<RecipeIngredient>recipeIngredientList,
                  List<String> directions, Duration preparationTime, Duration CookingTime,
                  String Tip ) throws IllegalArgumentException {
        this.setCookingTime(CookingTime);
        this.setPreparationTime(preparationTime);
        if(recipeIngredientList == null || directions == null )
            throw new IllegalArgumentException("can't create object with null as a List");
        this.directions = directions;
        this.recipeIngredientList = recipeIngredientList;
        this.recipeName =recipeName;
        this.tip = tip;
    }

    public Recipe(String recipeName, List<RecipeIngredient>recipeIngredientList,
                  List<String> Directions,
                  Duration preparationTime, Duration CookingTime ){
        this(recipeName, recipeIngredientList, Directions, preparationTime, CookingTime, null);
    }

    public Recipe(String recipeName){
        this(recipeName, new ArrayList<RecipeIngredient>(), new ArrayList<String>(),
                null, null, null);
    }



    /**
     *
     * @param recipeIngredient ingredient to add
     */
    public void addRecipeIngredient(RecipeIngredient recipeIngredient){
        recipeIngredientList.add(recipeIngredient);
    }

    /**
     *
     * @param index index of ingredient in ordered Ingredient List
     * @throws IndexOutOfBoundsException
     */
    public void removeIngredient(int index) throws IndexOutOfBoundsException {
        if(recipeIngredientList.size() <= index || index < 0)
            throw new IndexOutOfBoundsException();
        else
            recipeIngredientList.remove(index);
    }

    /**
     *
     * @param direction direction to add
     */
    public void addDirection(String direction){
        directions.add(direction);
    }

    /**
     *
     * @param index index of Direction in ordered Direction List
     * @throws IndexOutOfBoundsException
     */
    public void removeDirection(int index) throws IndexOutOfBoundsException {
        if(directions.size() <= index || index < 0)
            throw new IndexOutOfBoundsException();
        else
            directions.remove(index);
    }

    /**
     * describes if recipe contain only vegetarian ingredients
     *
     * @return true if do not contain meat, otherwise false
     */
    public boolean isVegetarian(){
        return !this.contains(Ingredient::isMeat);
    }

    /**
     * describes if recipe do not contain nuts
     *
     * @return true if do not contain nuts, otherwise false
     */
    public boolean isNutFree(){
        return !this.contains(Ingredient::isNut);
    }

    /**
     * describes if recipe do not contain diary products
     *
     * @return true if do not contain dairy product, otherwise false
     */
    public boolean isLactoseFree(){
        return !this.contains(Ingredient::isDairyProduct);
    }

    /**
     * describes if recipe can be made with available Ingredients
     *
     * @param ingredientList List of available Ingredients
     * @return true if can be made with available ingredients, false otherwise
     */
    public boolean canBeMadeWith(List<Ingredient> ingredientList){
        for(RecipeIngredient recipeIngredient: recipeIngredientList){
            boolean isInList = false;
            for(Ingredient ingredient: ingredientList){
                if(recipeIngredient.getIngredient() == ingredient) {
                    isInList = true;
                    break;
                }
            }
            if(!isInList)
                return false;
        }
        return true;
    }



    /**
     *
     * @param cookingTime new cooking time
     */
    public void setCookingTime(Duration cookingTime){
        this.cookingTime = cookingTime;
    }

    /**
     *
     * @param preparationTime new preparation time
     */
    public void setPreparationTime(Duration preparationTime){
        this.preparationTime = preparationTime;
    }

    /**
     *
     * @param recipeName new recipe name
     */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     * remove old tip and set new one.
     *
     * @param tip new tip
     */
    public void setTip(String tip){
        this.tip = tip;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Duration getPreparationTime() {
        return preparationTime;
    }

    public Duration getCookingTime() {
        return cookingTime;
    }

    public List<RecipeIngredient> getRecipeIngredientList() {
        return recipeIngredientList;
    }

    public List<String> getDirections() {
        return directions;
    }

    public String getTip() {
        return tip;
    }




    @Override
    public String toString() {
        return recipeName;
    }

    /**
     * Two Recipes are equal if and only if they have same names and ingredients
     *
     * @param obj object to compare to
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof Recipe))
            return false;
        if(!this.getRecipeName().equals(((Recipe)obj).getRecipeName()))
            return false;
        if(this.recipeIngredientList.size() != ((Recipe) obj).recipeIngredientList.size())
            return false;
        for(RecipeIngredient recipeIngredient :((Recipe) obj).recipeIngredientList){
            if(!this.recipeIngredientList.contains(recipeIngredient))
                return false;
        }
        return true;
    }



    /**
     * support method helpful in declaring other methods like isNutFree()
     *
     * @param checker instance of IIngredient Checker or lambda expression
     * @return true if checker find one fitting Ingredient, otherwise false
     */
    private boolean contains(IIngredientChecker checker){
        for(RecipeIngredient recipeIngredient : recipeIngredientList){
            if(checker.checkIfIs(recipeIngredient.getIngredient()))
                return true;
        }
        return false;
    }

    private boolean contains2(IIngredientChecker checker){
        return recipeIngredientList.stream()
                .anyMatch(l->checker.checkIfIs(l.getIngredient()));
    }



}