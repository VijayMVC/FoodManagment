package main.view;


public class MessageViewer {

    public void printEmptyCommandLineMessage() {
    }

    public void printContext(Object focusedObject) {
        String objectClass = "Console";
        if (focusedObject != null)
            objectClass = focusedObject.getClass().getSimpleName() + ", " + focusedObject.toString();
        System.out.println("Working in context of: " + objectClass);
    }

    public void printErrorMessage(String message){
        System.out.println(message);
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}