package Tests;
import Managers.Auxiliary.ID;
import Managers.InMemoryTaskManager;
import Tasks.Epic;

import Tasks.Task;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setUp() {
        manager =  new InMemoryTaskManager();
        id = new ID();
    }

    @Test
    @DisplayName("Проверка создания таска")
    public void checkAddNewTaskTest() {
        addNewTaskTest();
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
    @DisplayName("Проверка получения списка задач")
    public void getTasksTest() {
        initTask();
        initTask();
        final Map<Integer, Task> tasks = manager.getTasks();
        final int size = tasks.size();
        assertNotNull(tasks, "Список не должен быть null");
        assertEquals(2, size, "Длинна списка задачь должна быть 2");
    }

    @Test
    @DisplayName("Проверка получения пустого списка задач")
    public void getTasksTestEmptyList() {
        final Map<Integer, Task> tasks = manager.getTasks();
        assertEquals(0, tasks.size(), "Длинна списка задачь должна быть 0");
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
    @DisplayName("Проверка выдачи мапы с эпиками")
    public void getEpicsTest() {
        initEpic();
        final Map<Integer, Epic> epics = manager.getEpics();
        final int size = epics.size();
        assertEquals(1, size, "Мапа с эпиками не пустая");
        assertNotNull(epics, "Мапа с эпиками не пустая");
    }

    @Test
    @DisplayName("Проверка выдачи пустой мапы эпиков")
    public void getEpicsTestEmptyMap() {
        final Map<Integer, Epic> epics = manager.getEpics();
        final int size = epics.size();
        assertEquals(0, size, "Мапа с эпиками пустая");
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
    @DisplayName("Проверка получения списка истории")
    public void getHistory() {
        initEpicAndSubtask();
        List<Task> list = manager.getHistory();
        final int sizeBefore = list.size();
        manager.getSubtask(0, 1);
        list = manager.getHistory();
        final int sizeAfter = list.size();
        assertEquals(2, sizeBefore, "Длинна списка должна быть 1");
        assertEquals(3, sizeAfter, "Длинна списка должна быть 2");
        assertNotNull(list, "Список не должен быть null");
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

    @Test
    @DisplayName("Проверка пересечения времени")
    public void checkIntersectionTimeTest() {
        checkIntersectionTime();
    }
}
