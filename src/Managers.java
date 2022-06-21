public final class Managers {

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefaultEpicManager() {
        return new InMemoryEpicManager();
    }
}