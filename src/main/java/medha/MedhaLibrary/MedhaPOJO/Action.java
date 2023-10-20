package medha.MedhaLibrary.MedhaPOJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {
    private String action;
    private String actionStatus;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public Map<String, String> getDataElements() {
        return dataElements;
    }

    public void setDataElements(Map<String, String> dataElements) {
        this.dataElements = dataElements;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    private Map<String, String> dataElements;
    private ArrayList<Message> messages;
}
