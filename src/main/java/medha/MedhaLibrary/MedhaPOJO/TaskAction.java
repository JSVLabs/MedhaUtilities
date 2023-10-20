package medha.MedhaLibrary.MedhaPOJO;


import java.util.ArrayList;


public class TaskAction {
    private String page;
    private String action;

    public String getPage() {
        return page;
    }

    public String getAction() {
        return action;
    }

    public ArrayList<String> getDataElement() {
        return dataElement;
    }

    public Object get_id() {
        return _id;
    }

    public TableData getTableData() {
        return tableData;
    }

    private ArrayList<String> dataElement;
    private Object _id;
    private TableData tableData;
}
