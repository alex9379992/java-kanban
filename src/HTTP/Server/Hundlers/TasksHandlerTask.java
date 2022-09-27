package HTTP.Server.Hundlers;
import Tasks.Task;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static HTTP.Server.HttpTaskServer.*;

public class TasksHandlerTask implements HttpHandler {
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
                    if (stringPath.length == 1 && stringPath[0].equals("task")) {
                        stringJson = gson.toJson(manager.getTasks());
                    } else if (stringPath.length == 2 && stringPath[0].equals("task?id")) {
                          int id = idFromQuery(stringPath[1]);
                          if(id != -1) {
                              stringJson = gson.toJson(manager.getTask(id));
                          } else {
                              exchange.sendResponseHeaders(400, 0);
                          }
                    } else {
                        stringJson = "Неверно передан id задачи.";
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
                    Task task = gson.fromJson(body, Task.class);
                    int idTask = manager.addNewTask(task);
                    if(idTask >= 0 ) {
                        stringJson = "Задача успешно добавлена";
                        exchange.sendResponseHeaders(201, 0);
                    } else {
                        stringJson = "Задача не добавлена";
                        exchange.sendResponseHeaders(400, 0);
                    }
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(stringJson.getBytes());
                    }
                    break;
                case "DELETE":
                    if (stringPath.length == 1 && !stringPath[0].contains("?")) {
                        manager.deleteTasks();
                        stringJson = "Задачи успешно удалены.";
                        exchange.sendResponseHeaders(200, 0);
                    } else if (stringPath[0].contains("?")) {
                        int id = idFromQuery(stringPath[1]);
                        if(id != -1) {
                            manager.deleteTask(id);
                            stringJson = "Задача под индексом " + id + " успешно удалена.";
                            exchange.sendResponseHeaders(200, 0);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int idFromQuery(String query) {
        try {
            return Integer.parseInt(query.replace("id=", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
