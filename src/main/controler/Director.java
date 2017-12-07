package main.controler;

import main.controler.execution.ExecutionStrategy;
import main.controler.execution.UnfocusedExecution;
import main.controler.parse.IParser;
import main.model.book.CookBook;

import java.util.Map;

public class Director {
    public final IParser parser;
    private Map<String,CookBook> cookBooks;
    private ExecutionStrategy executionStrategy;
    private Object foucsedObject;

    public Director(IParser parser) {
        this.parser = parser;
        executionStrategy = new UnfocusedExecution();
        cookBooks = null;
        foucsedObject = null;
    }


    public static void main(String[] args){
        int i=0;
        while(true) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
