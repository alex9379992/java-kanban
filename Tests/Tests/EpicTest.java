package Tests;

import Managers.ID;
import Managers.InMemoryTaskManager;
import Tasks.Epic;
import Tasks.Status;
import Tasks.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private InMemoryTaskManager manager = new InMemoryTaskManager();

    private Epic epic;
    private ID id;

    @BeforeEach
    public void beforeEach() {
        id = new ID();
        epic = new Epic("Epic", "epic", id.generator());
    }

    @Test
    @DisplayName("Проверка расчета с спустым списком")
    public void checkStatusWhenTheListIsEmpty() {
        final int listSize = epic.getSubtaskData().size();
        assertEquals(0, listSize, " Длинна списка должна быть 0");
        final Status status = epic.getStatus();
        assertEquals(Status.NEW, status, "статус долже быть NEW");
    }

    @Test
    @DisplayName("Проверка расчета статуса на NEW")
    public void checkStatusWhenSubtasksStatusNew() {
        for (int i = 2; i <= 4; i++) {
            final Subtask subtask = new Subtask("Подзадача" + i, "подзадача" + i, id.generator(), epic.getId());
            epic.getSubtaskData().put(subtask.getId(), subtask);
        }
        final Status status = epic.getStatus();
        assertEquals(Status.NEW, status, "статус долже быть NEW");
    }

    @Test
    @DisplayName("Проверка расчета статуса на DONE")
    public void checkStatusWhenSubtasksStatusDone() {
        epic.getSubtaskData().putAll(addSubtasks(Status.DONE));
        manager.updateEpic(epic);
        final Status status = epic.getStatus();
        assertEquals(Status.DONE, status, "статус долже быть DONE");
    }

    @Test
    @DisplayName("Проверка расчета статуса на NEW and DONE")
    public void checkStatusWhenSubtasksStatusNewAndDone() {
        epic.getSubtaskData().putAll(addSubtasks(Status.DONE));
        epic.getSubtaskData().putAll(addSubtasks(Status.NEW));
        manager.updateEpic(epic);
        final Status status = epic.getStatus();
        assertEquals(Status.IN_PROGRESS, status, "статус долже быть IN_PROGRESS");
    }

    @Test
    @DisplayName("Проверка расчета статуса на IN_PROGRESS")
    public void checkStatusWhenSubtasksStatusProgress() {
        epic.getSubtaskData().putAll(addSubtasks(Status.IN_PROGRESS));
        manager.updateEpic(epic);
        final Status status = epic.getStatus();
        assertEquals(Status.IN_PROGRESS, status, "статус долже быть IN_PROGRESS");
    }

    private HashMap<Integer, Subtask> addSubtasks(Status status) {
        HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            final Subtask subtask = new Subtask("Подзадача" + i, "подзадача" + i, id.generator(), epic.getId());
            subtask.setStatus(status);
            subtaskHashMap.put(subtask.getId(), subtask);
        }
        return subtaskHashMap;
    }
}