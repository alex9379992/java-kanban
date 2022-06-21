import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    InMemoryEpicManager epicManager = new InMemoryEpicManager();

    ID id = new ID();
    Scanner scanner = new Scanner(System.in);

    protected void printMainMenu() {
        System.out.println("С какими типами задачь хотите произвести действия?");
        System.out.println("1 ---> Простые задачи.");
        System.out.println("2 ---> Сложные задачи. Задачи с несколькими подзадачами.");
        System.out.println("3 ---> Выход.");
    }

    private  void printTaskMenu() {
        System.out.println("Выберите действие: ↓");
        System.out.println("1 ---> Получение списка всех задач.");
        System.out.println("2 ---> Удаление всех задач.");
        System.out.println("3 ---> Получение по индексу.");
        System.out.println("4 ---> Создать задачу.");
        System.out.println("5 ---> Обновить статус задачи.");
        System.out.println("6 ---> Удалить по индексу.");
        System.out.println("7 ---> Изменить задачу.");
        System.out.println("8 ---> Получить историю вызовов по Id");
        System.out.println("0 ---> Назад.");
    }
    protected void outputTaskMenu() {
        while (true) {
            printTaskMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                taskManager.getList();
            } else if (command == 2) {
                taskManager.clear();
            } else if (command == 3) {
                taskManager.getById();
            }else if (command == 4) {
                taskManager.add(id.generator());
            } else if (command == 5) {
                taskManager.updateStatus();
            } else if (command == 6) {
                taskManager.removeById();
            } else if (command == 7) {
                taskManager.edit();
            } else if (command == 8) {
                taskManager.getHistory();
            } else if (command == 0) {
                break;
            } else {
                System.out.println("Неверно введена команда.");
            }
        }
    }

    private  void printEpicMenu() {
        System.out.println("Выберите действие: ↓");
        System.out.println("1 ---> Получение списка всех сложных задач.");
        System.out.println("11 --> Получение подзадач определённой сложной задачи");
        System.out.println("2 ---> Удаление всех сложных задач.");
        System.out.println("3 ---> Получение по индексу.");
        System.out.println("4 ---> Создать сложную задачу.");
        System.out.println("44 --> Добавить подзадачу в определенную сложную задачу");
        System.out.println("5 ---> Обновить статус подзадачи.");
        System.out.println("6 ---> Удалить по индексу.");
        System.out.println("7 ---> Изменить задачу.");
        System.out.println("8 ---> Получить историю вызовов по Id");
        System.out.println("0 ---> Назад.");
    }

    protected void outputEpicMenu() {
        while (true) {
            printEpicMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                epicManager.getList();
            } else if (command == 11) {
                epicManager.getSubtaskForId();
            } else if (command == 2) {
                epicManager.clear();
            } else if (command == 3) {
                epicManager.getById();
            } else if (command == 4) {
                epicManager.add(id.generator());
            } else if (command == 44) {
                epicManager.getList();
                System.out.println("Введите индекс сложной задачи, в которую хотите добавить подзадачи:");
                int idEpic = scanner.nextInt();
                epicManager.addSubtask(idEpic, id.generator());
            } else if (command == 5) {
                epicManager.updateStatus();
            } else if (command == 6) {
                epicManager.removeById();
            } else if (command == 7) {
                epicManager.edit();
            } else if (command == 8) {
                epicManager.getHistory();
            } else if (command == 0) {
                break;
            } else {
                System.out.println("Неверно введена команда.");
            }
        }
    }


}
