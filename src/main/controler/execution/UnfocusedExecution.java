package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.model.collection.*;
import main.view.ViewManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;


public class UnfocusedExecution implements IExecutionStrategy {
    protected ViewManager viewManager;
    protected Director director;


    public UnfocusedExecution(Director director){
        this.viewManager = director.getViewManager();
        this.director = director;
    }

    @Override
    public void execute(List<String> commandLine) {
        if(commandLine.isEmpty()){
            viewManager.messageViewer.printEmptyCommandLineMessage();
        }
        else if (commandLine.get(0).substring(0,1).equals("/")){
            executeConsoleCommand(commandLine);
        }
        else executeContextCommand(commandLine);
    }
    /*
     * All commands which, start with '/' are assumed to be a ConsoleCommand and won't be associate with
     * any context.
    */
    private void executeConsoleCommand(List<String> commandLine) {
        switch(commandLine.get(0)){
            case "/help":{
                viewManager.helpViewer.printHelp();
                showHelp();
                break;
            }
            case "/context":{
                viewManager.messageViewer.printContext(director.getFocusedObject());
                break;
            }
            case "/showBooks":{
                viewManager.dataViewer.showBooks(director.getCookBookCollection().getTableOfContents());
                break;
            }
            case "/selectBook":{
                selectBook(commandLine);
                break;
            }
            case "/import":{
                importBook(commandLine);
                break;
            }
            case "/export":{
                exportBook(commandLine);
                break;
            }
            case "/newBook":{
                createNewBook(commandLine);
                break;
            }
            case "/setCollectionName":{
                setCollectionName(commandLine);
                break;
            }
            case "/showCollectionName":{
                viewManager.messageViewer.printMessage("Collection name:" + director.getCookBookCollection().getCollectionName());
                break;
            }
            case "/unfocus":{
                director.setFocusedObject(null);
                break;
            }
            default:{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
                break;
            }
        }
    }


    protected void showHelp() {
        viewManager.helpViewer.printUnfocusedHelp();
    }

    protected void executeContextCommand(List<String> commandLine) {
        switch (commandLine.get(0)) {
            case "removeCookBook": {
                removeCookBook(commandLine);
                break;
            }
            default:{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
            }
        }
    }

    private void removeCookBook(List<String> commandLine) {
        try{
            director.getCookBookCollection().removeCookBook(commandLine.get(1));
        }catch(IndexOutOfBoundsException e){
            viewManager.messageViewer.printErrorMessage("Failed to remove book because name was not provided.");
        }
    }

    private void setCollectionName(List<String> commandLine) {
        try{
            director.getCookBookCollection().setCollectionName(commandLine.get(1));
        }
        catch(IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to set CollectionName because name was not provided.");
        }
    }

    private void createNewBook(List<String> commandLine) {
        try {
            String name = commandLine.get(1);
            director.getCookBookCollection().addCookBook(new CookBook(name));
        }
        catch(IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to create book, name was not defined.");
        } catch (DuplicateCookBookException e) {
            viewManager.messageViewer.printErrorMessage("Failed to create book, book with that name already exists.");
        }
    }

    private void exportBook(List<String> commandLine) {
        try {
            String path = commandLine.get(1);
            director.getCookBookCollection().serialize(path);
            viewManager.messageViewer.printMessage("Successfully exported books to " + path);
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to export Book because name was not defined.");
        } catch (IOException e) {
            viewManager.messageViewer.printErrorMessage("Failed to export Book because of wrong path.");
        } catch (UnnamedCollectionException e) {
            viewManager.messageViewer.printErrorMessage("Failed to export Book because current collection " +
                    "you want to export is unnamed" +
                    " use command /setCollectionName and try again.");
        }
    }

    private void importBook(List<String> commandLine) {
        try{
            String path = commandLine.get(1);
            CookBookCollection cookBookCollection = CookBookCollection.deserialize(path);
            CookBookCollection currentCookBookCollection =director.getCookBookCollection();
            currentCookBookCollection.merge(cookBookCollection);
            viewManager.messageViewer.printMessage("successfully imported books from " + path);
        }
        catch(IndexOutOfBoundsException e){
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " +
                    "path was not given.");
        }catch(EOFException e ){
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " +
                    "program found end of file. Make sure that selected directory is " +
                    "not an empty file.");
        } catch(StreamCorruptedException e ){
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " +
                    "wrong type of stream. File may have wrong type, make sure " +
                    "the path point at \".ser\" file.");
        } catch(InvalidClassException e){
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " +
                    "obsolete version of book file.");
        } catch (IOException e) {
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because" +
                    " of wrong path.");
        } catch (ClassNotFoundException e) {
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " +
                    "CookBook is not defined.");
        } catch (MergeSameNamedCollectionsException e) {
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " + e.getMessage());
        }
    }

    private void selectBook(List<String> commandLine){
       try {
           String cookBookName = commandLine.get(1);
           CookBook cookBook = director.getCookBookCollection().getCookBook(cookBookName);
           director.setFocusedObject(cookBook);
       }
       catch (IndexOutOfBoundsException e) {
           viewManager.messageViewer.printErrorMessage("Failed to select Book because " +
                   "name was not defined.");
       } catch (CookBookNotFoundException e) {
           viewManager.messageViewer.printErrorMessage(" Failed to select Book because " +
                   " there is no such book," +
                   " check if you spelled name properly");
       }
    }
}
