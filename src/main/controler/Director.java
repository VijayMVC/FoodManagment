package main.controler;

import main.controler.execution.*;
import main.controler.parse.IParser;
import main.controler.parse.SimpleParser;
import main.model.book.CookBook;
import main.model.recipe.Recipe;
import main.model.recipe.RecipeIngredient;
import main.view.MessageViewer;
import main.view.ViewManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Director {
    public final IParser parser;
    private Map<String,CookBook> cookBooks;
    private ExecutionStrategy executionStrategy;
    private Object focusedObject; // context of Execution Strategy/
    private ViewManager viewManager;

    public Director(IParser parser) {
        this.parser = parser;
        focusedObject = null;
        viewManager = new ViewManager();
        executionStrategy = new UnfocusedExecution(this);
        cookBooks = new HashMap<>();
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

    public Map<String, CookBook> getCookBooks() {
        return cookBooks;
    }

    public void setCookBooks(Map<String, CookBook> cookBooks) {
        this.cookBooks = cookBooks;
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
