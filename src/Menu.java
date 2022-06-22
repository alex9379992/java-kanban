//Убрал до лучших времен.
import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);

    protected void printMainMenu() {
        System.out.println("С какими типами задачь хотите произвести действия?");
        System.out.println("1 ---> Простые задачи.");
        System.out.println("2 ---> Сложные задачи. Задачи с несколькими подзадачами.");
        System.out.println("0 ---> Выход.");
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

            } else if (command == 2) {

            } else if (command == 3) {

            }else if (command == 4) {

            } else if (command == 5) {

            } else if (command == 6) {

            } else if (command == 7) {

            } else if (command == 8) {

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

            } else if (command == 11) {

            } else if (command == 2) {

            } else if (command == 3) {

            } else if (command == 4) {

            } else if (command == 44) {



            } else if (command == 5) {

            } else if (command == 6) {

            } else if (command == 7) {

            } else if (command == 8) {

            } else if (command == 0) {
                break;
            } else {
                System.out.println("Неверно введена команда.");
            }
        }
    }
}
