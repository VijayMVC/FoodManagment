package main.model.recipe;

import main.model.book.CookBook;
import main.model.book.DuplicateRecipeException;
import main.model.cookBookIO.IOManager;
import main.model.food.IIngredientChecker;
import main.model.food.Ingredient;
import main.model.units.MassMeasureUnit;

import java.io.IOException;
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

    public void addDirection(String direction){
        directions.add(direction);
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

    private boolean contains(IIngredientChecker checker){
        for(RecipeIngredient recipeIngredient : recipeIngredientList){
            if(checker.checkIfIs(recipeIngredient.getIngredient()))
                return true;
        }
        return false;
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

    public static void main (String args[]) throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        /*Recipe recipe = new Recipe("Masło");
        Recipe recipe2 = new Recipe("Adam");
        recipe.setCookingTime(Duration.ofMinutes(20));
        recipe.setPreparationTime(Duration.ofMinutes(40));
        recipe.addDirection("lel");
        recipe.addDirection("a potem kek");
        recipe.addDirection("a potem ni chu chu");
        recipe.addRecipeIngredient(new RecipeIngredient(Ingredient.Hazelnut, MassMeasureUnit.Kilogram, 7*0.125/4 ));
        CookBook cookBook = new CookBook("lolo");
        try {
            cookBook.addRecipe(recipe);
            cookBook.addRecipe(recipe2);
        } catch (DuplicateRecipeException e) {
            e.printStackTrace();
        }
        List<CookBook> llist = new ArrayList<CookBook>();
        llist.add(cookBook);
        try {
            IOManager.serialzie(llist,"/home/jakub/cookbook.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        try {
            List<CookBook> llist = IOManager.deserialzie("/home/jakub/cookbook.ser");
            CookBook a = llist.get(0);
            System.out.println(a.getRecipe("Masło").toString());
            System.out.println(a.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
