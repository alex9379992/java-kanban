package Tests;

import Interfaces.TaskManager;
import Managers.ID;
import Managers.InMemoryTaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest{
    TaskManager manager;
    ID id;

    private void initTask() {
        Task task = new Task("Task", "task", id.generator());
        manager.addNewTask(task);
    }

    private void initEpic() {
        Epic epic = new Epic("Epic", "epic", id.generator());
        manager.addNewEpic(epic);
    }

    private void initSubtask() {
        Subtask subtask = new Subtask("Subtask", "subtask",
                id.generator(), manager.getEpic(0).getId());
        manager.addNewSubtask(subtask, manager.getEpic(0).getId());
    }

    private void initEpicAndSubtask() {
        initEpic();
        initSubtask();
        initSubtask();
    }

   @BeforeEach
   public void setUp() {
       manager = new InMemoryTaskManager();
       id = new ID();
   }

   @Test
   @DisplayName("Проверка на пустую историю")
  public void checkEmptyHistoryListTest() {
       List<Task> history1 = manager.getHistory();
       initTask();
       List<Task> history2 = manager.getHistory();
       assertNotEquals(history1, "Список не должен быть null");
       assertNotEquals(history2, "Список не должен быть null");
       assertEquals(history1, history2, "Списки должны быть идентичны");
       assertEquals(0, history1.size(), "Длинна списка должна быть 0");
   }

   @Test
    @DisplayName("Проверка на дублирование")
    public void checkForDuplicationTest() {
        initTask();
        manager.getTask(0);
        manager.getTask(0);
       List<Task> history = manager.getHistory();
       assertNotEquals(history, "Список не должен быть null");
       assertEquals(1, history.size(), "Длинна списка должна быть 1");
   }

   @Test
    @DisplayName("Удаление из серидины")
    public void checkDeleteFromCenterTest() {
        initTask();
        initTask();
        initTask();
        manager.getTask(0);
        manager.getTask(1);
        manager.getTask(2);
       final List<Task> history = manager.getHistory();
       final Task task1 = history.get(0);
       final Task task3 = history.get(2);
       assertEquals(3, history.size(), "Длинна списка должна быть 3");
       manager.deleteTask(1);
       final List<Task> historySaved = manager.getHistory();
       assertNotEquals(history,historySaved, "таски должн быть идентичны");
       assertEquals(task1, manager.getHistory().get(0), "таски должн быть идентичны");
       assertEquals(task3, manager.getHistory().get(1), "таски должн быть идентичны");
       assertEquals(2, historySaved.size(), "Длинна списка должна быть 2");
   }

    @Test
    @DisplayName("Удаление из начала списка")
    public void checkDeleteFromBeginningTest() {
        initTask();
        initTask();
        initTask();
        manager.getTask(0);
        manager.getTask(1);
        manager.getTask(2);
        final List<Task> history = manager.getHistory();
        Collections.reverse(history);
        final Task task2 = history.get(1);
        final Task task3 = history.get(2);
        assertEquals(3, history.size(), "Длинна списка должна быть 3");
        manager.deleteTask(0);
        final List<Task> historySaved = manager.getHistory();
        Collections.reverse(historySaved);
        assertNotEquals(history,historySaved, "таски должн быть идентичны");
        assertEquals(task2, manager.getHistory().get(1), "таски должн быть идентичны");
        assertEquals(task3, manager.getHistory().get(0), "таски должн быть идентичны");
        assertEquals(2, historySaved.size(), "Длинна списка должна быть 2");
    }

    @Test
    @DisplayName("Удаление из конца списка")
    public void checkDeleteFromTheEndTest() {
        initTask();
        initTask();
        initTask();
        manager.getTask(0);
        manager.getTask(1);
        manager.getTask(2);
        final List<Task> history = manager.getHistory();
        final Task task1 = history.get(0);
        final Task task2 = history.get(1);
        assertEquals(3, history.size(), "Длинна списка должна быть 3");
        manager.deleteTask(2);
        final List<Task> historySaved = manager.getHistory();
        assertEquals(task1, manager.getHistory().get(0), "таски должн быть идентичны");
        assertEquals(task2, manager.getHistory().get(1), "таски должн быть идентичны");
    }

}