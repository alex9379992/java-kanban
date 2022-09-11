package Managers;

import Interfaces.HistoryManager;
import Interfaces.TaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistoryManager();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, Task> tasks = new HashMap<>();

    protected TreeSet<Task> prioritizedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            if (task1.getStartTime() == null || task2.getStartTime() == null) {
                return task1.getId() - task2.getId();
            } else {
                if (task1.getStartTime().isBefore(task2.getStartTime())) {
                    return -1;
                } else if (task2.getStartTime().isBefore(task1.getStartTime())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    });

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

        if(task.getStartTime() != null) {
            if(checkTime(task)) {
                tasks.put(task.getId(), task);
                prioritizedTasks.add(task);
                System.out.println("Задача добавлена под индексом " + task.getId() + ".");
                return task.getId();

            } else {
                System.out.println("Найдено пересечение во времени задач");
            }
        } else {
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
            System.out.println("Задача добавлена под индексом " + task.getId() + ".");
            return task.getId();
        }
        return -1;
    }

    @Override
    public void updateTask(Task task, Status status) {
             task.setStatus(status);
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
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
        boolean isInProgress = false;
        for (Subtask subtask : epic.getSubtaskData().values()) {
            if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
                isInProgress = true;
            }
            if(subtask.getStatus().equals(Status.NEW) && isInProgress)
                epic.setStatus(Status.IN_PROGRESS);
            if (subtask.getStatus().equals(Status.DONE)) {
                sumDone += 1;
                isInProgress = true;
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
            subtaskList.addAll(epic.getSubtaskData().values());
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
        if(epics.containsKey(idEpic)) {
            if(subtask.getStartTime() != null) {
                if(checkTime(subtask)) {
                    epics.get(idEpic).getSubtaskData().put(subtask.getId(), subtask);
                    System.out.println("Подзадача " + subtask.getName() + ", с индексом " + subtask.getId() +
                            ", добавлена в " + epics.get(idEpic).getName());
                    updateEpic(epics.get(idEpic));
                    updateTimeEpic(epics.get(idEpic));
                    prioritizedTasks.add(subtask);
                    return subtask.getId();
                } else {
                    System.out.println("Обнаружено пересечение во времени");
                }
            } else {
                epics.get(idEpic).getSubtaskData().put(subtask.getId(), subtask);
                System.out.println("Подзадача " + subtask.getName() + ", с индексом " + subtask.getId() +
                    ", добавлена в " + epics.get(idEpic).getName());
                updateEpic(epics.get(idEpic));
                updateTimeEpic(epics.get(idEpic));
                prioritizedTasks.add(subtask);
                return subtask.getId();
            }
        }
        System.out.println("Ненашлось эпика с таким id");
        return -1;
    }

    @Override
    public void updateSubtask(Subtask subtask, int idEpic, Status status) {
          if(epics.containsKey(idEpic)) {
              subtask.setStatus(status);
              System.out.println("Статус задачи изменен.");
              updateEpic(epics.get(idEpic));
          }

    }

    @Override
    public void deleteSubtask(int id, int idEpic) {
        if (epics.containsKey(idEpic)) {
            if(epics.get(idEpic).getSubtaskData().containsKey(id)) {
                epics.get(idEpic).getSubtaskData().remove(id);
                updateTimeEpic(epics.get(idEpic));
                System.out.println("Подзадача задача удалена.");
            } else {
                System.out.println("По данному индексу, подзадачи не нашлась.");
            }
        } else {
            System.out.println("По данному индексу, эпика не нашлась.");
        }

    }

    @Override
    public void deleteSubtasks(int idEpic) {
        if(epics.containsKey(idEpic)) {
            if (epics.get(idEpic).getSubtaskData().size() != 0) {
                epics.get(idEpic).getSubtaskData().clear();
                System.out.println("Подзадачи удалены.");
            } else {
                System.out.println("Подзадачи у данной сложной задачи отсутствуют.");
            }
        } else {
            System.out.println("По данному id сложая задача отсутствует");
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    @Override
    public void updateTimeEpic(Epic epic) {
        LocalDateTime localDateTime = null;
        Duration duration = Duration.ofMinutes(0);
        for(Subtask subtask: epic.getSubtaskData().values()) {
                if(subtask.getStartTime() != null) {
                    if(localDateTime == null || subtask.getStartTime().isBefore(localDateTime)) {
                        localDateTime = subtask.getStartTime();
                        epic.setStartTame(localDateTime);
                        duration = duration.plusMinutes(subtask.getDuration().toMinutes());
                        epic.setDuration(duration);
                        epic.setEndTime(epic.getStartTame().plus(epic.getDuration()));
                    }
                }

        }
    }
    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public boolean checkTime(Task task) {
        List<Task> tasks = List.copyOf(prioritizedTasks);
        int sizeTimeNull = 0;
        if(tasks.size() > 0) {
            for (Task taskSave : tasks) {
                if(taskSave.getStartTime() != null && taskSave.getEndTime() != null) {
                    if (task.getStartTime().isBefore(taskSave.getStartTime())
                            && task.getEndTime().isBefore(taskSave.getStartTime())) {
                        return true;
                    } else if (task.getStartTime().isAfter(taskSave.getEndTime())
                            && task.getEndTime().isAfter(taskSave.getEndTime())) {
                        return true;
                    }
                } else {
                    sizeTimeNull++;
                }

            }
            if(sizeTimeNull == tasks.size()) {
                return true;
            }
        } else {
            return true;
        }

    return false;
    }
}
