package Tests;
import Managers.ID;
import Managers.InMemoryTaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
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

    @Test
    @DisplayName("Удаление эпика")
    public void deleteEpicTest() {
        initEpic();
        manager.deleteEpic(0);
        final int size = manager.getEpics().size();
        assertEquals(0, size, "Список эпиков должен быть пустым");
    }

    @Test
    @DisplayName("Удаление эпика из пустой мапы")
    public void deleteEpicTestEmptyMap() {
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpic(0);
        final int sizeAfter = manager.getEpics().size();
        assertEquals(0, sizeAfter, "Список эпиков должен быть пустым");
        assertEquals(sizeBefore, sizeAfter, "Список эпиков должен быть пустым");
    }

    @Test
    @DisplayName("Удаление эпика с неверным id")
    public void deleteEpicTestIncorrectId() {
        initEpic();
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpic(1);
        final int sizeAfter = manager.getEpics().size();
        assertEquals(1, sizeAfter, "Длинна мапы не должна измениться");
        assertEquals(sizeBefore, sizeAfter, "Длинна мапы не должна измениться");
    }

    @Test
    @DisplayName("Удаление содержимого мапы")
    public void deleteEpicsTest() {
        initEpic();
        initEpic();
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpics();
        final int sizeAfter = manager.getEpics().size();
        assertEquals(2, sizeBefore, "Мапа не должа быть пуста");
        assertEquals(0, sizeAfter, "Мапа должа быть пуста");
    }

    @Test
    @DisplayName("Удаление содержимого мапы")
    public void deleteEpicsTestEmptyMap() {
        final int sizeBefore = manager.getEpics().size();
        manager.deleteEpics();
        final int sizeAfter = manager.getEpics().size();
        assertEquals(0, sizeAfter, "Мапа должа быть пуста");
        assertEquals(sizeBefore, sizeAfter, "Мапа должа быть пуста");
    }

    @Test
    @DisplayName("Получить список с сабтасками")
    public void getSubtasksTest() {
        initEpicAndSubtask();
        final int size = manager.getSubtasks().size();
        final List<Subtask> list = manager.getSubtasks();
        assertNotNull(list, "список не должен быть null");
        assertEquals(2, size, "Длинна списка должна быть 2");
    }

    @Test
    @DisplayName("Получить пустой список с сабтасками")
    public void getSubtasksTestEmptyList() {
        final int size = manager.getSubtasks().size();
        final List<Subtask> list = manager.getSubtasks();
        assertNotNull(list, "список не должен быть null");
        assertEquals(0, size, "Длинна списка должна быть 0");
    }

    @Test
    @DisplayName("Получение списка сабтасков по id эпика")
    public void getEpicSubtasksTest() {
        initEpicAndSubtask();
        final Map<Integer, Subtask> subtasks = manager.getEpicSubtasks(0);
        final int size = subtasks.size();
        assertNotNull(subtasks, "мапа не должена быть null");
        assertEquals(2, size, "Длинна мпы должна быть 2");
    }

    @Test
    @DisplayName("Получение списка сабтасков по неверному id эпика")
    public void getEpicSubtasksTestIncorrectId() {
        initEpicAndSubtask();
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.getEpicSubtasks(1));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Получение пустого списка сабтасков по id эпика")
    public void getEpicSubtasksTestEmptyMap() {
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.getEpicSubtasks(0));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Получение подзадачи по id")
    public void getSubtaskTest() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0, 1);
        assertNotNull(subtask, "Подзадача не должна быть null");
    }

    @Test
    @DisplayName("Получение подзадачи по неверному id")
    public void getSubtaskTestIncorrectId() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0, 3);
        Assertions.assertNull(subtask);
    }
    @Test
    @DisplayName("Получение подзадачи по  id из пустого списка")
    public void getSubtaskTestEmptyList() {
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.getSubtask(0, 3));
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Создание подзадачи")
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

    @Test
    @DisplayName("Создание подзадачи с неверным id эпика")
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

    @Test
    @DisplayName("Проверка обновления статуса подзадачи")
    public void updateSubtaskTest() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0,1);
        assertNotNull(subtask, "Подзадача не должна быть null");
        manager.updateSubtask(subtask, 0, Status.IN_PROGRESS);
        Status status = manager.getSubtask(0, 1).getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Статус должен быть -> IN_PROGRESS");
    }

    @Test
    @DisplayName("Проверка обновления статуса подзадачи с пустым списком")
    public void updateSubtaskTestEmptyList() {
        final  int size = manager.getSubtasks().size();
        final NullPointerException exception = assertThrows(NullPointerException.class,
                () -> manager.updateSubtask(manager.getSubtask(0, 1), 0, Status.IN_PROGRESS));
        Assertions.assertNull(exception.getMessage());
        assertEquals(0, size, "Длинна списка должна быть 0");
    }

    @Test
    @DisplayName("Проверка обновления статуса подзадачи с неверным id")
    public void updateSubtaskTestIncorrectId() {
        initEpicAndSubtask();
        Subtask subtask = manager.getSubtask(0,1);
        assertNotNull(subtask, "Подзадача не должна быть null");
        manager.updateSubtask(subtask, 1, Status.IN_PROGRESS);
        Status status = manager.getSubtask(0, 1).getStatus();
        assertEquals(Status.NEW, status, "Статус должен быть -> IN_PROGRESS");
    }

    @Test
    @DisplayName("Удаление подзадачи по id")
    public void deleteSubtask() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(1, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(1, sizeAfter, "длинна списка подзадач должна быть 1");
    }

    @Test
    @DisplayName("Удаление подзадачи по неверному id  ")
    public void deleteSubtaskIncorrectId() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(3, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(2, sizeAfter, "длинна списка подзадач должна быть 2");
    }

    @Test
    @DisplayName("Удаление подзадачи по id с пустым списком")
    public void deleteSubtaskEmptyList() {
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtask(3, 0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(0, sizeBefore, "длинна списка подзадач должна быть 0");
        assertEquals(0, sizeAfter, "длинна списка подзадач должна быть 0");
    }

    @Test
    @DisplayName("Удаление списка подзадач")
    public void deleteSubtasksTest() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtasks(0);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(0, sizeAfter, "длинна списка подзадач должна быть 0");
    }

    @Test
    @DisplayName("Удаление списка подзадач с неверным id")
    public void deleteSubtasksTestIncorrectId() {
        initEpicAndSubtask();
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtasks(1);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(2, sizeBefore, "длинна списка подзадач должна быть 2");
        assertEquals(2, sizeAfter, "длинна списка подзадач должна быть 2");
    }

    @Test
    @DisplayName("Удаление пустого списка подзадач")
    public void deleteSubtasksTestEmptyList() {
        final int sizeBefore = manager.getSubtasks().size();
        manager.deleteSubtasks(1);
        final int sizeAfter = manager.getSubtasks().size();
        assertEquals(0, sizeBefore, "длинна списка подзадач должна быть 0");
        assertEquals(0, sizeAfter, "длинна списка подзадач должна быть 0");
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
        assertEquals(1, sizeBefore, "Длинна списка должна быть 1");
        assertEquals(2, sizeAfter, "Длинна списка должна быть 2");
        assertNotNull(list, "Список не должен быть null");
    }
}
