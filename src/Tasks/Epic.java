package Tasks;

import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtaskData = new HashMap<>();


    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.type = TaskType.EPIC;
    }

    @Override
    public String toString() {
        return "Имя сложной задачи - " + name +
                ", Краткое описание - " + description +
                ", Индекс - " + id +
                ", Колличество подзадач - " + subtaskData.size() +
                ", Статус задачи - " + status + ";";
    }

    public HashMap<Integer, Subtask> getSubtaskData() {
        return subtaskData;
    }
}
