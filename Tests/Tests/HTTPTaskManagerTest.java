package Tests;

import HTTP.Clients.KVTaskClient;
import HTTP.Server.KVServer;
import Managers.Auxiliary.ID;
import Managers.HTTPTaskManager;
import Managers.Managers;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager>{
    private KVServer kvServer;
    Gson gson = Managers.getGson();

    protected void taskManagerTestSetUp() {
        task = new Task("Задача1", "задача1", id.generator(), LocalDateTime.now(), 15);
        manager.addNewTask(task);
        epic = new Epic("Epic", "epic", id.generator());
        manager.addNewEpic(epic);
        subtask1 = new Subtask("Subtask1", "subtask1", id.generator(), epic.getId()
                , LocalDateTime.of(2022,1,1,1, 30), 30);
        subtask2 = new Subtask("Subtask2", "subtask2", id.generator(), epic.getId()
                , LocalDateTime.of(2022,1,1,3, 30), 30);
        manager.addNewSubtask(subtask1, epic.getId());
        manager.addNewSubtask(subtask2, epic.getId());
        manager.getTask(0);
    }

    @BeforeEach
    void setUp() throws IOException, KVTaskClient.ManagerSaveException {

        kvServer = new KVServer();
        kvServer.start();
        manager = new HTTPTaskManager(KVServer.PORT);
        id = new ID();
        taskManagerTestSetUp();
    }

    @AfterEach
    void tearDown() {
        kvServer.stop();
    }

    @Test
    void save() {
        manager.save();
        String value = kvServer.getValue("task");

    }

    @Test
    void reading() {

        manager.reading();
        manager.getTask(task.getId());
    }
}