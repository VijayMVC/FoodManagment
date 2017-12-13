package main.controler.execution;

import main.controler.Director;

import java.util.List;

public class RecipeIngredientExecution extends UnfocusedExecution implements ExecutionStrategy  {
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
            case "ConvertTo":{
                break;
            }
            case "setQuantity":{

            }
            default :{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
            }

        }
    }


}
