package Managers;

import HTTP.Adapters.LocalDateTimeDeserializer;
import HTTP.Adapters.LocalDateTimeSerializer;
import HTTP.Server.KVServer;
import Interfaces.HistoryManager;
import Interfaces.TaskManager;
import HTTP.Adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

public final class Managers <T extends TaskManager>{

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFileBackedTasksManager() {
        return  new FileBackedTasksManager("resources/dataSave.CSV");
    }

    public static KVServer getDefaultKVServer() throws IOException {
            return new KVServer();
    }
    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        return gsonBuilder.create();
    }
}