import java.util.HashMap;
import java.util.Scanner;

public class InMemoryEpicManager implements InterfaceManager{
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private Scanner scannerLine = new Scanner(System.in);

    @Override
    public void getList() {
        if (epics.size() != 0) {
            System.out.println("Вот список сложных задач:");
            for (Integer epicNumber : epics.keySet()) {
                System.out.println(epics.get(epicNumber));
            }
        } else {
            System.out.println("Список сложных задач пуст.");
        }
    }

    public void getSubtaskForId() {
        getList();
        if (epics.size() != 0) {
            System.out.println("Введите индекс сложной задачи");
            int index = scanner.nextInt();
            if(epics.containsKey(index)) {
                System.out.println(epics.get(index).getSubtaskData());
            } else {
                System.out.println("Сложной задачи по данному индексу не обнаружено.");
            }
        } else {
            System.out.println("Список задач пуст.");
        }
    }

    @Override
    public void clear() {
        if (epics.size() != 0) {
            epics.clear();
            System.out.println("Сложные задачи удалены.");
        } else {
            System.out.println("Список сложных задач уже пуст.");
        }
    }

    @Override
    public void getById() {
        if (epics.size() != 0) {
            System.out.println("Введите индекс");
            int index = scanner.nextInt();
            boolean isFound = false;
            for (Integer epicNumber : epics.keySet()) {
                Epic epic = epics.get(epicNumber);
                if (epic.getId() == index) {
                    isFound = true;
                    System.out.println(epic);
                }
            }
            for (Integer epicNumber : epics.keySet()) {
                Epic epic = epics.get(epicNumber);
                for (Integer subtaskNumber : epic.getSubtaskData().keySet()) {
                    Subtask subtask = epic.getSubtaskData().get(subtaskNumber);
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

    @Override
    public void add(int id) {
        System.out.println("Введите название сложной задачи --->");
        String nameEpic = scannerLine.nextLine();
        System.out.println("Введите короткое описание сложной задачи --->");
        String epicDescription = scannerLine.nextLine();
        Epic epic = new Epic(nameEpic, epicDescription, id);
        System.out.println("Эпик создан под индексом " + id);
        epics.put(epic.getId(), epic);
        System.out.println(epic);
    }

    public void addSubtask(int idEpic, int idSubtask) {
        if (epics.size() != 0) {
            for (Integer epicNumber : epics.keySet()) {
                if (epicNumber.equals(idEpic)) {
                    System.out.println("Введите название подзадачи ---> ");
                    String nameSubtask = scannerLine.nextLine();
                    System.out.println("Введите краткое описание подзадачи ---> ");
                    String subtaskDescription = scannerLine.nextLine();
                    Subtask subtask = new Subtask(nameSubtask, subtaskDescription, idSubtask, idEpic);
                    epics.get(epicNumber).getSubtaskData().put(idSubtask, subtask);
                    System.out.println("Подзадача создана под индексом " + idSubtask);
                    checkingEpicStatus(idEpic, idSubtask);
                }
            }
        } else {
            System.out.println("Список сложных задач пуст.");
        }

    }

    @Override
    public void updateStatus() {
        if (epics.size() != 0) {
            getList();
            while (true) {
                System.out.println("Введите индекс сложной задачи:");
                int idEpic = scanner.nextInt();
                if (epics.get(idEpic).getSubtaskData().size() != 0) {
                    while (true) {
                        if (epics.containsKey(idEpic)) {
                            System.out.println(epics.get(idEpic).getSubtaskData());
                            System.out.println("Введите индекс подзадачи, в которой хотите обновить статус:");
                            int idSubtask = scanner.nextInt();
                            if (epics.get(idEpic).getSubtaskData().containsKey(idSubtask)) {
                                System.out.println("Выберите новый статус");
                                System.out.println("1 ---> IN_PROGRESS — над задачей ведётся работа.\n" +
                                                   "2 ---> DONE — задача выполнена.");
                                int command = scanner.nextInt();
                                if (command == 1) {
                                    epics.get(idEpic).getSubtaskData().get(idSubtask).status = Status.IN_PROGRESS;
                                    System.out.println("Статус задачи изменен.");
                                    checkingEpicStatus(idEpic, idSubtask);
                                    return;
                                } else if (command == 2) {
                                    epics.get(idEpic).getSubtaskData().get(idSubtask).status = Status.DONE;
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

    @Override
    public void removeById () {
        System.out.println("Введите индекс задачи");
        int index = scanner.nextInt();
        if (epics.containsKey(index)) {
            epics.remove(index);
            System.out.println("Сложная задача удалена.");
        }
        for (Integer keyEpic : epics.keySet()) {
            if (epics.get(keyEpic).getSubtaskData().containsKey(index)) {
                epics.get(keyEpic).getSubtaskData().remove(index);
                System.out.println("Подзадача удалена.");
            }
        }
    }

    @Override
    public void edit(){
        if(epics.size() != 0) {
            System.out.println("Введите индекс задачи, которую хотите изменить:");
            int index = scanner.nextInt();
            if (epics.containsKey(index)) {
                add(index);
                System.out.println("Сложная задача изменена.");
            }
            for (Integer keyEpic : epics.keySet()) {
                if (epics.get(keyEpic).getSubtaskData().containsKey(index)) {
                    addSubtask(keyEpic, index);
                    System.out.println("Подзадача изменена.");
                }
            }
        }  else {
            System.out.println("Список сложных задач пуст.");
        }
    }

    private void checkingEpicStatus ( int keyEpic, int keySubtask) {
        int sumDone = 0;
        if (epics.get(keyEpic).getSubtaskData().get(keySubtask).status.equals(Status.IN_PROGRESS)) {
            epics.get(keyEpic).status = Status.IN_PROGRESS;
        }
        for (Integer key : epics.get(keyEpic).getSubtaskData().keySet()) {
            if (epics.get(keyEpic).getSubtaskData().get(key).status.equals(Status.DONE)) {
                sumDone += 1;
            }
        }
        if(epics.get(keyEpic).getSubtaskData().size() == sumDone) {
            epics.get(keyEpic).status = Status.DONE;
        }
    }
}
