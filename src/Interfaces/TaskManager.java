package Interfaces;

import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.util.List;
import java.util.Map;

public interface
TaskManager {
     Map<Integer, Task> getTasks();
     Task getTask(int id);
     int addNewTask(Task task);
     void updateTask(Task task, Status status);
     void deleteTask(int id);
     void deleteTasks();
     Map<Integer, Epic> getEpics();
     Epic getEpic(int id);
     int addNewEpic(Epic epic);
     void updateEpic(Epic epic);
     void deleteEpic(int id);
     void deleteEpics();
     List<Subtask> getSubtasks();
     Map<Integer, Subtask> getEpicSubtasks(int epicId);
     Subtask getSubtask(int epicId, int id);
     int addNewSubtask(Subtask subtask, int idEpic);
     void updateSubtask(Subtask subtask, int idEpic, Status status);
     void deleteSubtask(int id, int idEpic);
     void deleteSubtasks(int idEpic);
     List<Task> getHistory();
}
