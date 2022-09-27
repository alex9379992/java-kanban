package HTTP.Server.Hundlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static HTTP.Server.HttpTaskServer.gson;
import static HTTP.Server.HttpTaskServer.manager;

public class TasksHandlerHistory implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().toString();
        String[] stringSplit = path.split("/");
        if(method.equals("GET") && stringSplit[1].equals("tasks") && stringSplit[2].equals("history")) {
            String stringJson = gson.toJson(manager.getHistory());
            exchange.sendResponseHeaders(200, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(stringJson.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, 0);
        }
    }
}
