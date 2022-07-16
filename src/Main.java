import Managers.Managers;
import Interfaces.TaskManager;
import TaskClases.Epic;
import TaskClases.Subtask;
import TaskClases.Task;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ID id = new ID();

        TaskManager manager = Managers.getDefaultTaskManager();

        Epic epic1 = new Epic("Эпик1", "Эпик1", id.generator());
        manager.addNewEpic(epic1);

        for (int i = 1; i <= 3; i++){
            Subtask subtask = new Subtask("Подзадача" + i, "Подзадача" + i, id.generator(), epic1.getId());
            manager.addNewSubtask(subtask, epic1.getId());
        }
        Epic epic2 = new Epic("Епик2", "Епик2", id.generator());
        manager.addNewEpic(epic2);


        manager.getEpic(0);
        manager.getSubtask(epic1.getId(), 1);
        manager.getSubtask(epic1.getId(), 3);
        manager.getSubtask(epic1.getId(), 2);
        manager.getSubtask(epic1.getId(), 3);
        manager.getSubtask(epic1.getId(), 1);
        manager.getEpic(4);
        manager.getEpic(0);
        System.out.println("");
        List<Task> historyList = manager.getHistory();

        for (int i = 0; i < historyList.size(); i++) {
            System.out.println(historyList.get(i));
        }

        manager.deleteEpic(0);

        for (int i = 0; i < historyList.size(); i++) {
            System.out.println(historyList.get(i));
        }
        }
    }

