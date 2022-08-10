package Interfaces;

import Tasks.Task;

import java.util.List;

public interface HistoryManager <T extends Task> {


   void addTask(Task task);

    void remove(int id);

    List<Task> getHistory();

}
