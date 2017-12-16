package main.view;

import main.model.book.CookBook;
import main.model.food.Ingredient;
import main.model.recipe.Recipe;
import main.model.recipe.RecipeIngredient;
import main.model.units.MassMeasureUnit;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;

import java.util.List;
import java.util.Map;

public class DataViewer {

    public void showBooks(Map<String, CookBook> cookBooks) {
        int i=1;
        if (cookBooks.isEmpty())
            System.out.println("There are no available books");
        else {
            System.out.println("Available books:");
            for (CookBook cookBook : cookBooks.values())
                System.out.println(((Integer) i).toString() + ". " + cookBook.toString());
            i++;
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

    public void showRecipe(Recipe recipe){
        String recipeStr = "Recipe: " + recipe.getRecipeName() + "\n\n";
        String preparationTime = "not known";
        if(recipe.getPreparationTime() != null) {
            preparationTime = recipe.getPreparationTime().toString();
            preparationTime= preparationTime.substring(2,preparationTime.length());
        }
        String cookingTime = "not known";
        if(recipe.getCookingTime() != null) {
            cookingTime = recipe.getCookingTime().toString();
            cookingTime= cookingTime.substring(2,cookingTime.length());
        }
        recipeStr += "Preparation time: " + preparationTime + "\n";
        recipeStr += "Cooking Time: " + cookingTime + "\n\n";
        recipeStr += "Ingredients:\n";
        int index = 1;
        for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredientList()) {
            recipeStr += "\t " + String.valueOf(index) + ". " + recipeIngredient.toString(true) + "\n";
            index++;
        }
        recipeStr += "\nDirections:\n";
        index = 1;
        for (String direction : recipe.getDirections()) {
            recipeStr += "\t" + String.valueOf(index) + ". " + direction + "\n";
            index++;
        }
        if(recipe.getTip()!=null)
            recipeStr += "\nTip: " + recipe.getTip();
        System.out.println(recipeStr);
    }

    public void showMeasures() {
        System.out.println("Available mass measure units: ");
        for(MassMeasureUnit measureUnit:MassMeasureUnit.values()){
            System.out.println("* " + measureUnit.toString());
        }
        System.out.println("Available volume measure units: ");
        for(VolumeMeasureUnit volumeMeasureUnit:VolumeMeasureUnit.values()){
            System.out.println("* " + volumeMeasureUnit.toString());
        }
        System.out.println("Available other measure units: ");
        for(OtherMeasureUnit otherMeasureUnit:OtherMeasureUnit.values()){
            System.out.println("* " + otherMeasureUnit.toString());
        }
    }

    public void showIngredients() {
        System.out.println("Available ingredients: ");
        for(Ingredient ingredient:Ingredient.values()){
            System.out.println("* " + ingredient.toString());
        }
    }
}
