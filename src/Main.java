import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
       Scanner scanner = new Scanner(System.in);

        while (true) {
            menu.printMainMenu();
            int command = scanner.nextInt();
            if (command == 1) {
               menu.outputTaskMenu();
            } else if (command == 2) {
               menu.outputEpicMenu();
            } else if (command == 3) {
                System.out.println("Программа завершена.");
                break;
            } else {
                System.out.println("Такой команды не существует.");
            }
        }
    }
}
