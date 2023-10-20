package medha.MedhaLibrary.MedhaPOJO;



import java.util.ArrayList;

public class TaskOptions {
    public ArrayList<Object> getPages() {
        return pages;
    }

    public ArrayList<Object> getActions() {
        return actions;
    }

    public ArrayList<Object> getDataElements() {
        return dataElements;
    }

    private ArrayList<Object> pages;
    private ArrayList<Object> actions;
    private ArrayList<Object> dataElements;
}
