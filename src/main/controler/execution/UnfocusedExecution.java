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
            viewManager.emptyComandLineMessage();
        }
        else if(comandLine.get(0).substring(0,1).equals("/")){
            executeConsoleCommand(comandLine);
        }
        else{
            return;
        }
    }
    /*
     * All commands which, start with '/' are assumed to be a ConsoleCommand and won't be associate with
     * any context.
    */
    public void executeConsoleCommand(List<String> comandLine) {
        switch(comandLine.get(0)){
            case "/help":{
                viewManager.printHelp();
                break;
            }
            case "/context":{
                viewManager.printContext(director.getFocusedObject());
                break;
            }
            case "/showBooks":{
                viewManager.showBooks(director.getCookBooks());
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
        }
    }

    private void importBook(List<String> comandLine) {
        try{
            String path = comandLine.get(1);
            Map<String, CookBook> cookBookMap = IOManager.deserialzie(path);
            director.setCookBooks(cookBookMap);
        }
        catch(IndexOutOfBoundsException e){
            viewManager.printWrongImportMessage("path was not given.");
        }catch(EOFException e ){
            viewManager.printWrongImportMessage("program found end of file. Make sure that selected directory is " +
                    "not an empty file.");
        } catch(StreamCorruptedException e ){
            viewManager.printWrongImportMessage("wrong type of stream. File may have wrong type, make sure " +
                    "the path point at \".ser\" file.");
        } catch(InvalidClassException e){
            viewManager.printWrongImportMessage("deprecated version of book file.");
        } catch (IOException e) {
            viewManager.printWrongImportMessage("path was wrong.");
        } catch (ClassNotFoundException e) {
            viewManager.printWrongImportMessage("CookBook is not defined.");
        }
    }

    private void  selectBook(List<String> comandLine){
       try {
           String cookBookName = comandLine.get(1);
           Map<String,CookBook> cookBookMap = director.getCookBooks();
           CookBook cookBook = cookBookMap.get(cookBookName);
           if( cookBook == null)
               viewManager.printWrongBookMessage("there is no such book," +
                       " check if you spelled name properly");
           else
               director.setFocusedObject(cookBook);
       }
       catch (IndexOutOfBoundsException e) {
           viewManager.printWrongBookMessage("name of book was not given");
       }
    }
}
