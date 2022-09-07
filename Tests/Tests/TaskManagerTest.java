package Tests;

import Managers.ID;
import Interfaces.TaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagerTest<T extends TaskManager> {

    protected ID id;

    protected T manager;


    protected void initTask() {
        Task task = new Task("Task", "task", id.generator());
        manager.addNewTask(task);
    }

    protected void initEpic() {
        Epic epic = new Epic("Epic", "epic", id.generator());
        manager.addNewEpic(epic);
    }

    protected void initSubtask() {
        Subtask subtask = new Subtask("Subtask", "subtask",
                id.generator(), manager.getEpic(0).getId());
        manager.addNewSubtask(subtask, manager.getEpic(0).getId());
    }

    protected void initEpicAndSubtask() {
        initEpic();
        initSubtask();
        initSubtask();
    }
    protected void getTaskTest() {
        initTask();
        Task task = manager.getTask(0);
        final int taskId = manager.addNewTask(task);
        final Task savedTask = manager.getTask(taskId);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    protected void getTaskTestEmptyList() {
        final Task task = manager.getTask(1);
        Assertions.assertNull(task, "задача должна быть null");
    }

    public void getTaskTestIncorrectId() {
        initTask();
        final Task task = manager.getTask(1);
        Assertions.assertNull(task,"задача должна быть null");
    }

    protected void  addNewTaskTest() {
        initTask();
        final Task task = new Task("Task", "task", 0);
        assertNotNull(task, "такс не должен быть null");
        assertEquals(task, manager.getTask(task.getId()), "Задачи должны быть равны");
    }

    protected void updateTaskTest() {
        initTask();
        manager.updateTask(manager.getTask(0), Status.DONE);
        assertEquals(Status.DONE, manager.getTask(0).getStatus(), "Статус задачи должен быть DONE");
    }

    protected void updateTaskTestEmptyList() {
        final NullPointerException exception = Assertions.assertThrows(NullPointerException.class,
                () -> manager.updateTask(manager.getTask(0), Status.DONE));
        Assertions.assertNull(exception.getMessage());
    }

    protected void deleteTaskTest() {
        initTask();
        manager.deleteTask(0);
        final int size = manager.getTasks().size();
        assertEquals(0, size, "Длинна мапы должна быть 0");
    }

    public void deleteTaskTestEmptyList() {
        manager.deleteTask(0);
        final int size = manager.getTasks().size();
        assertEquals(0, size, "Длинна списка должна быть 0");
    }

    public void deleteTaskTestIncorrectId() {
        initTask();
        manager.deleteTask(1);
        final int size = manager.getTasks().size();
        assertEquals(1, size, "Длинна списка не должна измениться");
    }

    public void deleteTasksTest() {
        initTask();
        initTask();
        manager.deleteTasks();
        final int size = manager.getTasks().size();
        assertEquals(0, size);
    }

    public void deleteTasksTestEmptyList() {
        manager.deleteTasks();
        final int size = manager.getTasks().size();
        assertEquals(0, size, "Длинна списка не должна измениться");
    }

    public void getEpicTest() {
        initEpic();
        final Epic epic = manager.getEpic(0);
        assertEquals(epic, manager.getEpic(0), "Эпики не равны");
        assertEquals(0, epic.getId(), "Индексы эпиков не равны");
        assertNotNull(epic, "null");
    }

    public void getEpicTestIncorrectId() {
        initEpic();
        final Epic epic = manager.getEpic(1);
        Assertions.assertNull(epic, "Епик не null");
    }

    public void getEpicTestEmptyMap() {
        final Epic epic = manager.getEpic(1);
        Assertions.assertNull(epic, "Епик не null");
    }

    public void addNewEpicTest() {
        initEpic();
        final Epic epic = new Epic("Epic", "epic" , 0);
        assertNotNull(manager.getEpic(0), "Епик не должен быть пустым");
        assertEquals(epic, manager.getEpic(0), "Епики должы быть равны");
        assertEquals(manager.getEpic(0).getId(), epic.getId(), "id эпиков должны быть равны");
    }
}


