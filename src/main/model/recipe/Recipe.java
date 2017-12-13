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

    public void setCookingTime(Duration cookingTime){
        this.cookingTime = cookingTime;
    }

    public void setPreparationTime(Duration preparationTime){
        this.preparationTime = preparationTime;
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

    @Override
    public String toString() {
        String recipeStr = "Recipe: " + this.recipeName + "\n\n";
        recipeStr += "Preparation time: " + preparationTime.toString() + "\n";
        recipeStr += "Cooking Time: " + cookingTime.toString() + "\n\n";
        recipeStr += "Ingriedients:\n";
        for (RecipeIngredient recipeIngredient : recipeIngredientList) {
            recipeStr += "\t* " + recipeIngredient.toString(true) + "\n";
        }
        recipeStr += "\nDirections:\n";
        int index = 1;
        for (String direction : directions) {
            recipeStr += "\t" + String.valueOf(index) + ". " + direction + "\n";
            index++;
        }
        if(tip!=null)
            recipeStr += "\nTip: " + this.tip;
        return recipeStr;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
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
