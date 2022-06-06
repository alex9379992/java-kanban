import java.util.HashMap;
import java.util.Scanner;
public class TaskManager {
    HashMap<Integer, Task> taskData = new HashMap<>();
    HashMap<Integer, Subtask> subtaskData = new HashMap<>();
    HashMap<Integer, Epic> epicData = new HashMap<>();
    int idTask = 0;
    int idSabtask = 0;
    int idEpic = 0;
    Scanner scanner = new Scanner(System.in);

    public void creatingTask() {

        taskData.put(idTask, creatingNewTask());

    }
    public void creatingSabtask() {
        System.out.println("Введите название подзадачи ---> ");
        String nameSabtask = scanner.next();
        System.out.print("Введите краткое описание подзадачи ---> ");
        String sabtaskDescription = scanner.next();
        Subtask subtask = new Subtask(nameSabtask, sabtaskDescription, idSabtask = idGeneration(idSabtask));
        System.out.println("Подзадача создана под номером " + idSabtask + ". Добавьте в неё задачи ↓");
        subtask.taskList.put(idGeneration(idTask), creatingNewTask());
        while (true){
            System.out.println("Добавить еще? --> 1 \n" +
                    "Выйти --> 2");
            int command = scanner.nextInt();
            if(command == 1) {
                subtask.taskList.put(idGeneration(idTask), creatingNewTask());
            } else if (command == 2) {
                subtaskData.put(idSabtask, subtask);
                break;
            } else {
                System.out.println("Такой команды нет.");
            }
        }
    }
    public Task creatingNewTask() {
        System.out.println("Введите название задачи ---> ");
        String nameTask = scanner.next();
        System.out.print("Введите краткое описание задачи ---> ");
        String taskDescription = scanner.next();
        idTask = idGeneration(idTask);
        Task task = new Task(nameTask, taskDescription, idTask);
        System.out.println("Задача добавлена.");
        System.out.println(task);
        return task;
    }
    public int idGeneration(int id) {
        int newId = id + 1;
        return newId;
    }
}
