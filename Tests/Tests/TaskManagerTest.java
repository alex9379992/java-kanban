package Tests;

import Managers.ID;
import Interfaces.TaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    public void updateEpicTest() {
        initEpic();
        initSubtask(); //При создании сабтаска, автоматически пересчитывается статус эпика
        Status status = manager.getEpic(0).getStatus();
        assertEquals(Status.NEW, status, "Статусы не совпадают");
        manager.updateSubtask(manager.getSubtask(0,1), 0, Status.IN_PROGRESS);
        status = manager.getEpic(0).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статусы не совпадают");
        manager.updateSubtask(manager.getSubtask(0,1), 0, Status.DONE);
        status = manager.getEpic(0).getStatus();
        assertEquals(Status.DONE, status, "Статусы не совпадают");
        initSubtask();
        status = manager.getEpic(0).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статусы не совпадают");
    }

    public void deleteEpicTest() {
        initEpic();
        manager.deleteEpic(0);
        final int size = manager.getEpics().size();
        assertEquals(0, size, "Список эпиков должен быть пустым");
    }

    public void deleteEpicTestEmptyMap() {
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpic(0);
        final int sizeAfter = manager.getEpics().size();
        assertEquals(0, sizeAfter, "Список эпиков должен быть пустым");
        assertEquals(sizeBefore, sizeAfter, "Список эпиков должен быть пустым");
    }

    public void deleteEpicTestIncorrectId() {
        initEpic();
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpic(1);
        final int sizeAfter = manager.getEpics().size();
        assertEquals(1, sizeAfter, "Длинна мапы не должна измениться");
        assertEquals(sizeBefore, sizeAfter, "Длинна мапы не должна измениться");
    }

    public void deleteEpicsTest() {
        initEpic();
        initEpic();
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpics();
        final int sizeAfter = manager.getEpics().size();
        assertEquals(2, sizeBefore, "Мапа не должа быть пуста");
        assertEquals(0, sizeAfter, "Мапа должа быть пуста");
    }

    public void deleteEpicsTestEmptyMap() {
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpics();
        final int sizeAfter = manager.getEpics().size();
        assertEquals(0, sizeAfter, "Мапа должа быть пуста");
        assertEquals(sizeBefore, sizeAfter, "Мапа должа быть пуста");
    }
    public void getSubtasksTest() {
        initEpicAndSubtask();
        final int size = manager.getSubtasks().size();
        final List<Subtask> list = manager.getSubtasks();
        assertNotNull(list, "список не должен быть null");
        assertEquals(2, size, "Длинна списка должна быть 2");
    }

    public void getSubtasksTestEmptyList() {
        final int size = manager.getSubtasks().size();
        final List<Subtask> list = manager.getSubtasks();
        assertNotNull(list, "список не должен быть null");
        assertEquals(0, size, "Длинна списка должна быть 0");
    }

    public void getEpicSubtasksTest() {
        initEpicAndSubtask();
        final Map<Integer, Subtask> subtasks = manager.getEpicSubtasks(0);
        final int size = subtasks.size();
        assertNotNull(subtasks, "мапа не должена быть null");
        assertEquals(2, size, "Длинна мпы должна быть 2");
    }

    public void getEpicSubtasksTestIncorrectId() {
        initEpicAndSubtask();
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.getEpicSubtasks(1));
        Assertions.assertNull(exception.getMessage());
    }

    public void getEpicSubtasksTestEmptyMap() {
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.getEpicSubtasks(0));
        Assertions.assertNull(exception.getMessage());
    }

    public void getSubtaskTest() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0, 1);
        assertNotNull(subtask, "Подзадача не должна быть null");
    }

    public void getSubtaskTestIncorrectId() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0, 3);
        Assertions.assertNull(subtask);
    }

    public void getSubtaskTestEmptyList() {
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.getSubtask(0, 3));
        Assertions.assertNull(exception.getMessage());
    }

    public void addNewSubtaskTest() {
        initEpicAndSubtask();
        int sizeBefore = manager.getSubtasks().size();
        Subtask subtask = new Subtask("Subtask", "subtask", 3, 0);
        manager.addNewSubtask(subtask, 0);
        int sizeAfter = manager.getSubtasks().size();
        Subtask subtaskSaved = manager.getSubtask(0, 3);
        assertNotNull(subtaskSaved, "Подзадача не должна быть null");
        assertEquals(2, sizeBefore, "длинна списка подзадачь должна быть 2");
        assertEquals(3, sizeAfter, "длинна списка подзадачь должна быть 3");
    }

    public void addNewSubtaskTestIncorrectId() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        Subtask subtask = new Subtask("Subtask", "subtask", 3, 1);
        int returnId = manager.addNewSubtask(subtask, 1);// если вернется -1, то не сохранено.
        final int sizeAfter = manager.getSubtasks().size();
        Subtask subtaskSaved = manager.getSubtask(0, 3);
        Assertions.assertNull(subtaskSaved);
        assertEquals(-1, returnId, "Вернуться должно -1");
        assertEquals(2, sizeBefore, "длинна списка подзадачь должна быть 2");
        assertEquals(2, sizeAfter, "длинна списка подзадачь должна быть 2");
    }

    public void updateSubtaskTest() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0,1);
        assertNotNull(subtask, "Подзадача не должна быть null");
        manager.updateSubtask(subtask, 0, Status.IN_PROGRESS);
        Status status = manager.getSubtask(0, 1).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статус должен быть -> IN_PROGRESS");
    }

    public void updateSubtaskTestEmptyList() {
        final  int size = manager.getSubtasks().size();
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.updateSubtask(manager.getSubtask(0, 1), 0, Status.IN_PROGRESS));
        Assertions.assertNull(exception.getMessage());
        assertEquals(0, size, "Длинна списка должна быть 0");
    }

    public void updateSubtaskTestIncorrectId() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0,1);
        assertNotNull(subtask, "Подзадача не должна быть null");
        manager.updateSubtask(subtask, 1, Status.IN_PROGRESS);
        Status status = manager.getSubtask(0, 1).getStatus();
        assertEquals(Status.NEW, status, "Статус должен быть -> IN_PROGRESS");
    }

    public void deleteSubtaskTest() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(1, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(1, sizeAfter, "длинна списка подзадач должна быть 1");
    }

    public void deleteSubtaskTestIncorrectId() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(3, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(2, sizeAfter, "длинна списка подзадач должна быть 2");
    }

    public void deleteSubtaskTestEmptyList() {
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(3, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(0, sizeBefore, "длинна списка подзадач должна быть 0");
        assertEquals(0, sizeAfter, "длинна списка подзадач должна быть 0");
    }

    public void deleteSubtasksTest() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtasks(0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(0, sizeAfter, "длинна списка подзадач должна быть 0");
    }

    public void deleteSubtaskIncorrectId() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(3, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(2, sizeAfter, "длинна списка подзадач должна быть 2");
    }

    public void deleteSubtasksTestEmptyList() {
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtasks(1);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(0, sizeBefore, "длинна списка подзадач должна быть 0");
        assertEquals(0, sizeAfter, "длинна списка подзадач должна быть 0");
    }
}


