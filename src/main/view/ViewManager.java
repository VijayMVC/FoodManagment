package main.view;

public class ViewManager {
    public final MessageViewer messageViewer;
    public final DataViewer dataViewer;
    public final HelpViewer helpViewer;

    public ViewManager(){
        messageViewer = new MessageViewer();
        dataViewer = new DataViewer();
        helpViewer = new HelpViewer();

    }

}
