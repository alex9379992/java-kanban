import java.util.HashMap;

public class Epic extends Task{
    HashMap<Integer, Subtask> subtaskData = new HashMap<>();


    public Epic(String name, String description, int id, String status) {
        super(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Имя сложной задачи - " + name +
                "; Краткое описание - " + description +
                "; Индекс - " + id +
                "; Колличество подзадач - " + subtaskData.size() +
                "; Статус задачи - " + status;
    }
}
