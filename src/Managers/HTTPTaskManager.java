package Managers;

import HTTP.Clients.KVTaskClient;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPTaskManager extends FileBackedTasksManager {

    private static final String TASKS_KEY = "task";
    private static final String EPICS_KEY = "epic";
    private static final String SUBTASKS_KEY = "subtask";
    private static final String HISTORY_KEY = "history";
    private final KVTaskClient client;
    private final Gson gson;
    private final Type taskType =  new TypeToken<Map<Integer, Task>>(){}.getType();
    private final Type epicType = new TypeToken<Map<Integer, Epic>>() {}.getType();
    private final Type subtaskType = new TypeToken<ArrayList<Subtask>>() {}.getType();
    private final Type historyType = new TypeToken<List<Integer>>() {}.getType();

    public HTTPTaskManager(int port) throws KVTaskClient.ManagerSaveException {
        super("resources/dataSave.CSV");
        client  = new KVTaskClient(port);
        gson = Managers.getGson();
    }

    @Override
    public void save() {

        client.put(TASKS_KEY, gson.toJson(this.tasks));
        client.put(EPICS_KEY, gson.toJson(this.epics));

        String subtaskJson = gson.toJson(getSubtasks());
        client.put(SUBTASKS_KEY, subtaskJson);

        List<Integer> history = getHistory().stream()
                .map(Task::getId)
                .collect(Collectors.toList());
        String historyJson = gson.toJson(history);
        client.put(HISTORY_KEY, historyJson);
    }

    @Override
    public void reading() {

        Type taskType =  new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(client.load(TASKS_KEY), taskType);
        this.tasks.putAll(tasks);

        List<Subtask> subtasks = gson.fromJson(client.load(SUBTASKS_KEY), subtaskType);
        subtasks.forEach(subtask -> {
            int id = subtask.getId();
            int epicId = subtask.getIdEpic();
            this.epics.get(epicId).getSubtaskData().put(id, subtask);
            this.prioritizedTasks.add(subtask);
        });

        Map<Integer, Epic> epics = gson.fromJson(client.load(EPICS_KEY), epicType);
        this.epics.putAll(epics);

        ArrayList<Integer> history = gson.fromJson(client.load(HISTORY_KEY), historyType);
        for(Integer id : history) {
            historyManager.addTask(findTask(id));
        }

    }
}
