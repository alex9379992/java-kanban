package Tasks;


import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private final int idEpic;

    public Subtask(String name, String taskDescription, int id, int idEpic) {
        super(name, taskDescription, id);
        this.idEpic = idEpic;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(String name, String taskDescription, int id, int idEpic, String dataTime, long minutes) {
        super(name, taskDescription, id);
        this.idEpic = idEpic;
        this.type = TaskType.SUBTASK;
        setStartTime(dataTime, minutes);
    }



    @Override
    public String toString() {
       return  "Имя подзадачи - " + name +
                ", Краткое описание - " + description +
                ", Индекс - " + id +
                "Индекс эпика - " + idEpic +
                ", Статус задачи - " + status + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return idEpic == subtask.idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

}
