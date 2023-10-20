package medha.MedhaLibrary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import medha.MedhaLibrary.MedhaPOJO.MedhaTestTasks;
import medha.MedhaLibrary.MedhaPOJO.Test;
import medha.MedhaLibrary.MedhaPOJO.UniqueId;

import java.lang.reflect.Method;
import java.util.ResourceBundle;


public class MedhaReader {
    private Test test;
//    private String medhaUri = "https://sandbox.medha.io/api/";
//    private String medhaTaskUri = "tests/v3/";
//    private String medhaTestUri = "tests/v4/";
//    private String medhaUniqueId = "results/get-id";
    private MedhaTestTasks[] medhaTestTasks;
    public MedhaReader(String accessToken){
        MedhaDataReader.setAccesstoken(accessToken);
    }

    public MedhaReader(){

    }
     private void getTests(String testId)  {
         ResourceBundle resourceBundle
                 = ResourceBundle.getBundle("MedhaConfig");
         String medhaUri = resourceBundle.getString("medhaBaseUrl");
         String medhaTaskUri = resourceBundle.getString("taskInfo");
         String medhaTestUri = resourceBundle.getString("testInfo");
         //medha.MedhaLibrary.RestClient rs = new medha.MedhaLibrary.RestClient();
         RestClient rs = new RestClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String testInfoUri = medhaUri + medhaTestUri + testId;
        String tasksUri = medhaUri + medhaTaskUri + testId + "/tasks";
        String testInfo = rs.sendRequest(testInfoUri,MedhaDataReader.getAccesstoken());
         try {
             this.test = objectMapper.readValue(testInfo, Test.class);
             String taskInfo = rs.sendRequest(tasksUri,MedhaDataReader.getAccesstoken());
             this.medhaTestTasks = objectMapper.readValue(taskInfo, MedhaTestTasks[].class);
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }

     }

     public Test getTestInfo(String testId)  {
         medha.MedhaLibrary.RestClient rs = new medha.MedhaLibrary.RestClient();
         ResourceBundle resourceBundle
                 = ResourceBundle.getBundle("MedhaConfig");
         String medhaUri = resourceBundle.getString("medhaBaseUrl");
         String medhaTestUri = resourceBundle.getString("testInfo");
         ObjectMapper objectMapper = new ObjectMapper();
         String testInfoUri = medhaUri + medhaTestUri + testId;
         String testInfo = rs.sendRequest(testInfoUri,"");
         try {
             return (objectMapper.readValue(testInfo, Test.class));
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }
     }

     private int getIterationCount(){
            int co = this.test.getIterations().size();
            return co;
     }

     private int getTasksCount(){
         return this.medhaTestTasks.length;
     }

     public void startTest(String[] argss)  {
         //medha.MedhaLibrary.MedhaReporter reporter = new medha.MedhaLibrary.MedhaReporter();
         MedhaReporter reporter = new MedhaReporter();
         ResourceBundle resourceBundle
                 = ResourceBundle.getBundle("MedhaConfig");
         //medha.MedhaLibrary.RestClient restClient = new medha.MedhaLibrary.RestClient();
         RestClient restClient = new RestClient();
         ObjectMapper objectMapper = new ObjectMapper();
         String actionResult = "";
         String browser = argss[argss.length-1];
         try{
             String baseUrl = resourceBundle.getString("medhaBaseUrl");
             String resultUrl = resourceBundle.getString("results");
             String apiUrl = baseUrl + resultUrl;
             //medha.MedhaLibrary.MedhaDataReader.setEnvironment(argss[argss.length-2]);
             MedhaDataReader.setEnvironment(argss[argss.length-2]);
             //medha.MedhaLibrary.MedhaDataReader.setBrowser(argss[argss.length-1]);
             MedhaDataReader.setBrowser(browser);
             //Creating a report json
             String[] tests = argss[0].split(" ");
             for(int testIndex =0;testIndex<tests.length;testIndex++){
                 String jobId = getUUID();
                 String testId = tests[testIndex];
                 getTests(testId);
                 medha.MedhaLibrary.MedhaDataReader.setTestId(testId);
                 test.setJobId(jobId);
                 medha.MedhaLibrary.MedhaDataReader.setJobId(jobId);
                 test.set_id(jobId);
                 test.setBrowser(browser);
                 test.setTestStatus("In Progress");
                 String resultJsonString = objectMapper.writeValueAsString(test);
                 restClient.postRequest(apiUrl,MedhaDataReader.getAccesstoken(), resultJsonString);
                 int taskCount = getTasksCount();
                 int iterationCount = getIterationCount();
                 iterationLoop: for(int i=0; i<iterationCount; i++) {
                     for (int t = 0; t < taskCount; t++) {
                         int actCount = this.medhaTestTasks[t].getActions().size();
                         for (int a = 0; a < actCount; a++) {
                             String pageName = this.medhaTestTasks[t].getActions().get(a).getPage();
                             String actionName = this.medhaTestTasks[t].getActions().get(a).getAction();
//                             medha.MedhaLibrary.MedhaDataReader.setIterationIndex(i);
//                             medha.MedhaLibrary.MedhaDataReader.setTaskIndex(t);
//                             medha.MedhaLibrary.MedhaDataReader.setActionIndex(a);
                             MedhaDataReader.setIterationIndex(i);
                             MedhaDataReader.setTaskIndex(t);
                             MedhaDataReader.setActionIndex(a);
                             actionResult = executeAction(pageName, actionName);
                             if(actionResult.equalsIgnoreCase("PASS") ){
                                 MedhaReporter.postActionStatus("Completed");
                             }else{
                                 System.out.println(actionResult);
                                 break iterationLoop;
                             }
                            }
                        }
                     //System.out.println("iteration end "+i);
                 }
                 //System.out.println("Out of iteration loop");
                 MedhaReporter.postTestStatus("Completed");
             }
         }catch (Exception ex){
             System.out.println(ex.getStackTrace());
         }

    }


    private String getUUID(){
        medha.MedhaLibrary.RestClient rs = new RestClient();
        UniqueId uid = new UniqueId();
        ObjectMapper objectMapper = new ObjectMapper();
        ResourceBundle resourceBundle
                = ResourceBundle.getBundle("MedhaConfig");
        String medhaUrl = resourceBundle.getString("medhaBaseUrl");
        String medhaUniqueId = resourceBundle.getString("uniqueId");
        String url = medhaUrl+medhaUniqueId;
        String uids = rs.sendRequest(url,MedhaDataReader.getAccesstoken());
        try {
            uid = objectMapper.readValue(uids, UniqueId.class);
            return uid.getId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private String executeAction(String className, String actionName) {
        Class[] noparams = {};
        try{
//            Action action =test.getIterations().get(MedhaDataReader.getIterationIndex()).getTasks().get(MedhaDataReader.getTaskIndex()).getActions().get(MedhaDataReader.getActionIndex());
//            action.setActionStatus("Executed");
            String fullClassPath = "com.libraries.pages."+className;
            Class cls = Class.forName(fullClassPath);
            Object obj = cls.getDeclaredConstructor().newInstance();
            Method method = cls.getDeclaredMethod(actionName, noparams);
            method.invoke(obj, null);
            //To Do: Add code to send request to update the execution status
            return "Pass";
        }catch(Exception ex) {
            //int iterationNumber = medha.MedhaLibrary.MedhaDataReader.getIterationIndex() + 1;
            int iterationNumber = MedhaDataReader.getIterationIndex() + 1;
            //System.out.println("Exception occured while executing the test "+ medha.MedhaLibrary.MedhaDataReader.getTestId()+" Iteration "+iterationNumber+" action "+className + " "+actionName+" error: "+ex.getLocalizedMessage());
            //System.out.println(ex.getStackTrace());
            return "Exception occured while executing the test "+ MedhaDataReader.getTestId()+" Iteration "+iterationNumber+" action "+className + " "+actionName+" error: "+ex.getLocalizedMessage();
        }
    }


}
