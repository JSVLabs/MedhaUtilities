package medha.MedhaLibrary.MedhaPOJO;


import java.util.Map;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class DataElements {
    private Map<String, String> dynamicData;

    public Map<String, String> getDynamicData() {
        return dynamicData;
    }

    public String getDataElementValue() {
        return dataElementValue;
    }

    private String dataElementValue;
}
