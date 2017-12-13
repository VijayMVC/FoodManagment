package main.view;


public class MessageViewer {

    public void printEmptyCommandLineMessage() { }

    public void printContext(Object focusedObject) {
        String objectClass = "Console";
        if (focusedObject != null)
            objectClass = focusedObject.getClass().getSimpleName() + ", " + focusedObject.toString();
        System.out.println("Working in context of: " + objectClass);
    }

    public void printWrongBookMessage(String message) {
        System.out.println("Selecting book failed, because " + message);
    }

    public void printWrongImportMessage(String message) {
        System.out.println("Importing books failed, because " + message);
    }

    public void printWrongExportMessage(String message) {
        System.out.println("Exporting books failed, because " + message);
    }

    public void printWrongConsoleCommandMessage() {
        System.out.println("Wrong command use /help to get" +
                " available commands");
    }

    public void printSuccessImportMessage(String path) {
        System.out.println("successfully imported books from " + path);
    }

    public void printSuccessExportMessage(String path) {
        System.out.println("successfully exported " +
                "books to " + path);
    }

    public void printCreatingBookErrorMessage(String message) {
        System.out.println("Failed to create book, because " +message);
    }

}