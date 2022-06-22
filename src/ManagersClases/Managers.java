package ManagersClases;

public final class Managers <T extends TaskManager>{

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }
}