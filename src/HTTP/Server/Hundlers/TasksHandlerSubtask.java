package HTTP.Server.Hundlers;

import Tasks.Epic;
import Tasks.Subtask;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.InputStream;
import java.io.OutputStream;

import static HTTP.Server.HttpTaskServer.*;
import static HTTP.Server.Hundlers.TasksHandlerTask.idFromQuery;

public class TasksHandlerSubtask implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().toString();
        String[] stringSplit = path.split("/");
        String[] stringPath = stringSplit[2].split("=");
        String stringJson = "";

        try {
            switch (method) {
                case "GET":
                    if(stringSplit.length == 4 && stringSplit[3].contains("epic")) {
                        String[] findEpicID = stringSplit[3].split("=");
                        int id = idFromQuery(findEpicID[1]);
                        stringJson = gson.toJson(manager.getEpicSubtasks(id));
                    } else if (stringPath.length == 1 && stringPath[0].equals("subtask")) {
                        stringJson = gson.toJson(manager.getSubtasks());
                    } else if (stringPath.length == 2 && stringPath[0].equals("subtask?id")) {
                        int id = idFromQuery(stringPath[1]);
                        if (id != -1) {
                            if(findSubtask(id) != null) {
                                stringJson = gson.toJson(findSubtask(id));
                            } else {
                                stringJson = "Подзадача не найдена.";
                                exchange.sendResponseHeaders(400, 0);
                            }
                        } else {
                            stringJson = "Неверно передан id задачи.";
                            exchange.sendResponseHeaders(400, 0);
                        }
                    } else {
                        stringJson = "Неверно передан путь.";
                        exchange.sendResponseHeaders(400, 0);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(stringJson.getBytes());
                    }
                    break;
                case "POST":
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    inputStream.close();
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    int idEpic = subtask.getIdEpic();
                    int idTask = manager.addNewSubtask(subtask, idEpic);
                    if(idTask >= 0 ) {
                        stringJson = "Подзадача успешно добавлена";
                        exchange.sendResponseHeaders(201, 0);
                    } else {
                        stringJson = "Подзадача не добавлена";
                        exchange.sendResponseHeaders(400, 0);
                    }
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(stringJson.getBytes());
                    }
                    break;
                case "DELETE":
                    if (stringPath.length == 1 && !stringPath[0].contains("?")) {
                        for(Epic epic : manager.getEpics().values()) {
                            manager.deleteSubtasks(epic.getId());
                        }
                        stringJson = "Задачи успешно удалены.";
                        exchange.sendResponseHeaders(200, 0);
                    } else if (stringPath[0].contains("?")) {
                        int id = idFromQuery(stringPath[1]);
                        if(id != -1) {
                            for (Epic epic : manager.getEpics().values()) {
                                if (epic.getSubtaskData().containsKey(id)) {
                                    manager.getSubtask(epic.getId(), id);
                                }
                                stringJson = "Задача под индексом " + id + " успешно удалена.";
                                exchange.sendResponseHeaders(200, 0);
                            }
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                            stringJson = "Задача под индексом " + id + " не удалена.";
                        }
                    } else {
                        exchange.sendResponseHeaders(400, 0);
                    }
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(stringJson.getBytes());
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + method);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Subtask findSubtask(int id) {
        for (Epic epic : manager.getEpics().values()) {
            if (epic.getSubtaskData().containsKey(id)) {
                return epic.getSubtaskData().get(id);
            }
        }
        return null;
    }
}