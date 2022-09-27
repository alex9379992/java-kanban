package HTTP.Server.Hundlers;

import Tasks.Epic;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static HTTP.Server.HttpTaskServer.*;
import static HTTP.Server.HttpTaskServer.manager;
import static HTTP.Server.Hundlers.TasksHandlerTask.idFromQuery;

public class TasksHandlerEpic implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().toString();
        String[] stringSplit = path.split("/");
        String[] stringPath = stringSplit[2].split("=");
        String stringJson = "";

        try {
            switch (method) {
                case "GET":
                    if (stringPath.length == 1 && stringPath[0].equals("epic")) {
                        stringJson = gson.toJson(manager.getEpics());
                    } else if (stringPath.length == 2 && stringPath[0].equals("epic?id")) {
                        int id = idFromQuery(stringPath[1]);
                        if(id != -1) {
                            stringJson = gson.toJson(manager.getEpic(id));
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                    } else {
                        stringJson = "Неверно передан id эпика.";
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
                    Epic epic = gson.fromJson(body, Epic.class);
                    int idTask = manager.addNewTask(epic);
                    if(idTask >= 0 ) {
                        stringJson = "Эпик успешно добавлен";
                        exchange.sendResponseHeaders(201, 0);
                    } else {
                        stringJson = "Эпик не добавлен";
                        exchange.sendResponseHeaders(400, 0);
                    }
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(stringJson.getBytes());
                    }
                    break;
                case "DELETE":
                    if (stringPath.length == 1 && !stringPath[0].contains("?")) {
                        manager.deleteEpics();
                        stringJson = "Задачи успешно удалены.";
                        exchange.sendResponseHeaders(200, 0);
                    } else if (stringPath[0].contains("?")) {
                        int id = idFromQuery(stringPath[1]);
                        if(id != -1) {
                            manager.deleteEpic(id);
                            stringJson = "Эпик под индексом " + id + " успешно удален.";
                            exchange.sendResponseHeaders(200, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                            stringJson = "Эпик под индексом " + id + " не удален.";
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
