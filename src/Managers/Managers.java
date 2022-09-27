package Managers;

import Interfaces.HistoryManager;
import Interfaces.TaskManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }
}