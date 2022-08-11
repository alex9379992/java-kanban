import Managers.Managers;
import Interfaces.TaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import Managers.ID;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ID id = new ID();

        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("Задача 1" , "задача1", id.generator());
        manager.addNewTask(task);
        Epic epic1 = new Epic("Эпик1", "эпик1", id.generator());
        manager.addNewEpic(epic1);

        for (int i = 2; i <= 4; i++){
            Subtask subtask = new Subtask("Подзадача" + i, "подзадача" + i, id.generator(), epic1.getId());
            manager.addNewSubtask(subtask, epic1.getId());
        }
        Epic epic2 = new Epic("Эпик2", "эпик2", id.generator());
        manager.addNewEpic(epic2);

        manager.getTask(0);
        manager.getEpic(1);
        manager.getSubtask(epic1.getId(), 2);
        manager.getSubtask(epic1.getId(), 4);
        manager.getSubtask(epic1.getId(), 3);
        manager.getSubtask(epic1.getId(), 4);
        manager.getSubtask(epic1.getId(), 2);
        manager.getEpic(5);
        manager.getEpic(1);
        System.out.println("");
        List<Task> historyList = manager.getHistory();

        for (int i = 0; i < historyList.size(); i++) {
            System.out.println(historyList.get(i));
        }
        }
    }

