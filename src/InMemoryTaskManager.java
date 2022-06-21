import java.util.HashMap;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    HashMap<Integer, Task> tasks = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    Scanner scannerLine = new Scanner(System.in);

    @Override
    public void add(int index) {
        System.out.println("Введите название задачи ---> ");
        String nameTask = scannerLine.nextLine();
        System.out.println("Введите краткое описание задачи ---> ");
        String taskDescription = scannerLine.nextLine();
        Task task = new Task(nameTask, taskDescription, index);
        System.out.println("Задача добавлена под индексом " + index + ".");
        System.out.println(task);
        tasks.put(task.getId(), task);
    }

    @Override
    public void clear() {
        if (tasks.size() != 0) {
            tasks.clear();
            System.out.println("Простые задачи удалены.");
        } else {
            System.out.println("Список задач уже пуст.");
        }
    }

    @Override
    public void getById() {
        if (tasks.size() != 0) {
            boolean isFound = false;
            System.out.println("Введите индекс задачи");
            int index = scanner.nextInt();

            for (Integer taskNumber : tasks.keySet()) {
                Task task = tasks.get(taskNumber);
                if (task.id == index) {
                    System.out.println(task);
                    isFound = true;
                    historyManager.add(task);
                }
            }
            if(!isFound) {
                System.out.println("По данному индексу ничего не нашлось.");
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    @Override
    public void getList() {
        if (tasks.size() != 0) {
            System.out.println("В классе простых задач, есть следующие задачи:");
            for (Integer taskNumber : tasks.keySet()) {
                System.out.println(tasks.get(taskNumber));
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    @Override
    public void updateStatus() {
        while (true) {
            System.out.println("Введите индекс задачи");
            int index = scanner.nextInt();
            if (tasks.containsKey(index)) {
                System.out.println("Введите новый статус");
                System.out.println("1 ---> IN_PROGRESS — над задачей ведётся работа.\n" +
                                   "2 ---> DONE — задача выполнена.");
                int command = scanner.nextInt();
                if (command == 1) {
                    tasks.get(index).setStatus(Status.IN_PROGRESS);
                    System.out.println("Статус задачи изменен.");
                    break;
                } else if (command ==2) {
                    tasks.get(index).setStatus(Status.DONE);
                    System.out.println("Статус задачи изменен.");
                    break;
                } else {
                    System.out.println("Новый статус введен некорректно.");
                }
            } else {
                System.out.println("По введенному индексу, задача не найдена.");
                break;
            }
        }
    }

    @Override
    public void removeById() {
        System.out.println("Введите индекс задачи");
        int index = scanner.nextInt();
        if (tasks.containsKey(index)) {
            tasks.remove(index);
            System.out.println("Задача удалена");
        } else {
            System.out.println("По введенному индексу, задача не найдена.");
        }
    }

    @Override
    public void edit() {
        if(tasks.size() != 0) {
            System.out.println("Введите индекс задачи, которую хотите изменить:");
            int index = scanner.nextInt();
            for(Integer keyTask : tasks.keySet()) {
                if(tasks.get(keyTask).id.equals(index)) {
                    add(index);
                }
            }
        }  else {
            System.out.println("Список задач пуст.");
        }
    }

    @Override
    public void getHistory(){
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }
    }
}
