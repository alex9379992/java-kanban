import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        while (true){
            printMainMenu();
            int command = scanner.nextInt();
            if(command == 1) {
                while (true) {
                    printCreationTaskMenu();
                    int subcommand = scanner.nextInt();
                    if(subcommand == 1) {
                        taskManager.creatingTask();
                    } else if (subcommand == 2) {
                        taskManager.creatingSabtask();
                    }else if(subcommand == 4) {
                        break;
                    } else {
                        System.out.println("Такой команды не существует.");
                    }
                }
            } else {
                System.out.println("Такой команды не существует.");
            }
        }

    }

     static void printMainMenu() {
        System.out.println("Выберите пункт, который хотите выполнить:");
        System.out.println("1 ---> Добавить задачу.");
        System.out.println("2 ---> Выход.");
    }
    static void printCreationTaskMenu(){
        System.out.println("Какой тип задач хотите добавить?");
        System.out.println("1 ---> Простую задачу.");
        System.out.println("2 ---> Подзадачу.");
        System.out.println("3 ---> Эпик.");
        System.out.println("4 ---> Назад.");
    }

}
