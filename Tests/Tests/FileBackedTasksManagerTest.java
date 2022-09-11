package Tests;

import Managers.FileBackedTasksManager;
import Managers.ID;
import Tasks.Epic;
import Tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private String fileDir = "resources\\testFile.CSV";

    @BeforeEach
    public void setUp() {
        manager =  new FileBackedTasksManager(fileDir);
        id = new ID();
    }

    @AfterEach
    public void afterDelete() {
        manager.getFile().delete();
    }



    @Test
    @DisplayName("Проверка получения задачи по id")
    public void checkedGetTaskTest() {
        getTaskTest();
    }

    @Test
    @DisplayName("Проверка получения задачи из пустого списка")
    public void checkGetTaskTestEmptyList () {
        getTaskTestEmptyList();
    }

    @Test
    @DisplayName("Проверка получения задачи по неверному id")
    public void checkGetTaskTestIncorrectId() {
        getTaskTestIncorrectId();
    }

    @Test
    @DisplayName("Проверка создания таска")
    public void checkAddNewTaskTest() {
        addNewTaskTest();
    }

    @Test
    @DisplayName("Проверка обновления статуса задачи")
    public void checkUpdateTaskTest() {
        updateTaskTest();
    }

    @Test
    @DisplayName("Проверка обновления статуса задачи в пустом списке")
    public void checkUpdateTaskTestEmptyList() {
        updateTaskTestEmptyList();
    }

    @Test
    @DisplayName("Проверка удаления задачи по id")
    public void checkDeleteTaskTest() {
        deleteTaskTest();
    }

    @Test
    @DisplayName("Проверка удаления задачи в пустом списке")
    public void checkDeleteTaskTestEmptyList() {
        deleteTaskTestEmptyList();
    }

    @Test
    @DisplayName("Проверка удаления задачи по неверному id")
    public void checkDeleteTaskTestIncorrectId() {
        deleteTaskTestIncorrectId();
    }

    @Test
    @DisplayName("Проверка удаления мапы с задачами")
    public void checkDeleteTasksTest() {
        deleteTasksTest();
    }

    @Test
    @DisplayName("Проверка удаления пустой мапы")
    public void checkDeleteTasksTestEmptyList() {
        deleteTasksTestEmptyList();
    }

    @Test
    @DisplayName("Проверка выдачи эпика по id")
    public void checkGetEpicTest() {
        getEpicTest();
    }

    @Test
    @DisplayName("Проверка выдачи эпика по неверному id")
    public void checkGetEpicTestIncorrectId() {
        getEpicTestIncorrectId();
    }

    @Test
    @DisplayName("Проверка выдачи эпика из пустой мапы")
    public void checkGetEpicTestEmptyMap() {
        getEpicTestEmptyMap();
    }

    @Test
    @DisplayName("Проверка создания эпика")
    public void checkAddNewEpicTest() {
        addNewEpicTest();
    }

    @Test
    @DisplayName("Проверка обновления статуса")
    public void checkUpdateEpicTest() {
        updateEpicTest();
    }
    @Test
    @DisplayName("Удаление эпика")
    public void checkDeleteEpicTest() {
        deleteEpicTest();
    }

    @Test
    @DisplayName("Удаление эпика из пустой мапы")
    public void checkDeleteEpicTestEmptyMap() {
        deleteEpicTestEmptyMap();
    }

    @Test
    @DisplayName("Удаление эпика с неверным id")
    public void checkDeleteEpicTestIncorrectId() {
        deleteEpicTestIncorrectId();
    }

    @Test
    @DisplayName("Удаление содержимого мапы")
    public void checkDeleteEpicsTest() {
        deleteEpicsTest();
    }

    @Test
    @DisplayName("Удаление содержимого мапы")
    public void checkDeleteEpicsTestEmptyMap() {
        deleteEpicsTestEmptyMap();
    }
    @Test
    @DisplayName("Получить список с сабтасками")
    public void checkGetSubtasksTest() {
        getSubtasksTest();
    }

    @Test
    @DisplayName("Получить пустой список с сабтасками")
    public void checkGetSubtasksTestEmptyList() {
        getSubtasksTestEmptyList();
    }

    @Test
    @DisplayName("Получение списка сабтасков по id эпика")
    public void checkGetEpicSubtasksTest() {
        getEpicSubtasksTest();
    }

    @Test
    @DisplayName("Получение списка сабтасков по неверному id эпика")
    public void checkGetEpicSubtasksTestIncorrectId() {
        getEpicSubtasksTestIncorrectId();
    }

    @Test
    @DisplayName("Получение пустого списка сабтасков по id эпика")
    public void checkGetEpicSubtasksTestEmptyMap() {
        getEpicSubtasksTestEmptyMap();
    }

    @Test
    @DisplayName("Получение подзадачи по id")
    public void checkGetSubtaskTest() {
        getSubtaskTest();
    }

    @Test
    @DisplayName("Получение подзадачи по неверному id")
    public void checkGetSubtaskTestIncorrectId() {
        getSubtaskTestIncorrectId();
    }

    @Test
    @DisplayName("Получение подзадачи по  id из пустого списка")
    public void checkGetSubtaskTestEmptyList() {
        getSubtaskTestEmptyList();
    }

    @Test
    @DisplayName("Создание подзадачи")
    public void checkAddNewSubtaskTest() {
        addNewSubtaskTest();
    }

    @Test
    @DisplayName("Создание подзадачи с неверным id эпика")
    public void checkAddNewSubtaskTestIncorrectId() {
        addNewSubtaskTestIncorrectId();
    }

    @Test
    @DisplayName("Проверка обновления статуса подзадачи")
    public void checkUpdateSubtaskTest() {
        updateSubtaskTest();
    }

    @Test
    @DisplayName("Проверка обновления статуса подзадачи с пустым списком")
    public void checkUpdateSubtaskTestEmptyList() {
        updateSubtaskTestEmptyList();
    }

    @Test
    @DisplayName("Проверка обновления статуса подзадачи с неверным id")
    public void checkUpdateSubtaskTestIncorrectId() {
        updateSubtaskTestIncorrectId();
    }

    @Test
    @DisplayName("Удаление подзадачи по id")
    public void checkDeleteSubtaskTest() {
        deleteSubtaskTest();
    }

    @Test
    @DisplayName("Удаление подзадачи по неверному id  ")
    public void checkDeleteSubtaskTestIncorrectId() {
        deleteSubtaskTestIncorrectId();
    }

    @Test
    @DisplayName("Удаление подзадачи по id с пустым списком")
    public void checkDeleteSubtaskTestEmptyList() {
        deleteSubtaskTestEmptyList();
    }

    @Test
    @DisplayName("Удаление списка подзадач")
    public void checkDeleteSubtasksTest() {
        deleteSubtasksTest();
    }

    @Test
    @DisplayName("Удаление списка подзадач с неверным id")
    public void checkDeleteSubtaskIncorrectId() {
        deleteSubtaskIncorrectId();
    }

    @Test
    @DisplayName("Удаление пустого списка подзадач")
    public void checkDeleteSubtasksTestEmptyList() {
        deleteSubtasksTestEmptyList();
    }

    @Test
    @DisplayName("Проверка сохранения и чтения с пустым эпиком")
    public void saveAndReadingTestEmptyEpic() {
        initEpic();
        manager.getEpic(0);
        manager.getEpics().clear();
        manager.reading();
        final Map<Integer, Epic> epics = manager.getEpics();
        final List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "Длинна списка истории должна быть 1");
        assertEquals(1, epics.size(), "Длинна мапы должна быть 1");
        assertNotNull(epics, "Список не должен быть пустым");
    }

    @Test
    @DisplayName("Проверка сохранения и чтения с пустым списком задач")
    public void saveAndReadingTest() {
        final FileBackedTasksManager.ManagerSaveException exception =
                assertThrows(FileBackedTasksManager.ManagerSaveException.class,
                        () -> manager.reading());
        final Map<Integer, Epic> epics = manager.getEpics();
        assertEquals(0, epics.size(), "Длинна мапы должна быть 0");
        assertNotNull(epics, "Список не должен быть null");
    }

    @Test
    @DisplayName("Проверка сохранения и чтения с пустым списком истории")
    public void saveAndReadingTestEmptyHistoryList() {
        initTask();
        final Map<Integer, Task> tasks = new HashMap<>();
        tasks.putAll(manager.getTasks());
        assertNotNull(tasks, "Мапа не должна быть null");
        assertEquals(1, tasks.size(), "Длинна мапы должна быть 1");
        manager.getTasks().clear();
        manager.reading();
        final Map<Integer, Task> tasksSaved = manager.getTasks();
        assertEquals(tasks, tasksSaved, "Мапы должны быть идентичны");
        assertEquals(1, tasksSaved.size(), "Длинна мапы должна быть 1");
        assertNotNull(tasksSaved, "Список не должен быть null");
        final List<Task> history = manager.getHistory();
        assertEquals(0, history.size(), "Длинна списка должна быть 0");
    }

    @Test
    @DisplayName("Проверка записи подзадач в PrioritizedTasks")
    public void checkAddSubtaskPrioritizedTasksTest() {
        checkAddSubtaskPrioritizedTasks();
    }

    @Test
    @DisplayName("Проверка записи задач в PrioritizedTasks")
    public void checkAddTaskPrioritizedTasksTest() {
        checkAddTaskPrioritizedTasks();
    }

    @Test
    @DisplayName("Проверка поля Эпика endTime")
    public void checkFieldEpicTest() {
        checkFieldEpic();
    }

    @Test
    @DisplayName("Проверка поля Subtask")
    public void checkFieldSubtaskTest() {
        checkFieldSubtask();
    }

    @Test
    @DisplayName("Проверка поля Task")
    public void checkFieldTaskTest() {
        checkFieldTask();
    }
}