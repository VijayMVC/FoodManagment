package main.controler.execution;

import main.controler.Director;
import main.model.book.CookBook;
import main.model.cookBookIO.IOManager;
import main.view.ViewManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;


public class UnfocusedExecution implements ExecutionStrategy {
    private ViewManager viewManager;
    private Director director;


    public UnfocusedExecution(Director director){
        this.viewManager = director.getViewManager();
        this.director = director;
    }

    @Override
    public void execute(List<String> comandLine, Object o) {
        if(comandLine.isEmpty()){
            viewManager.messageViewer.printEmptyCommandLineMessage();
        }
        else if (comandLine.get(0).substring(0,1).equals("/")){
            executeConsoleCommand(comandLine);
        }
    }
    /*
     * All commands which, start with '/' are assumed to be a ConsoleCommand and won't be associate with
     * any context.
    */
    protected void executeConsoleCommand(List<String> comandLine) {
        switch(comandLine.get(0)){
            case "/help":{
                viewManager.messageViewer.printHelp();
                break;
            }
            case "/context":{
                viewManager.messageViewer.printContext(director.getFocusedObject());
                break;
            }
            case "/showBooks":{
                viewManager.dataViewer.showBooks(director.getCookBooks());
                break;
            }
            case "/selectBook":{
                selectBook(comandLine);
                break;
            }
            case "/import":{
                importBook(comandLine);
                break;
            }
            case "/export":{
                exportBook(comandLine);
                break;
            }
            case "/newBook":{
                createNewBook(comandLine);
                break;
            }
            default:{
                viewManager.messageViewer.printWrongConsoleCommandMessage();
                break;
            }
        }
    }

    private void createNewBook(List<String> comandLine) {
        try {
            String name = comandLine.get(1);
            if(director.getCookBooks().get(name) != null) {
                viewManager.messageViewer.printCreatingBookErrorMessage("book with that name already exists.");
            }
            else{
                director.getCookBooks().put(name, new CookBook(name));
            }
        }
        catch(IndexOutOfBoundsException e) {
            viewManager.messageViewer.printCreatingBookErrorMessage("name was not definded.");
        }
    }

    private void exportBook(List<String> comandLine) {
        try {
            String path = comandLine.get(1);
            IOManager.serialzie(director.getCookBooks(), path);
            viewManager.messageViewer.printSuccessExportMessage(path);
        } catch (IndexOutOfBoundsException e) {
            viewManager.messageViewer.printWrongExportMessage("path was not given.");
        } catch (IOException e) {
            viewManager.messageViewer.printWrongExportMessage("of wrong path.");
        }
    }

    private void importBook(List<String> comandLine) {
        try{
            String path = comandLine.get(1);
            Map<String, CookBook> cookBookMap = IOManager.deserialzie(path);
            Map<String, CookBook> currentCookBookMap = director.getCookBooks();
            for(CookBook cookBook : cookBookMap.values()){
                CookBook temp = currentCookBookMap.get(cookBook.toString());
                if( temp == null)
                    currentCookBookMap.put(cookBook.toString(), cookBook);
                else{
                    if(!cookBook.equals(temp))
                        currentCookBookMap.put(cookBook.toString() + "I", cookBook);
                }
            }
            viewManager.messageViewer.printSuccessImportMessage(path);
        }
        catch(IndexOutOfBoundsException e){
            viewManager.messageViewer.printWrongImportMessage("path was not given.");
        }catch(EOFException e ){
            viewManager.messageViewer.printWrongImportMessage("program found end of file. Make sure that selected directory is " +
                    "not an empty file.");
        } catch(StreamCorruptedException e ){
            viewManager.messageViewer.printWrongImportMessage("wrong type of stream. File may have wrong type, make sure " +
                    "the path point at \".ser\" file.");
        } catch(InvalidClassException e){
            viewManager.messageViewer.printWrongImportMessage("deprecated version of book file.");
        } catch (IOException e) {
            viewManager.messageViewer.printWrongImportMessage("of wrong path.");
        } catch (ClassNotFoundException e) {
            viewManager.messageViewer. printWrongImportMessage("CookBook is not defined.");
        }
    }

    private void  selectBook(List<String> comandLine){
       try {
           String cookBookName = comandLine.get(1);
           Map<String,CookBook> cookBookMap = director.getCookBooks();
           CookBook cookBook = cookBookMap.get(cookBookName);
           if( cookBook == null)
               viewManager.messageViewer.printWrongBookMessage("there is no such book," +
                       " check if you spelled name properly");
           else
               director.setFocusedObject(cookBook);
       }
       catch (IndexOutOfBoundsException e) {
           viewManager.messageViewer.printWrongBookMessage("name of book was not given");
       }
    }
}
