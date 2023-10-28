package medha.MedhaLibrary;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class RestClient {
    private final HttpClient httpClient;
//    private final ObjectMapper objectMapper;

    public RestClient() {
        this.httpClient = HttpClient.newHttpClient();

    }

   public String sendRequest(String apiUrl, String accessToken){
        try{
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("x-api-key", accessToken) // Set the access token in the request header
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                String responseBody = response.body();
                return responseBody;
            } else {
                System.err.println("Failed to GET data from. "+apiUrl+" Status code: " + statusCode);
                return "";
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
   }

    public String sendRequest(String apiUrl, String accessToken, Map<String,String> queryParam){
        apiUrl = constructURLWithQueryParams(apiUrl,queryParam);

        try{
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("x-api-key", accessToken) // Set the access token in the request header
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                String responseBody = response.body();
                return responseBody;
            } else {
                System.err.println("Failed to GET data from. "+apiUrl+" Status code: " + statusCode);
                return "";
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

   public String postRequest(String apiUrl, String accessToken, String requestBody){
       try{
           HttpRequest httpRequest = HttpRequest.newBuilder()
                   .uri(URI.create(apiUrl))
                   .header("x-api-key",  accessToken) // Set the access token in the request header
                   .header("Content-Type", "application/json") // Set the content type for JSON
                   .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                   .build();

           HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

           int statusCode = response.statusCode();
           //System.out.println("Report status "+statusCode);
           if (statusCode == 200) {
               String responseBody = response.body();
               return responseBody;
           } else {
               System.err.println("Failed to POST data. using "+ apiUrl +" Status code: " + statusCode);
               return "";
           }
       }catch (IOException | InterruptedException e) {
           e.printStackTrace();
       }
       return null;
   }
    private String constructURLWithQueryParams(String baseUrl, Map<String, String> queryParams) {
        StringBuilder result = new StringBuilder(baseUrl);
        if (queryParams != null && !queryParams.isEmpty()) {
            result.append("?");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                result.append("&");
            }
            result.deleteCharAt(result.length() - 1); // Remove the extra "&" at the end
        }
        return result.toString();
    }
}
