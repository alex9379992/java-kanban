package ManagersClases;
import TaskClases.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private static ArrayList<Task> viewsHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if(viewsHistory.size() < 10) {
            viewsHistory.add(0,  task);
        }
        if(viewsHistory.size() >= 10) {
            viewsHistory.add(0,  task);
            viewsHistory.remove(10);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        if(viewsHistory.isEmpty()) {
            System.out.println("Список истории задач пуст");
        }
        return viewsHistory;
    }
}
