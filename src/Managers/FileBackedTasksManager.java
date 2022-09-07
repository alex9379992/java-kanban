package Managers;
import Interfaces.TaskManager;
import Tasks.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public static void main(String[] args) {
        writingToFile(); //работа приложения, при записи в файл.
        readingToFile();   //работа приложения при считывании из файла.
    }

    @Override
    public Task getTask(int id) {
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

    public void updateTask(Task task, Status status) {
        super.updateTask(task, status);
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
    public int addNewSubtask(Subtask subtask, int idEpic) {
        int idSubtask = super.addNewSubtask(subtask, idEpic);
        save();
        return idSubtask;
    }

    @Override
    public void updateSubtask(Subtask subtask, int idEpic, Status status) {
        super.updateSubtask(subtask, idEpic, status);
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

    public void save() {
        try (Writer fileWriter = new FileWriter("resources/dataSave.CSV")) {
            fileWriter.write(FormatCSV.recordingFields());
            for (Task task : tasks.values()) {
                fileWriter.write(FormatCSV.toStringCSV(task));
            }
            for (Epic epic : epics.values()) {
                fileWriter.write(FormatCSV.toStringCSV(epic));
            }
            for (Epic epic : epics.values()) {
                for (Subtask subtask : epic.getSubtaskData().values()) {
                    fileWriter.write(FormatCSV.toStringCSV(subtask));
                }
            }
            fileWriter.write(System.lineSeparator());
            if(historyManager.getSize() != 0) {
            fileWriter.write(FormatCSV.fromHistoryToString(historyManager));}
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void reading(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            skipLine(reader);
            while (reader.ready()) {
                String lines = reader.readLine();
                if (!lines.isBlank()) {
                    if (FormatCSV.fromString(lines).getType() == TaskType.TASK) {
                        tasks.put(FormatCSV.fromString(lines).getId(), FormatCSV.fromString(lines));
                    } else if (FormatCSV.fromString(lines).getType() == TaskType.SUBTASK) {
                        Subtask subtask = (Subtask) FormatCSV.fromString(lines);
                        epics.get(subtask.getIdEpic()).getSubtaskData().put(subtask.getId(), subtask);
                    } else if (FormatCSV.fromString(lines).getType() == TaskType.EPIC) {
                        epics.put(FormatCSV.fromString(lines).getId(), (Epic) FormatCSV.fromString(lines));
                    }
                } else {
                    String line = reader.readLine();
                    for (Integer key : FormatCSV.historyFromString(line)) {
                        if (tasks.containsKey(key)) {
                            historyManager.addTask(tasks.get(key));
                        } else if (epics.containsKey(key)) {
                            historyManager.addTask(epics.get(key));
                        } else {
                            for (Epic epic : epics.values()) {
                                if (epic.getSubtaskData().containsKey(key)) {
                                    historyManager.addTask(epic.getSubtaskData().get(key));
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(epics);
            System.out.println(tasks);
            System.out.println(historyManager.getHistory());
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    private void skipLine(BufferedReader reader) {
        for (int i = 0; i < 1; i++) {
            try {
                reader.readLine();
            } catch (IOException e) {
                throw new ManagerSaveException(e);
            }
        }
    }

    public static void writingToFile() {

        ID id = new ID();
        TaskManager manager = Managers.getDefaultFileBackedTasksManager();
        Task task = new Task("Задача 1", "задача1", id.generator());
        manager.addNewTask(task);
        Epic epic1 = new Epic("Эпик1", "эпик1", id.generator());
        manager.addNewEpic(epic1);
        for (int i = 2; i <= 4; i++) {
            Subtask subtask = new Subtask("Подзадача" + i, "подзадача" + i, id.generator(), epic1.getId());
            manager.addNewSubtask(subtask, epic1.getId());
        }
        Epic epic2 = new Epic("Эпик2", "эпик2", id.generator());
        manager.addNewEpic(epic2);
        manager.getTask(0);
        manager.getEpic(1);
        manager.getSubtask(epic1.getId(), 2);
        manager.getSubtask(epic1.getId(), 4);
        manager.getSubtask(epic1.getId(), 3);
        manager.getSubtask(epic1.getId(), 4);
        manager.getSubtask(epic1.getId(), 2);
        manager.getEpic(5);
        manager.getEpic(1);
        System.out.println("");
        List<Task> historyList = manager.getHistory();
        for (Task value : historyList) {
            System.out.println(value);
        }
    }

    public static void readingToFile() {
        File dir = new File("resources\\dataSave.CSV");
        FileBackedTasksManager manager = new FileBackedTasksManager();
        manager.reading(dir);
    }

   private static class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(Throwable cause) {
            super(cause);
        }
    }
}

