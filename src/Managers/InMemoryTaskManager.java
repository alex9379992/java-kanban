package Managers;

import Interfaces.HistoryManager;
import Interfaces.TaskManager;
import TaskClases.Epic;
import TaskClases.Status;
import TaskClases.Subtask;
import TaskClases.Task;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    private HistoryManager historyManager = Managers.getDefaultHistoryManager();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Task> tasks = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);


    @Override
    public Map<Integer, Task> getTasks() {
        if (tasks.size() == 0) {
            System.out.println("Список задач пуст.");
        }
        System.out.println("В классе простых задач, есть следующие задачи:");
        return tasks;
    }

    @Override
    public Task getTask(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("По данному индексу ничего не нашлось.");
        }
        historyManager.addTask(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public int addNewTask(Task task) {
        System.out.println("Задача добавлена под индексом " + task.getId() + ".");
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public void updateTask(Task task) {
        while (true) {
            System.out.println("Введите новый статус");
            System.out.println("1 ---> IN_PROGRESS — над задачей ведётся работа.\n" +
                    "2 ---> DONE — задача выполнена.");
            int command = scanner.nextInt();
            if (command == 1) {
                task.setStatus(Status.IN_PROGRESS);
                System.out.println("Статус задачи изменен.");
                break;
            } else if (command == 2) {
                task.setStatus(Status.DONE);
                System.out.println("Статус задачи изменен.");
                break;
            } else {
                System.out.println("Новый статус введен некорректно.");
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("По введенному индексу, задача не найдена.");
        }
    }

    @Override
    public void deleteTasks() {
        if (tasks.size() != 0) {
            tasks.clear();
            System.out.println("Простые задачи удалены.");
        } else {
            System.out.println("Список задач уже пуст.");
        }
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        if (epics.size() == 0) {
            System.out.println("Список сложных задач пуст.");
        }
        System.out.println("В классе сложных задач, есть следующие задачи:");
        return epics;
    }

    @Override
    public Epic getEpic(int id) {
        if (!epics.containsKey(id)) {
            System.out.println("По данному индексу ничего не нашлось.");
        }
        historyManager.addTask(epics.get(id));
        return epics.get(id);
    }

    @Override
    public int addNewEpic(Epic epic) {
        System.out.println("Эпик создан под индексом " + epic.getId());
        epics.put(epic.getId(), epic);
        System.out.println(epic);
        return epic.getId();
    }

    @Override
    public void updateEpic(Epic epic) {
        int sumDone = 0;
        for (Subtask subtask : epic.getSubtaskData().values()) {
            if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
            }
            if (subtask.getStatus().equals(Status.DONE)) {
                sumDone += 1;
            }
        }
        if (epic.getSubtaskData().size() == sumDone) {
            epic.setStatus(Status.DONE);
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Сложная задача удалена.");
        } else {
            System.out.println("По данному индексу, сложная задача не нашлась.");
        }
    }

    @Override
    public void deleteEpics() {
        if (epics.size() != 0) {
            epics.clear();
            System.out.println("Сложные задачи удалены.");
        } else {
            System.out.println("Список сложных задач уже пуст.");
        }
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> subtaskList = new ArrayList<>();

        for (Epic epic : epics.values()) {
            for (Subtask subtask : epic.getSubtaskData().values()) {
                subtaskList.add(subtask);
            }
        }
        return subtaskList;
    }

    @Override
    public Map<Integer, Subtask> getEpicSubtasks(int epicId) {
        if (epics.size() == 0) {
            System.out.println("Список сложных задач пуст.");
        }
        return epics.get(epicId).getSubtaskData();
    }

    @Override
    public Subtask getSubtask(int epicId, int id) {
        if (!epics.containsKey(epicId) && !epics.get(epicId).getSubtaskData().containsKey(id)) {
            System.out.println("По веденному индексу, подзадача не нашлась.");
        }
        historyManager.addTask(epics.get(epicId).getSubtaskData().get(id));
        return epics.get(epicId).getSubtaskData().get(id);
    }

    @Override
    public int addNewSubtask(Subtask subtask, int idEpic) {
        epics.get(idEpic).getSubtaskData().put(subtask.getId(), subtask);
        System.out.println("Подзадача " + subtask.getName() + ", с индексом " + subtask.getId() +
                ", добавлена в " + epics.get(idEpic).getName());
        updateEpic(epics.get(idEpic));
        return subtask.getId();
    }

    @Override
    public void updateSubtask(Subtask subtask, int idEpic) {
        System.out.println("Выберите новый статус");
        System.out.println("1 ---> IN_PROGRESS — над задачей ведётся работа.\n" +
                "2 ---> DONE — задача выполнена.");
        int command = scanner.nextInt();
        if (command == 1) {
            subtask.setStatus(Status.IN_PROGRESS);
            System.out.println("Статус задачи изменен.");
            updateEpic(epics.get(idEpic));
        } else if (command == 2) {
            subtask.setStatus(Status.DONE);
            System.out.println("Статус задачи изменен.");
            updateEpic(epics.get(idEpic));
        }
    }

    @Override
    public void deleteSubtask(int id, int idEpic) {
        if (epics.get(idEpic).getSubtaskData().containsKey(id)) {
            epics.get(idEpic).getSubtaskData().remove(id);
            System.out.println("Подзадача задача удалена.");
        } else {
            System.out.println("По данному индексу, подзадача не нашлась.");
        }
    }

    @Override
    public void deleteSubtasks(int idEpic) {
        if (epics.get(idEpic).getSubtaskData().size() != 0) {
            epics.get(idEpic).getSubtaskData().clear();
            System.out.println("Подзадачи удалены.");
        } else {
            System.out.println("Подзадачи у данной сложной задачи отсутствуют.");
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
