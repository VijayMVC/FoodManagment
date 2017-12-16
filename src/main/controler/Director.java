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
    private final IParser parser;
    private CookBookCollection cookBookCollection;
    private ViewManager viewManager;

    private IExecutionStrategy IExecutionStrategy;
    private Object focusedObject; // context of Execution Strategy/
    private Object parentObject;
    private CookBook lastFocusedCookBook;

    public Director(IParser parser) {
        this.parser = parser;
        focusedObject = null;
        viewManager = new ViewManager();
        IExecutionStrategy = new UnfocusedExecution(this);
        cookBookCollection = new CookBookCollection();
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

    public Object getParentObject() {
        return parentObject;
    }

    public void setFocusedObject(Object focusedObject) {
        this.parentObject = this.focusedObject;
        this.focusedObject = focusedObject;
        if(focusedObject instanceof CookBook) {
            IExecutionStrategy = new CookBookExecution(this);
            this.lastFocusedCookBook = (CookBook) focusedObject;
        }else if (focusedObject instanceof Recipe){
            IExecutionStrategy = new RecipeExecution(this);
            if(! (parentObject instanceof CookBook))
                parentObject = this.lastFocusedCookBook;
        }
        else if(focusedObject instanceof RecipeIngredient)
            IExecutionStrategy = new RecipeIngredientExecution(this);
        else
            IExecutionStrategy = new UnfocusedExecution(this);
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
                    director.getIExecutionStrategy().execute(lines);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IParser getParser() {
        return parser;
    }

    private IExecutionStrategy getIExecutionStrategy() {
        return IExecutionStrategy;
    }
}
