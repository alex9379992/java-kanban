package HTTP.Server;

import HTTP.Server.Hundlers.*;
import Managers.Managers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import Managers.FileBackedTasksManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public final static Gson gson = Managers.getGson();

    private static final int PORT = 8080;
    private static final String path = "/tasks";
    private static final String pathTask = "/tasks/task";
    private static final String pathSubtask = "/tasks/subtask";
    private static final String pathEpic = "/tasks/epic";
    private static final String pathHistory = "/tasks/history";
    public static FileBackedTasksManager  manager = Managers.getDefaultFileBackedTasksManager();
    private static HttpServer httpServer;

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {

        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext(path, new TasksHandler());
        httpServer.createContext(pathTask, new TasksHandlerTask());
        httpServer.createContext(pathSubtask, new TasksHandlerSubtask());
        httpServer.createContext(pathEpic, new TasksHandlerEpic());
        httpServer.createContext(pathHistory, new TasksHandlerHistory());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public static void reading() {
         manager.reading();
    }

    public static void stop() {
        httpServer.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }
}