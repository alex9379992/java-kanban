package Managers;

import Interfaces.HistoryManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager{
    Path file;

    public FileBackedTasksManager() {
        file = Paths.get("dataSave.CSV");
    }

    @Override
    public Task getTask(int id)  {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public int addNewTask(Task task) {
       int idTask = super.addNewTask(task);
       save();
       return idTask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int idEpic = super.addNewEpic(epic);
        save();
        return idEpic;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public Map<Integer, Subtask> getEpicSubtasks(int epicId) {
        Map<Integer, Subtask> map = super.getEpicSubtasks(epicId);
        save();
        return map;
    }

    @Override
    public Subtask getSubtask(int epicId, int id) {
        Subtask subtask = super.getSubtask(epicId, id);
        save();
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask, int idEpic) {
        super.updateSubtask(subtask, idEpic);
        save();
    }

    @Override
    public void deleteSubtask(int id, int idEpic) {
        super.deleteSubtask(id, idEpic);
        save();
    }

    @Override
    public void deleteSubtasks(int idEpic) {
        super.deleteSubtasks(idEpic);
        save();
    }


    public void save() throws IOException {
        try(Writer fileWriter = new FileWriter("dataSave.CSV")) {
            fileWriter.write("id,type,name,status,description,epic");
            if(!tasks.isEmpty()) {
                for(int i = 0; i < tasks.size(); i++){
                    fileWriter.write(tasks.get(i).toString());
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public String toString(Task task) {

    }

    public Task fromString(String value) {

    }

     public static String historyToString(HistoryManager manager) {

    }

    public static List<Integer> historyFromString(String value) {

    }
}
