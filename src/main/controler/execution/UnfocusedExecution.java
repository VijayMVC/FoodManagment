package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.model.collection.CookBookCollection;
import main.model.collection.MergeSameNamedCollectionsException;
import main.model.collection.UnnamedCollectionException;
import main.view.ViewManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;


public class UnfocusedExecution implements ExecutionStrategy {
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
                break;
            }
            case "/context":{
                viewManager.messageViewer.printContext(director.getFocusedObject());
                break;
            }
            case "/showBooks":{
                viewManager.dataViewer.showBooks(director.getCookBookCollection().getCookBooks());
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
            default:{
                viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
                        " available commands");
                break;
            }
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

    protected void executeContextCommand(List<String> commandLine){viewManager.messageViewer.printErrorMessage("Wrong command use /help to get" +
            " available commands"); }

    private void createNewBook(List<String> commandLine) {
        try {
            String name = commandLine.get(1);
            if(director.getCookBookCollection().getCookBooks().get(name) != null) {
                viewManager.messageViewer.printErrorMessage("Failed to create book, book with that name already exists.");
            }
            else{
                director.getCookBookCollection().getCookBooks().put(name, new CookBook(name));
            }
        }
        catch(IndexOutOfBoundsException e) {
            viewManager.messageViewer.printErrorMessage("Failed to create book, name was not defined.");
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
            viewManager.messageViewer.printErrorMessage("Fail to import cook book because " +
                    " current cookBookCollection name and name of" +
                    "\n imported collection are the same. This operation is forbidden, because of safety measures," +
                    "\n make sure if you want continue operation. To import this collection change one of collection names");
        }
    }

    private void selectBook(List<String> commandLine){
       try {
           String cookBookName = commandLine.get(1);
           Map<String,CookBook> cookBookMap = director.getCookBookCollection().getCookBooks();
           CookBook cookBook = cookBookMap.get(cookBookName);
           if( cookBook == null)
               viewManager.messageViewer.printErrorMessage(" Fail to select Book because " +
                       " there is no such book," +
                       " check if you spelled name properly");
           else
               director.setFocusedObject(cookBook);
       }
       catch (IndexOutOfBoundsException e) {
           viewManager.messageViewer.printErrorMessage("Fail to select Book because " +
                   "name was not defined.");
       }
    }
}
