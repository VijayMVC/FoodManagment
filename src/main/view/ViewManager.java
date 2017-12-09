package main.view;

public class ViewManager {
    public final MessageViewer messageViewer;
    public final DataViewer dataViewer;

    public ViewManager(){
        messageViewer = new MessageViewer();
        dataViewer = new DataViewer();
    }
}
