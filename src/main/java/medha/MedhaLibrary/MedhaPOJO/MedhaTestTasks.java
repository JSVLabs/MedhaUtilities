package medha.MedhaLibrary.MedhaPOJO;


import java.util.ArrayList;


public class MedhaTestTasks {
    private String _id;
    private TaskUser user;

    public String get_id() {
        return _id;
    }

    public TaskUser getUser() {
        return user;
    }

    public TaskProject getProject() {
        return project;
    }

    public ArrayList<TaskAction> getActions() {
        return actions;
    }

    public String getName() {
        return name;
    }

    public TaskOptions getOptions() {
        return options;
    }

    public String getType() {
        return type;
    }

    private TaskProject project;
    private ArrayList<TaskAction> actions;
    private String name;
    private TaskOptions options;
    private String type;
}
