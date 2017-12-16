package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.model.book.DuplicateRecipeException;
import main.model.book.RecipeNotFoundException;
import main.model.food.Ingredient;
import main.model.recipe.IllegalMeasureArgumentException;
import main.model.recipe.IllegalQuantityValueException;
import main.model.recipe.Recipe;
import main.model.recipe.RecipeIngredient;
import main.model.units.IMeasurable;
import main.model.units.MassMeasureUnit;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;

import java.time.Duration;
import java.util.List;

public class RecipeExecution extends UnfocusedExecution implements IExecutionStrategy {
    public RecipeExecution(Director director) {
        super(director);
    }

    @Override
    protected void showHelp(){
        viewManager.helpViewer.printRecipeHelp();
    }

    @Override
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
            case "showRecipe":{
                viewManager.dataViewer.showRecipe((Recipe) director.getFocusedObject());
                break;
            }
            case "addIngredient":{
                addIngredient(commandLine);
                break;
            }
            case "removeIngredient":{
                removeIngredient(commandLine);
                break;
            }
            case "setCookingTime":{
                setCookingTime(commandLine);
                break;
            }
            case "setPreparationTime":{
                setPreparationTime(commandLine);
                break;
            }
            case "setTip":{
                setTip(commandLine);
                break;
            }
            case "selectIngredient":{
                selectIngredient(commandLine);
                break;
            }
            case "return":{
                returnToCookBook();
                break;
            }
            case "showMeasures":{
                viewManager.dataViewer.showMeasures();
                break;
            }
            case "showIngredients":{
                viewManager.dataViewer.showIngredients();
                break;
            }
            default :{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
            }

        }
    }

    private void selectIngredient(List<String> commandLine) {
        try{
            Recipe recipe = (Recipe) director.getFocusedObject();
            int index = Integer.valueOf(commandLine.get(1));
            director.setFocusedObject(recipe.getRecipeIngredientList().get(index-1));
        }catch(IndexOutOfBoundsException e ){
            viewManager.messageViewer.printErrorMessage("Failed to select Ingredient, Ingredient with that index does not exist");
        }catch(NumberFormatException e ){
            viewManager.messageViewer.printErrorMessage("Failed to select Ingredient, because quantity is not a number.");
        } catch( IllegalArgumentException e) {
            viewManager.messageViewer.printErrorMessage("Failed to select Ingredient, check if you wrote proper arguments.");
        }
    }

    private void returnToCookBook() {
        director.setFocusedObject(director.getParentObject());
    }

    private void setTip(List<String> commandLine) {
        Recipe recipe = (Recipe) director.getFocusedObject();
        commandLine.remove(0);
        if(commandLine.isEmpty())
            viewManager.messageViewer.printErrorMessage("Failed to ad Tip , because tip was not defined.");
        String tip="";
        for(String str:commandLine)
            tip = tip + " " + str;
        recipe.setTip(tip);
    }

    private void setPreparationTime(List<String> commandLine) {
        Duration duration = getDuration(commandLine);
        if(duration!=null)
            ((Recipe)director.getFocusedObject()).setPreparationTime(duration);
    }

    private void setCookingTime(List<String> commandLine) {
        Duration duration = getDuration(commandLine);
        if(duration!=null)
            ((Recipe)director.getFocusedObject()).setCookingTime(duration);
    }

    private Duration getDuration(List<String> commandLine) {
        try{
            int hours = Integer.valueOf(commandLine.get(1));
            int minutes = Integer.valueOf(commandLine.get(2));
            Duration duration = Duration.ofMinutes(hours*60 + minutes);
            return duration;
        }catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set Time, because of to few arguments use /help" +
                    "to get more information about arguments.");
        }catch(NumberFormatException e ){
            viewManager.messageViewer.printErrorMessage("Failed to remove Time, because argument is not a number.");
        }
        return null;
    }

    private void removeIngredient(List<String> commandLine) {
        try {
            Recipe recipe = (Recipe) director.getFocusedObject();
            recipe.removeIngredient(Integer.valueOf(commandLine.get(1))-1);
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to remove Ingredient, because proper index was not defined.");
        } catch(NumberFormatException e ){
            viewManager.messageViewer.printErrorMessage("Failed to remove Ingredient, because index is not a number.");
        }
    }

    private void addIngredient(List<String> commandLine) {
        try {
            Recipe recipe = (Recipe) director.getFocusedObject();
            String ingredientName = commandLine.get(3);
            Ingredient ingredient = Ingredient.valueOf(ingredientName);
            String measureName = commandLine.get(2);
            IMeasurable measure;
            if(MassMeasureUnit.isValueOf(measureName))
                measure = MassMeasureUnit.valueOf(measureName);
            else if(VolumeMeasureUnit.isValueOf(measureName))
                measure = VolumeMeasureUnit.valueOf(measureName);
            else
                measure = OtherMeasureUnit.valueOf(measureName);
            double quantity = Double.valueOf(commandLine.get(1));
            RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient,measure,quantity);
            recipe.addRecipeIngredient(recipeIngredient);
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to add Ingredient, because of too few arguments.");
        } catch(NumberFormatException e ){
            viewManager.messageViewer.printErrorMessage("Failed to add Ingredient, because quantity is not a number.");
        } catch( IllegalArgumentException e){
            viewManager.messageViewer.printErrorMessage("Failed to add Ingredient, check if you wrote proper arguments.");
        } catch (IllegalMeasureArgumentException e) {
            viewManager.messageViewer.printErrorMessage(e.getMessage());
        } catch (IllegalQuantityValueException e) {
            viewManager.messageViewer.printErrorMessage("Failed to add Ingredient " + e.getMessage());
        }
    }

    private void removeDirection(List<String> commandLine) {
        try {
            Recipe recipe = (Recipe) director.getFocusedObject();
            recipe.removeDirection(Integer.valueOf(commandLine.get(1))-1);
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to remove Direction, because proper index was not defined.");
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
            CookBook cookBook = (CookBook) director.getParentObject();
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
