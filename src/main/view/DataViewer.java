package main.view;

import main.model.book.CookBook;
import main.model.recipe.RecipeIngredient;

import java.util.List;
import java.util.Map;

public class DataViewer {

    public void showBooks(Map<String, CookBook> cookBooks) {
        if (cookBooks.isEmpty())
            System.out.println("There are no available books");
        else {
            System.out.println("Available books:");
            for (CookBook cookBook : cookBooks.values())
                System.out.println("  * " + cookBook.toString());
        }
    }

    public void showTableOfContents(CookBook focusedObject) {
        List<String> table = focusedObject.getTableOfContents();
        int i=1;
        System.out.println("CookBook: " + focusedObject.toString());
        for(String str:table){
            System.out.println(((Integer) i).toString() + ". " + str );
            i++;
        }
    }
    /*
    private void showRecipe(){
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
    }*/
}
