package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.model.book.DuplicateRecipeException;
import main.model.book.RecipeNotFoundException;
import main.model.collection.CookBookNotFoundException;
import main.model.collection.DuplicateCookBookException;
import main.model.recipe.Recipe;

import java.util.List;

public class RecipeExecution extends UnfocusedExecution implements ExecutionStrategy {
    public RecipeExecution(Director director) {
        super(director);
    }

    @Override
    public void execute(List<String> commandLine) {
        super.execute(commandLine);
        if (!commandLine.isEmpty() && commandLine.get(0).equals("/help"))
            viewManager.helpViewer.printRecipeHelp();
    }

    protected void executeContextCommand(List<String> commandLine) {
        switch (commandLine.get(0)) {
            case "setRecipeName":{
                setRecipeName(commandLine);
                 break;
            }
            case "addDirection":{
                addDirection(commandLine);
                break;
            }
            case "removeDirection":{
                removeDirection(commandLine);
                break;
            }
            default :{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
            }

        }
    }

    private void removeDirection(List<String> commandLine) {
        try {
            Recipe recipe = (Recipe) director.getFocusedObject();
            recipe.removeDirection(Integer.valueOf(commandLine.get(1)));
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to remove Direction, because index was not defined.");
        } catch(NumberFormatException e ){
            viewManager.messageViewer.printErrorMessage("Failed to remove Direction, because index is not a number.");
        }
    }

    private void addDirection(List<String> commandLine) {
        Recipe recipe = (Recipe) director.getFocusedObject();
        commandLine.remove(0);
        if(commandLine.isEmpty())
            viewManager.messageViewer.printErrorMessage("Failed to ad Direction , because direction was not defined.");
        String direction="";
        for(String str:commandLine)
            direction = direction + " " + str;
        recipe.addDirection(direction);
    }
    private void setRecipeName(List<String> commandLine) {
        try {
            String oldName = ((Recipe) director.getFocusedObject()).getRecipeName();
            CookBook cookBook = (CookBook) director.getLastFocusedObject();
            cookBook.renameRecipe(oldName,commandLine.get(1));
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set recipeName , because name was not defined.");
        } catch (DuplicateRecipeException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set recipeName, because recipe with that name already" +
                    "exists in that cookbook");
        } catch (RecipeNotFoundException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set recipeName, because Cook Book Don't Exist" +
                    "in that cookbook");
        }
    }


}
