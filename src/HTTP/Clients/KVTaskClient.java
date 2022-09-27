package HTTP.Clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class KVTaskClient {

    private final String url;
    private final String apiToken;

    public KVTaskClient(int port) throws ManagerSaveException {
        url = "http://localhost:" + port + "/";
        apiToken = register(url);
    }

    private String register(String url) throws  ManagerSaveException {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> send = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
            if(send.statusCode() != 200) {
                 throw new ManagerSaveException("Ошибка сохранения");
            }
            return send.body();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String load(String key) {
        try {
          HttpClient httpClient = HttpClient.newHttpClient();
          HttpRequest build = HttpRequest.newBuilder()
                  .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                  .GET()
                  .build();
          HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
          return response.body();
          } catch (IOException | InterruptedException e) { // обработка ошибки отправки запроса
          System.out.println("Во время выполнения запроса ресурса по URL-адресу: '" + url + "' возникла ошибка.\n" +
                  "Проверьте, пожалуйста, адрес и повторите попытку.");
          }
        return "";
    }

    public void put(String key, String json) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> send = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static class ManagerSaveException extends Throwable {
        public ManagerSaveException(String message) {
            super(message);
        }
    }

    static class ManagerLoadException extends Throwable {
        public ManagerLoadException(String message) {
            super(message);
        }
    }
}