package medha.MedhaLibrary;


import medha.MedhaLibrary.MedhaPOJO.Test;


public class MedhaDataReader {


    private static int iterationIndex;


    public static String getAccesstoken() {
        return accesstoken;
    }

    public static void setAccesstoken(String accesstoken) {
        MedhaDataReader.accesstoken = accesstoken;
    }

    private static String accesstoken;


    public static String getJobId() {
        return jobId;
    }

    public static void setJobId(String jobId) {
        MedhaDataReader.jobId = jobId;
    }

    private static String jobId;

    public static int getIterationIndex() {
        return iterationIndex;
    }

    public static void setIterationIndex(int iterationIndex) {
        MedhaDataReader.iterationIndex = iterationIndex;
    }

    public static int getTaskIndex() {
        return taskIndex;
    }

    public static void setTaskIndex(int taskIndex) {
        MedhaDataReader.taskIndex = taskIndex;
    }

    public static int getActionIndex() {
        return actionIndex;
    }

    public static void setActionIndex(int actionIndex) {
        MedhaDataReader.actionIndex = actionIndex;
    }

    public static String getBrowser() {
        return browser;
    }

    public static void setBrowser(String browser) {
        MedhaDataReader.browser = browser;
    }

    public static String getEnvironment() {
        return environment;
    }

    public static void setEnvironment(String environment) {
        MedhaDataReader.environment = environment;
    }

    public static String getTestId() {
        return testId;
    }

    public static void setTestId(String testId) {
        MedhaDataReader.testId = testId;
    }


    private static int taskIndex;

    private static int actionIndex;

    private static String browser;

    private static String environment;

    private static String testId;

    public static String getFieldValue(String fieldName)  {
        medha.MedhaLibrary.MedhaReader medhaReader = new MedhaReader();
        Test test = medhaReader.getTestInfo(MedhaDataReader.getTestId());
        String fieldValue = test.getIterations().get(MedhaDataReader.getIterationIndex()).getTasks().get(MedhaDataReader.getTaskIndex()).getActions().get(MedhaDataReader.getActionIndex()).getDataElements().get(fieldName);
        if(fieldValue == null){
            fieldValue = "";
        }
        return fieldValue;
    }
}
