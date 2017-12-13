package main.controler;

import main.controler.execution.*;
import main.controler.parse.IParser;
import main.controler.parse.SimpleParser;
import main.model.book.CookBook;
import main.model.collection.CookBookCollection;
import main.model.recipe.Recipe;
import main.model.recipe.RecipeIngredient;
import main.view.ViewManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Director {
    public final IParser parser;
    private CookBookCollection cookBookCollection;
    private ExecutionStrategy executionStrategy;
    private Object focusedObject; // context of Execution Strategy/
    private Object lastFocusedObject;
    private ViewManager viewManager;

    public Director(IParser parser) {
        this.parser = parser;
        focusedObject = null;
        viewManager = new ViewManager();
        executionStrategy = new UnfocusedExecution(this);
        cookBookCollection = new CookBookCollection();
    }

    public IParser getParser() {
        return parser;
    }

    public  ExecutionStrategy getExecutionStrategy() {
        return executionStrategy;
    }

    public Object getFocusedObject(){
        return focusedObject;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public CookBookCollection getCookBookCollection() {
        return cookBookCollection;
    }

    public Object getLastFocusedObject() {
        return lastFocusedObject;
    }

    public static void main(String[] args){
        Director director = new Director(new SimpleParser());

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String line = br.readLine();
                List<String> lines = director.getParser().parse(line);
                if(line.equals("quit") || line.equals("q") || line.equals("/q") || line.equals("/quit"))
                    return;
                else {
                    director.getExecutionStrategy().execute(lines);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFocusedObject(Object focusedObject) {
        this.lastFocusedObject = this.focusedObject;
        this.focusedObject = focusedObject;
        if(focusedObject instanceof CookBook)
            executionStrategy = new CookBookExecution(this);
        else if (focusedObject instanceof Recipe)
            executionStrategy = new RecipeExecution(this);
        else if(focusedObject instanceof RecipeIngredient)
            executionStrategy = new RecipeIngredientExecution(this);
        else
            executionStrategy = new UnfocusedExecution(this);
    }
}
