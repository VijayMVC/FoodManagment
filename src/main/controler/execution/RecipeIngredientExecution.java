package main.controler.execution;

import main.controler.Director;
import main.model.recipe.IllegalQuantityValueException;
import main.model.recipe.RecipeIngredient;
import main.model.units.*;

import java.util.List;

public class RecipeIngredientExecution extends UnfocusedExecution implements IExecutionStrategy {
    public RecipeIngredientExecution(Director director) {
        super(director);
    }

    @Override
    public void execute(List<String> commandLine) {
        super.execute(commandLine);
        if (!commandLine.isEmpty() && commandLine.get(0).equals("/help"))
            viewManager.helpViewer.printRecipeIngredientHelp();
    }

    protected void executeContextCommand(List<String> commandLine) {
        switch (commandLine.get(0)) {
            case "convertTo":{
                convertTo(commandLine);
                break;
            }
            case "setQuantity":{
                setQuantity(commandLine);
                break;
            }
            case "return":{
                returnToRecipe();
                break;
            }
            default :{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
            }

        }
    }

    private void returnToRecipe() {
        director.setFocusedObject(director.getParentObject());
    }

    private void setQuantity(List<String> commandLine) {
        try{
            double quantity = Double.valueOf(commandLine.get(1));
            ((RecipeIngredient)director.getFocusedObject()).setQuantity(quantity);
        }catch(IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set quantity,quantity was not provided.");
        }catch( IllegalArgumentException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set quantity, check if you wrote proper arguments.");
        } catch (IllegalQuantityValueException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set quantity,quantity must be not negative value");
        }
    }

    private void convertTo(List<String> commandLine) {
        try{
            RecipeIngredient recipeIngredient= (RecipeIngredient)director.getFocusedObject();
            String measureName = commandLine.get(1);
            IMeasurable measure;
            if(MassMeasureUnit.isValueOf(measureName))
                measure = MassMeasureUnit.valueOf(measureName);
            else if(VolumeMeasureUnit.isValueOf(measureName))
                measure = VolumeMeasureUnit.valueOf(measureName);
            else
                measure = OtherMeasureUnit.valueOf(measureName);
            recipeIngredient.convertToMeasureUnit(measure);

        }catch(IndexOutOfBoundsException e){
            viewManager.messageViewer.printErrorMessage("Failed to convert measure, measure was not provided.");
        }catch( IllegalArgumentException e) {
            viewManager.messageViewer.printErrorMessage("Failed to convert measure, check if you wrote proper arguments.");
        }catch (NotConvertibleException e) {
            viewManager.messageViewer.printErrorMessage(e.getMessage());
        }

    }


}
