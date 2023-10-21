package medha.MedhaLibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import medha.MedhaLibrary.MedhaPOJO.Message;
import medha.MedhaLibrary.MedhaPOJO.PostMessage;
import medha.MedhaLibrary.MedhaPOJO.TestStatus;
import java.util.ResourceBundle;
public class MedhaReporter {
    public static void report(String description, String status, String screenshot){
        postReportMessage("In Progress", description,status,screenshot,"","");
      }

      public static void assertEqual(String description, String expected, String actual){
            if(expected.equalsIgnoreCase(actual)){
                postReportMessage("In Progress", description,"pass","",expected,actual);
            }else{
                postReportMessage("In Progress", description,"fail","",expected,actual);
            }
      }
    public static void postActionStatus(String actionStatus){
        postReportMessage(actionStatus, "","","","","");
    }
    public static void postTestStatus(String testStatus){

        try {
            ResourceBundle resourceBundle
                    = ResourceBundle.getBundle("MedhaConfig");
            medha.MedhaLibrary.RestClient restClient = new RestClient();
            ObjectMapper objectMapper = new ObjectMapper();
            String medhaUri = resourceBundle.getString("medhaBaseUrl");
            String postStatus = resourceBundle.getString("testStatus");
            TestStatus tstStatus = new TestStatus();
            tstStatus.setTestStatus(testStatus);
            String resultJsonString = objectMapper.writeValueAsString(tstStatus);
            String apiUrl = medhaUri + postStatus;
            String jobId = MedhaDataReader.getJobId();
            apiUrl = apiUrl+jobId;
            String repotInfo = restClient.postRequest(apiUrl,MedhaDataReader.getAccesstoken(), resultJsonString);
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
    }
      private static void postReportMessage(String actionStatus, String description, String status, String screenshot, String expected, String actual){
          PostMessage postmessage = new PostMessage();
          postmessage.setActionStatus(actionStatus);
          if(description!="" && status !="" ) {
              Message message = new Message();
              message.setStatus(status);
              message.setDescription(description);
              message.setScreenshot(screenshot);
              message.setExpected(expected);
              message.setActual(actual);
              postmessage.setMessage(message);
          }
           postMessage(postmessage);
      }
      private static void postMessage(PostMessage message){
        try {
            ResourceBundle resourceBundle
                    = ResourceBundle.getBundle("MedhaConfig");
            medha.MedhaLibrary.RestClient restClient = new RestClient();
            ObjectMapper objectMapper = new ObjectMapper();
            String medhaUri = resourceBundle.getString("medhaBaseUrl");
            String postMessage = resourceBundle.getString("postMessage");
            String resultJsonString = objectMapper.writeValueAsString(message);
            String apiUrl = medhaUri + postMessage;
            int iterationIndex = medha.MedhaLibrary.MedhaDataReader.getIterationIndex();
            int taskIndex = medha.MedhaLibrary.MedhaDataReader.getTaskIndex();
            int actionIndex = medha.MedhaLibrary.MedhaDataReader.getActionIndex();
            String jobId = MedhaDataReader.getJobId();
            apiUrl = apiUrl+jobId+"?iterationIndex="+String.valueOf(iterationIndex)+"&taskIndex="+String.valueOf(taskIndex)+"&actionIndex="+String.valueOf(actionIndex);
            String repotInfo = restClient.postRequest(apiUrl,MedhaDataReader.getAccesstoken(), resultJsonString);
            //System.out.println(repotInfo);
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }



      }
}
