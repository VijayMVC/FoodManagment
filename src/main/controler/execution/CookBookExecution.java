package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.model.book.DuplicateRecipeException;
import main.model.collection.CookBookNotFoundException;
import main.model.collection.DuplicateCookBookException;
import main.model.food.Ingredient;
import main.model.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class CookBookExecution extends UnfocusedExecution implements IExecutionStrategy {
    public CookBookExecution(Director director) {
        super(director);
    }

    @Override
    protected void showHelp(){
        viewManager.helpViewer.printCookBookHelp();
    }

    @Override
    protected void executeContextCommand(List<String> commandLine) {
        switch (commandLine.get(0)) {
            case "showTableOfContents": {
                viewManager.dataViewer.showTableOfContents((CookBook) director.getFocusedObject());
                break;
            }
            case "setCookBookName": {
                setCookBookName(commandLine);
                break;
            }
            case "addRecipe": {
                addRecipe(commandLine);
                break;
            }
            case "removeRecipe": {
                removeRecipe(commandLine);
                break;
            }
            case "selectRecipe": {
                selectRecipe(commandLine);
                break;
            }
            case "showVegetarianMeals":{
                showVegetarianMeals();
                break;
            }
            case "showNutFreeMeals":{
                showNutFreeMeals();
                break;
            }
            case "showLactoseFreeMeals":{
                showLactoseFreeMeals();
                break;
            }
            case "showIngredients":{
                viewManager.dataViewer.showIngredients();
                break;
            }
            case "showRecipesDoableWith":{
                showRecipesDoableWith(commandLine);
                break;
            }
            default :{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
            }

        }
    }

    private void showRecipesDoableWith(List<String> commandLine) {
        try {
            commandLine.remove(0);
            List<Ingredient> ingredientList = new ArrayList<>();
            for (String str : commandLine) {
                ingredientList.add(Ingredient.valueOf(str));
            }
            CookBook temp = new CookBook("Doable Recipes",
                    ((CookBook)director.getFocusedObject()).getRecipes(ingredientList));
            viewManager.dataViewer.showTableOfContents(temp);

        } catch (IllegalArgumentException e) {
            viewManager.messageViewer.printErrorMessage("Failed to recognize Ingredient, check if you wrote proper arguments.");
        }
    }

    private void showLactoseFreeMeals() {
        CookBook cookBook = (CookBook) director.getFocusedObject();
        CookBook lactoseFree = new CookBook(cookBook.getCookBookName() +" Lactose free",
                cookBook.getRecipes(Recipe::isLactoseFree));
        viewManager.dataViewer.showTableOfContents(lactoseFree);
    }

    private void showNutFreeMeals() {
        CookBook cookBook = (CookBook) director.getFocusedObject();
        CookBook nutFree = new CookBook(cookBook.getCookBookName() +" nut free",
                cookBook.getRecipes(Recipe::isNutFree));
        viewManager.dataViewer.showTableOfContents(nutFree);
    }

    private void showVegetarianMeals() {
        CookBook cookBook = (CookBook) director.getFocusedObject();
        CookBook vegetarian = new CookBook(cookBook.getCookBookName() +" vegetarian",
                cookBook.getRecipes(Recipe::isVegetarian));
        viewManager.dataViewer.showTableOfContents(vegetarian);
    }

    private void removeRecipe(List<String> commandLine) {
        try{
            CookBook cookBook = (CookBook) director.getFocusedObject();
            cookBook.removeRecipe(commandLine.get(1));
        }  catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to remove Recipe, because name was not defined.");
        }
    }

    private void addRecipe(List<String> commandLine) {
        try {
            String name = commandLine.get(1);
            CookBook cookBook = (CookBook) director.getFocusedObject();
            cookBook.addRecipe(new Recipe(name));
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to add Recipe, because name was not defined.");
        } catch (DuplicateRecipeException e) {
            viewManager.messageViewer.printErrorMessage("Failed to add Recipe, because book with that name already exists.");

        }
    }

    private void setCookBookName(List<String> commandLine) {
        try {
            String oldName = ((CookBook) director.getFocusedObject()).getCookBookName();
            director.getCookBookCollection().renameCookBook(oldName, commandLine.get(1));
        } catch (CookBookNotFoundException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set CookBookName, because Cook Book Don't Exist" +
                    "in that collection");
        } catch (DuplicateCookBookException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set CookBookName, becauseCook Book with that name already" +
                    "exists in that collection");
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set CookBookName, because name was not defined.");
        }
    }

    private void selectRecipe(List<String> commandLine){
        try {
            String recipeName = commandLine.get(1);
            CookBook cookBook= (CookBook)director.getFocusedObject();
            Recipe recipe = cookBook.getRecipe(recipeName);
            if( recipe == null)
                viewManager.messageViewer.printErrorMessage(" Fail to select recipe because " +
                        " there is no such recipe," +
                        " check if you spelled name properly");
            else
                director.setFocusedObject(recipe);
        }
        catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Fail to select recipe because " +
                    "name was not defined.");
        }
    }
}
