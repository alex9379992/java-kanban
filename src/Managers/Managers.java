package Managers;

import Interfaces.HistoryManager;
import Interfaces.TaskManager;

import java.io.File;

public final class Managers <T extends TaskManager>{

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFileBackedTasksManager() {
        return  new FileBackedTasksManager("resources/dataSave.CSV");
    }
}