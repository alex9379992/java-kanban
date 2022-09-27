package Tests;

import HTTP.Server.HttpTaskServer;

import Managers.Managers;
import Tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest extends TaskManagerTest {
    Gson gson = Managers.getGson();


    @BeforeEach
    void setUp() throws IOException {
        HttpTaskServer.start();
        HttpTaskServer.reading();

    }

    @AfterEach
    void after() {
        HttpTaskServer.stop();
    }

    @Test
    public void testGETTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=0");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Task task = gson.fromJson(response.body(), Task.class);
        assertNotNull(task, "Задач не должна быть null");
        assertEquals(task.getName(), "Задача 1");

    }

    @Test
    public void testGETTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> tasks = gson.fromJson(response.body(), taskType);
        //assertNotNull(task, "Задач не должна быть null");
        //assertEquals(task.getName(), "Задача 1");

    }
}