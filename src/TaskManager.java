import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    Scanner scannerLine = new Scanner(System.in);

    protected void addTask(int index) {
        System.out.println("Введите название задачи ---> ");
        String nameTask = scannerLine.nextLine();
        System.out.println("Введите краткое описание задачи ---> ");
        String taskDescription = scannerLine.nextLine();
        Task task = new Task(nameTask, taskDescription, index);
        System.out.println("Задача добавлена под индексом " + index + ".");
        System.out.println(task);
        tasks.put(task.getId(), task);
    }

    protected void clearTasks() {
        if (tasks.size() != 0) {
            tasks.clear();
            System.out.println("Простые задачи удалены.");
        } else {
            System.out.println("Список задач уже пуст.");
        }
    }

    protected void getByIdTask() {
        if (tasks.size() != 0) {
            System.out.println("Введите индекс задачи");
            int index = scanner.nextInt();
            for (Integer taskNumber : tasks.keySet()) {
                Task task = tasks.get(taskNumber);
                if (task.id == index) {
                    System.out.println(task);
                } else {
                    System.out.println("По данному индексу, задача не обнаружена");
                }
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    protected void getTasks() {
        if (tasks.size() != 0) {
            System.out.println("В классе простых задач, есть следующие задачи:");
            for (Integer taskNumber : tasks.keySet()) {
                System.out.println(tasks.get(taskNumber));
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    protected void updateTask() {
        while (true) {
            System.out.println("Введите индекс задачи");
            int index = scanner.nextInt();
            if (tasks.containsKey(index)) {
                System.out.println("Введите новый статус");
                System.out.println("New — задача только создана, но к её выполнению ещё не приступили.\n" +
                        "In_progress — над задачей ведётся работа.\n" +
                        "Done — задача выполнена.");
                String newStatus = scanner.next();
                if (newStatus.equals("New") || newStatus.equals("In_progress") || newStatus.equals("Done")) {
                    tasks.get(index).setStatus(newStatus);
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

    protected void removeTaskById() {
        System.out.println("Введите индекс задачи");
        int index = scanner.nextInt();
        if (tasks.containsKey(index)) {
            tasks.remove(index);
            System.out.println("Задача удалена");
        } else {
            System.out.println("По введенному индексу, задача не найдена.");
        }
    }

    protected void editTask() {
        if(tasks.size() != 0) {
            System.out.println("Введите индекс задачи, которую хотите изменить:");
            int index = scanner.nextInt();
            for(Integer keyTask : tasks.keySet()) {
                if(tasks.get(keyTask).id.equals(index)) {
                    addTask(index);
                }
            }
        }  else {
            System.out.println("Список задач пуст.");
        }
    }
}
