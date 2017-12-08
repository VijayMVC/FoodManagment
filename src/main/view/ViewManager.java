package main.view;

import main.model.book.CookBook;

import java.io.IOException;
import java.util.Map;

public class ViewManager {
    public void print(String a){
        System.out.println(a);
    }

    public void emptyComandLineMessage() {
        System.out.println(" ");
    }

    public void printHelp() {
        System.out.println("Available functions: " +
                "\n - /help" +
                "\n - /context" +
                "\n - /showBooks" +
                "\n - /selectBook BookName" +
                "\n - /importBooks filePath" +
                "\n - /quit");
    }

    public void printContext(Object focusedObject) {
        String objectClass = "Console";
        if(focusedObject != null)
            objectClass = focusedObject.getClass() + ", " + focusedObject.toString();
        System.out.println("Working in context of: " + objectClass  );
    }

    public void showBooks(Map<String, CookBook> cookBooks) {
        if(cookBooks.isEmpty())
            System.out.println("There are no available books");
        else{
            System.out.println("Available books:");
            for(CookBook cookBook: cookBooks.values())
                System.out.println("  * " + cookBook.toString());
        }
    }

    public void printWrongBookMessage(String message) {
        System.out.println("Selecting book failed, because " + message);
    }

    public void printWrongImportMessage(String message) {
        System.out.println("Importing books failed, because " + message);
    }
}
