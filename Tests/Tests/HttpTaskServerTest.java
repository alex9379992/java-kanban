package Tests;

import HTTP.Server.HttpTaskServer;

import Managers.Managers;
import Tasks.Epic;
import Tasks.Subtask;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest extends TaskManagerTest {
    private final Type taskType =  new TypeToken<Map<Integer, Task>>(){}.getType();
    private final Type subtaskType = new TypeToken<ArrayList<Subtask>>() {}.getType();
    private final Type epicType = new TypeToken<Map<Integer, Epic>>() {}.getType();
    private final Type historyType = new TypeToken<ArrayList<Integer>>() {}.getType();
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
        Map<Integer, Task> tasks = gson.fromJson(response.body(), taskType);
        assertNotNull(tasks, "Задач не должна быть null");
        assertEquals(tasks.get(0).getName(), "Задача 1");
    }

    @Test
    public void testPOSTTask() throws IOException, InterruptedException {
      Task newTask = new Task("Задача3", "задача3", 4, LocalDateTime.now(), 15);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Коды должны совпадать");
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=4");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Коды должны совпадать");
    }

    @Test
    public void testGETSubtask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Subtask subtask = gson.fromJson(response.body(), Subtask.class);
        assertNotNull(subtask, "Задач не должна быть null");
        assertEquals(subtask.getName(), "Подзадача2");
    }

    @Test
    public void testGetSubtasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ArrayList<Subtask> subtasks = gson.fromJson(response.body(), subtaskType);
        assertNotNull(subtasks, "Задач не должна быть null");
        assertEquals(subtasks.get(0).getName(), "Подзадача2");
    }

    @Test
    public void testPOSTSubtask() throws IOException, InterruptedException {
        Subtask subtask = new Subtask("Subtask", "subtask",
                5, 1,
                LocalDateTime.of(2020, 1, 1, 19, 25), 31);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        String json = gson.toJson(subtask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Коды должны совпадать");
    }

    @Test
    public void testDELETESubtask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=5");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Коды должны совпадать");
    }

    @Test
    public void testGETEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Epic epic = gson.fromJson(response.body(), Epic.class);
        assertNotNull(epic, "Задач не должна быть null");
        assertEquals(epic.getName(), "Эпик1");
    }

    @Test
    public void testGETEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<Integer, Epic> epics = gson.fromJson(response.body(), epicType);
        assertNotNull(epics, "Задач не должна быть null");
        assertEquals(1, epics.size(), "Длинна должна быть 2");
    }

    @Test
    public void testPOSTEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "epic", 6);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Коды должны совпадать");
    }

    @Test
    public void testDELETEEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?id=5");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Коды должны совпадать");
    }

    @Test
    public void testGETHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> list = gson.fromJson(response.body(), historyType);
        assertNotNull(list, "Задач не должна быть null");
    }
}