package medha.MedhaLibrary.MedhaPOJO;



import java.util.ArrayList;


public class Task {
    public String getTask() {
        return task;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    private String task;
    private ArrayList<Action> actions;
}
