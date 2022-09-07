package Tests;

import Managers.FileBackedTasksManager;
import Managers.ID;
import Managers.InMemoryTaskManager;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    public void setUp() {
        manager =  new FileBackedTasksManager();
        id = new ID();
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
}