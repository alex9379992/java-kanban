import java.util.HashMap;
import java.util.Scanner;

public class EpicManager {
    HashMap<Integer, Epic> epics = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    Scanner scannerLine = new Scanner(System.in);

    protected void getEpics() {
        if (epics.size() != 0) {
            System.out.println("Вот список сложных задач:");
            for (Integer epicNumber : epics.keySet()) {
                System.out.println(epics.get(epicNumber));
            }
        } else {
            System.out.println("Список сложных задач пуст.");
        }
    }

    protected void getSubtaskForId() {
        getEpics();
        if (epics.size() != 0) {
            System.out.println("Введите индекс подзадачи");
            int index = scanner.nextInt();
            for (Epic epic : epics.values()) {
                for (Integer subtaskNumber : epic.subtaskData.keySet()) {
                    Subtask subtask = epic.subtaskData.get(subtaskNumber);
                    if (subtask.id == index) {
                        System.out.println(subtask);
                    }
                }
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    protected void clearEpics() {
        if (epics.size() != 0) {
            epics.clear();
            System.out.println("Сложные задачи удалены.");
        } else {
            System.out.println("Список сложных задач уже пуст.");
        }
    }

    protected void getByIdEpic() {
        if (epics.size() != 0) {
            System.out.println("Введите индекс");
            int index = scanner.nextInt();
            boolean isFound = false;
            for (Integer epicNumber : epics.keySet()) {
                Epic epic = epics.get(epicNumber);
                if (epic.id == index) {
                    isFound = true;
                    System.out.println(epic);
                }
            }
            for (Integer epicNumber : epics.keySet()) {
                Epic epic = epics.get(epicNumber);
                for (Integer subtaskNumber : epic.subtaskData.keySet()) {
                    Subtask subtask = epic.subtaskData.get(subtaskNumber);
                    if (subtask.id == index) {
                        isFound = true;
                        System.out.println(subtask);
                    }
                }
            }
            if (!isFound) {
                System.out.println("По введенному индексу ничего не нашлось.");
            }
        } else {
            System.out.println("Список сложных задач пуст.");
        }
    }

    protected void addEpic(int id) {
        System.out.println("Введите название сложной задачи --->");
        String nameEpic = scannerLine.nextLine();
        System.out.println("Введите короткое описание сложной задачи --->");
        String epicDescription = scannerLine.nextLine();
        Epic epic = new Epic(nameEpic, epicDescription, id, "New");
        System.out.println("Эпик создан под индексом " + id);
        epics.put(epic.getId(), epic);
        System.out.println(epic);
    }

    protected void addSubtask(int idEpic, int idSubtask) {
        if (epics.size() != 0) {
            for (Integer epicNumber : epics.keySet()) {
                if (epicNumber.equals(idEpic)) {
                    System.out.println("Введите название подзадачи ---> ");
                    String nameSubtask = scannerLine.nextLine();
                    System.out.println("Введите краткое описание подзадачи ---> ");
                    String subtaskDescription = scannerLine.nextLine();
                    Subtask subtask = new Subtask(nameSubtask, subtaskDescription, idSubtask, "New", idEpic);
                    epics.get(epicNumber).subtaskData.put(idSubtask, subtask);
                    System.out.println("Подзадача создана под индексом " + idSubtask);
                    checkingEpicStatus(idEpic, idSubtask);
                }
            }
        } else {
            System.out.println("Список сложных задач пуст.");
        }

    }

    protected void updateSubtask() {
        if (epics.size() != 0) {
            getEpics();
            while (true) {
                System.out.println("Введите индекс сложной задачи:");
                int idEpic = scanner.nextInt();
                if (epics.get(idEpic).subtaskData.size() != 0) {
                    while (true) {
                        if (epics.containsKey(idEpic)) {
                            System.out.println(epics.get(idEpic).subtaskData);
                            System.out.println("Введите индекс подзадачи, в которой хотите обновить статус:");
                            int idSubtask = scanner.nextInt();
                            if (epics.get(idEpic).subtaskData.containsKey(idSubtask)) {
                                System.out.println("Введите новый статус");
                                System.out.println("In progress — над задачей ведётся работа.\n" +
                                                   "Done — задача выполнена.");
                                String newStatus = scannerLine.nextLine();
                                if (newStatus.equals("In progress") || newStatus.equals("Done")) {
                                    epics.get(idEpic).subtaskData.get(idSubtask).status = newStatus;
                                    System.out.println("Статус задачи изменен.");
                                    checkingEpicStatus(idEpic, idSubtask);
                                    return;
                                } else {
                                    System.out.println("Некорректно введен новый статус.");
                                }
                            }else {
                                System.out.println("Неверно введен индекс подзадачи.");
                            }
                        } else {
                            System.out.println("Неверно введен индекс сложной задачи.");
                        }
                    }
                } else {
                    System.out.println("Список сложной задачи пуст.");
                    break;
                }
            }
        } else {
            System.out.println("Список сложных задач пуст.");
        }
    }

    protected void removeById () {
        System.out.println("Введите индекс задачи");
        int index = scanner.nextInt();
        if (epics.containsKey(index)) {
            epics.remove(index);
            System.out.println("Сложная задача удалена.");
        }
        for (Integer keyEpic : epics.keySet()) {
            if (epics.get(keyEpic).subtaskData.containsKey(index)) {
                epics.get(keyEpic).subtaskData.remove(index);
                System.out.println("Подзадача удалена.");
            }
        }
    }

    private void checkingEpicStatus ( int keyEpic, int keySubtask) {
        int sumDone = 0;
        if (epics.get(keyEpic).subtaskData.get(keySubtask).status.equals("In progress")) {
            epics.get(keyEpic).status = "In progress";
        }
        for (Integer key : epics.get(keyEpic).subtaskData.keySet()) {
            if (epics.get(keyEpic).subtaskData.get(key).status.equals("Done")) {
                sumDone += 1;
            }
        }
        if(epics.get(keyEpic).subtaskData.size() == sumDone) {
            epics.get(keyEpic).status = "Done";
        }
    }
}