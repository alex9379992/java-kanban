import ManagersClases.Managers;
import ManagersClases.TaskManager;
import TaskClases.Task;

public class Main {
    public static void main(String[] args) {

        ID id = new ID();

        TaskManager manager = Managers.getDefaultTaskManager();

        for (int i = 0; i < 10; i++) {
            String name = "Задача";
            Task task = new Task(name, name, id.generator());
            manager.addNewTask(task);
        }

        for (int i = 0; i < 9; i++) {
            System.out.println(manager.getTask(i));
        }
        System.out.println(manager.getHistory());
    }
}
