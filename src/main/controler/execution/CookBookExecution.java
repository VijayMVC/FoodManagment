package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.view.ViewManager;

import java.util.List;

public class CookBookExecution extends UnfocusedExecution implements ExecutionStrategy {
    public CookBookExecution(Director director) {
        super(director);
    }

    @Override
    public void execute(List<String> commandLine) {
        super.execute(commandLine);
        if(!commandLine.isEmpty() && commandLine.get(0).equals("/help"))
            viewManager.helpViewer.printCookBookHelp();
    }


    @Override
    protected void executeContextCommand(List <String> commandLine){
        switch ( commandLine.get(0)){
            case "showTableOfContents":{
                viewManager.dataViewer.showTableOfContents((CookBook)director.getFocusedObject());
            }
        }
    }

}
