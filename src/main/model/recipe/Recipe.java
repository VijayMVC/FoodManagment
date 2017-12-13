package main.model.recipe;
import main.model.book.CookBook;
import main.model.food.IIngredientChecker;
import main.model.food.Ingredient;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    private String recipeName;
    private List<RecipeIngredient> recipeIngredientList;
    private List<String> directions;
    private Duration preparationTime;
    private Duration cookingTime;
    private String tip;

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

    public void addRecipeIngredient(RecipeIngredient recipeIngredient){
        recipeIngredientList.add(recipeIngredient);
    }

    public void removeIngredient(int index) throws IndexOutOfBoundsException {
        if(recipeIngredientList.size() <= index || index < 0)
            throw new IndexOutOfBoundsException();
        else
            recipeIngredientList.remove(index);
    }

    public void addDirection(String direction){
        directions.add(direction);
    }

    public void removeDirection(int index) throws IndexOutOfBoundsException {
        if(directions.size() <= index || index < 0)
            throw new IndexOutOfBoundsException();
        else
            directions.remove(index);
    }

    public boolean isVegetarian(){
        return !this.contains(Ingredient::isMeat);
    }

    public boolean isNutFree(){
        return !this.contains(Ingredient::isNut);
    }

    public boolean isLactoseFree(){
        return !this.contains(Ingredient::isDairyProduct);
    }

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



    public void setCookingTime(Duration cookingTime){
        this.cookingTime = cookingTime;
    }

    public void setPreparationTime(Duration preparationTime){
        this.preparationTime = preparationTime;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

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



    private boolean contains(IIngredientChecker checker){
        for(RecipeIngredient recipeIngredient : recipeIngredientList){
            if(checker.checkIfIs(recipeIngredient.getIngredient()))
                return true;
        }
        return false;
    }

}